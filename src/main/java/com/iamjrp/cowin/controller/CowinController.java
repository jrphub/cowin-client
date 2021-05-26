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
    private static final int DIST_ID_MUM = 395;

    @GetMapping("/kslots18")
    public String getVacSlotKhurda18() throws JsonProcessingException, InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_KHURDA,18);
    }
    @GetMapping("/kslots45")
    public String getVacSlotKhurda45() throws JsonProcessingException, InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_KHURDA,45);
    }

    @GetMapping("/cslots18")
    public String getVacSlotCtc18() throws JsonProcessingException, InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_CTC, 18);
    }

    @GetMapping("/cslots45")
    public String getVacSlotCtc45() throws JsonProcessingException, InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_CTC, 45);
    }

    @GetMapping("/mslots")
    public String getVacSlotMum() throws JsonProcessingException, InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DIST_ID_MUM, 0);
    }

}
