package com.iamjrp.cowin.utils;

import com.iamjrp.cowin.client.TelegramClient;
import com.iamjrp.cowin.filters.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CowinUtil {
    public static String getTodayDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate= formatter.format(date);
        return strDate;
    }

    public static HttpEntity<String> setHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add("user-agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36");
        return new HttpEntity<>(headers);
    }

    public static IFilter getFilter(int districtId, int minAge) {
        if (446 == districtId && 18 == minAge) {
            return new FilterKhurda18();
        } else if (446 == districtId && 45 == minAge) {
            return new FilterKhurda45();
        } else if (457 == districtId && 18 == minAge) {
            return new FilterCuttack18();
        } else if (457 == districtId && 45 == minAge) {
            return new FilterCuttack45();
        } else if (395 == districtId && 0 == minAge){
            return new FilterMumbai();
        } else if (265 == districtId && 0 == minAge) {
            return new FilterBengaluru();
        } else if (581 == districtId && 0 == minAge) {
            return new FilterHyderabad();
        } else if (464 == districtId && 0 == minAge) {
            return new FilterKalahandi();
        } else if (395 == districtId && -1 == minAge) {
            return new TestFilter();
        } else if (446 == districtId && -1 == minAge) {
            return new TestFilter2();
        }
        return null;
    }

    public static TelegramClient getTelegramClient(int districtId, int minAge) {
        String token = "";
        String chatId = "";
        if (446 == districtId && 18 == minAge) {
            token = "1697658720:AAHSrZetXavhnHIwRenb2TzwhtPfZsbd6Y4";
            chatId = "@khurda18";
        } else if (446 == districtId && 45 == minAge) {
            token = "1803734972:AAHyAva04ql-XsGl3zNAPllzBDM49dPneCw";
            chatId = "@khurda45";
        } else if (457 == districtId && 18 == minAge) {
            token = "1821790232:AAHGeYp5Ij-Xim8hXyYyqa8W8Kpxzo6zsog";
            chatId = "@ctc1844";
        } else if (457 == districtId && 45 == minAge) {
            token = "1851420198:AAHP80aHMwHIvAgBnOcNmYgAHkQE6Y-MP6M";
            chatId = "@ctc45";
        } else if (395 == districtId && 0 == minAge){
            token = "1846153659:AAHzQWT5tHdByboimm8W7h5LyRMdvVDTIuI";
            chatId = "@mumvac";
        } else if (265 == districtId && 0 == minAge) {
            token = "1830195107:AAFQJC75w4AnCeif0a3_XXwVT_9fb1ElQi0";
            chatId = "@cowin_blru";
        } else if (581 == districtId && 0 == minAge) {
            token = "1717732054:AAF1Hi9oi644co5lLIYxBEOZc-nIo2zKSME";
            chatId = "@cowin_hyd";
        } else if (464 == districtId && 0 == minAge) {
            token = "1861461791:AAGrcmW3KKWFTvlnQd4XPrVLKcbL1O-8m18";
            chatId = "@@kh_bhw";
        } else if (395 == districtId && -1 == minAge) {
            token = "1783244153:AAHlr8fSauey9F1aNi0GWSHoJMgIXc_FJrQ";
            chatId = "@nojoinjrp";
        } else if (446 == districtId && -1 == minAge) {
            token = "1732888415:AAH0HkJI3fFHIWfp8w5R_WfNLqmA9Jlv3Bc";
            chatId = "@nojoinjrp2";
        }
        return new TelegramClient(token, chatId);
    }
}
