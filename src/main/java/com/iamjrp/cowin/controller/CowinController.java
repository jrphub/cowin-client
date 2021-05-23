package com.iamjrp.cowin.controller;

import com.iamjrp.cowin.client.CowinClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CowinController {

    @Autowired
    private CowinClient cowinClient;

    private static String url = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict";

    @GetMapping("/slots")
    public String getVaccinationSlots() {
        final ResponseEntity<Object> slots = cowinClient.getSlots(url);
        return slots.toString();
    }

}
