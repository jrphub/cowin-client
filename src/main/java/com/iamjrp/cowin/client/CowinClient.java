package com.iamjrp.cowin.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamjrp.cowin.filters.IFilter;
import com.iamjrp.cowin.model.Message;
import com.iamjrp.cowin.model.Session;
import com.iamjrp.cowin.model.SessionCalendarEntrySchema;
import com.iamjrp.cowin.service.QueueConsumerThread;
import com.iamjrp.cowin.utils.Counter;
import com.iamjrp.cowin.utils.CowinUtil;
import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.*;

@Service
public class CowinClient {
    private final Logger LOG = LoggerFactory.getLogger(CowinClient.class);

    @Autowired
    private RestTemplate restTemplate;

    public CowinClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static Set<Integer> hashCodeSet = new ConcurrentSkipListSet<>();

    private static ExecutorService executor = Executors.newFixedThreadPool(10);

    private static BlockingQueue<List<Message>> queue = new LinkedBlockingQueue<>();

    @Autowired
    private Counter counter;

    public String getSlots(String requestUri, int districtId, int minAge) {
        counter.increment();
        LOG.info("REST API hit counter : {} : {}", counter.getCount().get(), districtId);
        if (counter.getCount().get() >= 200) {
            LOG.info("Clearing cache ...");
            hashCodeSet.clear(); //This will trigger full output, not the difference from prev call
            counter.reset();
        }
        HttpEntity<String> entity = CowinUtil.setHeader();

        final UriComponentsBuilder builder = getUriComponentsBuilder(requestUri, districtId);
        try {
            final ResponseEntity<Object> slotsInfo =
                    restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Object.class);
            final LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> mapBody =
                    (LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>>) slotsInfo.getBody();
            processResponse(mapBody, districtId, minAge);
        } catch (Exception e) {
            LOG.info("Error handling request {} : {}", builder.toUriString(), e);
            return e.getMessage();
        }
        return "Message sent Successfully";
    }



    private UriComponentsBuilder getUriComponentsBuilder(String requestUri, int districtId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestUri)
                .queryParam("district_id", districtId)
                .queryParam("date", CowinUtil.getTodayDate());
        return builder;
    }

    private void processResponse(LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> mapBody, int districtId, int minAge) throws InterruptedException {
        final ArrayList<LinkedHashMap<String, String>> centers = Objects.requireNonNull(mapBody).get("centers");
        final ObjectMapper mapper = new ObjectMapper();

        List<Message> MsgBatch = Collections.synchronizedList(new ArrayList<>());
        IFilter filter = CowinUtil.getFilter(districtId, minAge);

        for (LinkedHashMap<String, String> center: centers) {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SessionCalendarEntrySchema pojo = mapper.convertValue(center, SessionCalendarEntrySchema.class);
            Session[] sessions = pojo.getSessions();
            for (Session session: sessions) {
                if (filter.filter(session, pojo)) {
                    final Message message = getMessage(session, pojo);
                    if(hashCodeSet.add(message.hashCode())) { //This will avoid duplicate msg in cache
                        MsgBatch.add(message);
                    }
                }
            }
        }
        if (!MsgBatch.isEmpty()) {
            if(MsgBatch.size() > 20) {
                final List<Message>[] partitions = getPartitions(MsgBatch);
                for(List<Message> partition : partitions) {
                    queue.put(partition);
                    TelegramClient telegramClient = CowinUtil.getTelegramClient(districtId, minAge);
                    QueueConsumerThread consumerThread = new QueueConsumerThread(queue, telegramClient);
                    executor.submit(consumerThread);
                }
            } else {
                queue.put(MsgBatch);
                TelegramClient telegramClient = CowinUtil.getTelegramClient(districtId, minAge);
                QueueConsumerThread consumerThread = new QueueConsumerThread(queue, telegramClient);
                executor.submit(consumerThread);
            }
            
        }
    }

    private List<Message>[] getPartitions(List<Message> msgBatch) {
        // Calculate the total number of partitions of size `20` each
        int m = msgBatch.size() / 20;
        if (msgBatch.size() % 20 != 0) {
            m++;
        }

        // partition the list into sublists of size `20` each
        List<List<Message>> itr = ListUtils.partition(msgBatch, 20);

        // create `m` empty lists and initialize them with sublists
        List<Message>[] partition = new ArrayList[m];
        for (int i = 0; i < m; i++) {
            partition[i] = new ArrayList(itr.get(i));
        }

        // return the lists
        return partition;
    }

    private Message getMessage(Session session, SessionCalendarEntrySchema pojo) {
        Message message = new Message();
        message.setAddress(pojo.getAddress());
        message.setAvailableDose1(String.valueOf(session.getAvailable_capacity_dose1()));
        message.setAvailableDose2(String.valueOf(session.getAvailable_capacity_dose2()));
        message.setDate(session.getDate());
        message.setFeeType(pojo.getFee_type());
        message.setMinAgeLimit(String.valueOf(session.getMin_age_limit()));
        message.setPincode(pojo.getPincode());
        message.setVaccine(session.getVaccine());
        return message;
    }
}
