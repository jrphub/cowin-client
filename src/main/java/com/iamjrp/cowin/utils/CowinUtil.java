package com.iamjrp.cowin.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CowinUtil {
    public static String getTodayDate() {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String strDate= formatter.format(date);
        return strDate;
    }
}
