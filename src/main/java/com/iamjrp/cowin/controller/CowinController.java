package com.iamjrp.cowin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.JsonObject;
import com.iamjrp.cowin.client.CowinClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CowinController {

    @Autowired
    private CowinClient cowinClient;

    private static final String COWIN_URL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict";
    private static final int DST_ID_KHURDA = 446;
    private static final int DST_ID_CTC = 457;

    @GetMapping("/kslots")
    public String getVacSlotKhurda() throws JsonProcessingException, InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_KHURDA);
    }

    @GetMapping("/cslots")
    public String getVacSlotCtc() throws JsonProcessingException, InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_CTC);
    }

}
