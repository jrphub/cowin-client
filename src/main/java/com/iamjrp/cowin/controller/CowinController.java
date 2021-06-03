package com.iamjrp.cowin.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iamjrp.cowin.client.CowinClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CowinController {

    @Autowired
    private CowinClient cowinClient;

    private static final String COWIN_URL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByDistrict";
    private static final int DST_ID_KHURDA = 446;
    private static final int DST_ID_CTC = 457;
    private static final int DIST_ID_KLH = 464;

    private static final int DIST_ID_MUM = 395;
    private static final int DIST_ID_BLR = 265;
    private static final int DIST_ID_HYD = 581;

    private static final int DIST_ID_VZN = 15;


    @GetMapping("/kslots18")
    public String getVacSlotKhurda18() throws InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_KHURDA,18);
    }
    @GetMapping("/kslots45")
    public String getVacSlotKhurda45() throws InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_KHURDA,45);
    }

    @GetMapping("/cslots18")
    public String getVacSlotCtc18() throws JsonProcessingException, InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_CTC, 18);
    }

    @GetMapping("/cslots45")
    public String getVacSlotCtc45() throws InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_CTC, 45);
    }

    @GetMapping("/mslots")
    public String getVacSlotMum() throws InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DIST_ID_MUM, 0);
    }

    @GetMapping("/bslots")
    public String getVacSlotBlr() throws InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DIST_ID_BLR, 0);
    }

    @GetMapping("/hslots")
    public String getVacSlotHyd() throws InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DIST_ID_HYD, 0);
    }

    @GetMapping("/klhslots")
    public String getVacSlotKalahandi() throws InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DIST_ID_KLH, 0);
    }

    @GetMapping("/vznslots")
    public String getVacSlotVzn() throws InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DIST_ID_VZN, 0);
    }

    @GetMapping("/test")
    public String getVacSlotForTest() throws InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DIST_ID_MUM, -1);
    }

    @GetMapping("/test2")
    public String getVacSlotForTest2() throws InterruptedException {
        return cowinClient.getSlots(COWIN_URL, DST_ID_KHURDA, -1);
    }

}
