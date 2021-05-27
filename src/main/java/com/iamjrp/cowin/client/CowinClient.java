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

    private IFilter filter;

    private TelegramClient telegramClient;

    public CowinClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private static Set<Integer> hashCodeSet = new ConcurrentSkipListSet<>();

    @Autowired
    private Counter counter;

    public String getSlots(String requestUri, int districtId, int minAge) throws InterruptedException {
        LOG.info("REST API hit counter : " + counter.getCount().get());
        if (counter.getCount().get() == 200) {
            LOG.info("Clearing cache ...");
            hashCodeSet.clear(); //This will trigger full output, not the difference from prev call
            counter.reset();
        }
        HttpEntity<String> entity = CowinUtil.setHeader();

        IFilter filter = CowinUtil.getFilter(districtId, minAge);
        setFilter(filter);

        TelegramClient telegramClient = CowinUtil.getTelegramClient(districtId, minAge);
        setTelegramClient(telegramClient);


        final UriComponentsBuilder builder = getUriComponentsBuilder(requestUri, districtId);

        final ResponseEntity<Object> slotsInfo =
                restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Object.class);
        final LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> mapBody = (LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>>) slotsInfo.getBody();

        processResponse(mapBody);

        counter.increment();
        return "Message sent Successfully";
    }



    private UriComponentsBuilder getUriComponentsBuilder(String requestUri, int districtId) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestUri)
                .queryParam("district_id", districtId)
                .queryParam("date", CowinUtil.getTodayDate());
        return builder;
    }

    private void processResponse(LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> mapBody) throws InterruptedException {
        final ArrayList<LinkedHashMap<String, String>> centers = Objects.requireNonNull(mapBody).get("centers");
        final ObjectMapper mapper = new ObjectMapper();

        BlockingQueue<List<Message>> queue = new LinkedBlockingQueue<>();
        QueueConsumerThread consumerThread = new QueueConsumerThread(queue, telegramClient);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.submit(consumerThread);

        for (LinkedHashMap<String, String> center: centers) {
            List<Message> MsgForCenter = Collections.synchronizedList(new ArrayList<>());
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SessionCalendarEntrySchema pojo = mapper.convertValue(center, SessionCalendarEntrySchema.class);
            Session[] sessions = pojo.getSessions();
            for (Session session: sessions) {
                if (filter.filter(session, pojo)) {
                    final Message message = getMessage(session, pojo);
                    if(hashCodeSet.add(message.hashCode())) { //This will avoid duplicate msg in cache
                        MsgForCenter.add(message);
                    }
                }
            }
            if (!MsgForCenter.isEmpty()) {
                queue.put(MsgForCenter);
            }
        }

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

    public void setFilter(IFilter filter) {
        this.filter = filter;
    }

    public void setTelegramClient(TelegramClient client) {
        this.telegramClient = client;
    }
}
