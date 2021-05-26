package com.iamjrp.cowin.utils;

import com.iamjrp.cowin.model.Session;
import com.iamjrp.cowin.model.SessionCalendarEntrySchema;

public class Filters {

    public static boolean checkForKhurda18(Session session, SessionCalendarEntrySchema pojo) {
        return ("Khurda".equals(pojo.getDistrict_name()) && 18 == session.getMin_age_limit() && (session.getAvailable_capacity_dose1() > 5 || session.getAvailable_capacity_dose2() > 5));
    }

    public static boolean checkForKhurda45(Session session, SessionCalendarEntrySchema pojo) {
        return ("Khurda".equals(pojo.getDistrict_name()) && 45 == session.getMin_age_limit() && (session.getAvailable_capacity_dose1() > 5 || session.getAvailable_capacity_dose2() > 5));
    }

    public static boolean checkForCuttack18(Session session, SessionCalendarEntrySchema pojo) {
        return ("Cuttack".equals(pojo.getDistrict_name()) && 18 == session.getMin_age_limit() && (session.getAvailable_capacity_dose1() > 5 || session.getAvailable_capacity_dose2() > 5));
    }

    public static boolean checkForCuttack45(Session session, SessionCalendarEntrySchema pojo) {
        return ("Cuttack".equals(pojo.getDistrict_name()) && 45 == session.getMin_age_limit() && (session.getAvailable_capacity_dose1() > 5 || session.getAvailable_capacity_dose2() > 5));
    }

    public static boolean checkForMumbai(Session session, SessionCalendarEntrySchema pojo) {
        return ("Mumbai".equals(pojo.getDistrict_name()) && (session.getAvailable_capacity_dose1() > 5 || session.getAvailable_capacity_dose2() > 5));
    }
}
