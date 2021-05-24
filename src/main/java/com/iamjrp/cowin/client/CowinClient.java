package com.iamjrp.cowin.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iamjrp.cowin.model.Message;
import com.iamjrp.cowin.model.Session;
import com.iamjrp.cowin.model.SessionCalendarEntrySchema;
import com.iamjrp.cowin.service.QueueConsumerThread1;
import com.iamjrp.cowin.service.QueueConsumerThread2;
import com.iamjrp.cowin.service.QueueConsumerThread3;
import com.iamjrp.cowin.service.QueueConsumerThread4;
import com.iamjrp.cowin.utils.CowinUtil;
import com.iamjrp.cowin.utils.Filters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class CowinClient {
    private final Logger LOG = LoggerFactory.getLogger(CowinClient.class);

    @Autowired
    private RestTemplate restTemplate;

    public CowinClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getSlots(String requestUri, int districtId) throws JsonProcessingException, InterruptedException {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<>(headers);

        final UriComponentsBuilder builder = getUriComponentsBuilder(requestUri, districtId);

        final ResponseEntity<Object> slotsInfo =
                restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Object.class);
        final LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>> mapBody = (LinkedHashMap<String, ArrayList<LinkedHashMap<String, String>>>) slotsInfo.getBody();
        processResponse(mapBody);

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
        BlockingQueue<Message> queue1 = new LinkedBlockingQueue<>();
        BlockingQueue<Message> queue2 = new LinkedBlockingQueue<>();
        BlockingQueue<Message> queue3 = new LinkedBlockingQueue<>();
        BlockingQueue<Message> queue4 = new LinkedBlockingQueue<>();
        QueueConsumerThread1 consumerThread1 = new QueueConsumerThread1(queue1);
        QueueConsumerThread2 consumerThread2 = new QueueConsumerThread2(queue2);
        QueueConsumerThread3 consumerThread3 = new QueueConsumerThread3(queue3);
        QueueConsumerThread4 consumerThread4 = new QueueConsumerThread4(queue4);
        ExecutorService executor = Executors.newFixedThreadPool(10);
        /*for (int i = 1; i <= 10; i++) {
            executor.submit(consumerThread1);
            executor.submit(consumerThread2);
            executor.submit(consumerThread3);
            executor.submit(consumerThread4);
        }*/
        new Thread(consumerThread1).start();
        new Thread(consumerThread2).start();
        new Thread(consumerThread3).start();
        new Thread(consumerThread4).start();

        for (LinkedHashMap<String, String> center: centers) {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            SessionCalendarEntrySchema pojo = mapper.convertValue(center, SessionCalendarEntrySchema.class);
            Session[] sessions = pojo.getSessions();
            for (Session session: sessions) {
                if (Filters.checkForKhurda18(session, pojo)) {
                    final Message message = getMessage(session, pojo);
                    queue1.put(message);
                } else if (Filters.checkForKhurda45(session, pojo)) {
                    final Message message = getMessage(session, pojo);
                    queue2.put(message);
                } else if (Filters.checkForCuttack18(session, pojo)) {
                    final Message message = getMessage(session, pojo);
                    queue3.put(message);
                } else if (Filters.checkForCuttack45(session, pojo)) {
                    final Message message = getMessage(session, pojo);
                    queue4.put(message);
                }

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

}
