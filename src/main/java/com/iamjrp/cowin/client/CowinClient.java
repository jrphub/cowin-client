package com.iamjrp.cowin.client;

import com.iamjrp.cowin.utils.CowinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class CowinClient {
    private final Logger LOG = LoggerFactory.getLogger(CowinClient.class);

    @Autowired
    private RestTemplate restTemplate;

    public CowinClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<Object> getSlots(String requestUri) {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36");

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestUri)
                .queryParam("district_id", 446)
                .queryParam("date", CowinUtil.getTodayDate());

        HttpEntity<String> entity = new HttpEntity<>(headers);
        final ResponseEntity<Object> exchange =
                restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, Object.class);
        return exchange;
    }

}
