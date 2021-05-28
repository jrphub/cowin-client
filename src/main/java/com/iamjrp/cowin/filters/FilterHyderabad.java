package com.iamjrp.cowin.filters;

import com.iamjrp.cowin.model.Session;
import com.iamjrp.cowin.model.SessionCalendarEntrySchema;

public class FilterHyderabad implements IFilter {

    private final String distName = "Hyderabad";
    private final int minDose1 = 5;
    private final int minDose2 = 5;

    @Override
    public boolean filter(Session session, SessionCalendarEntrySchema pojo) {
        return (distName.equals(pojo.getDistrict_name()) && 18 == session.getMin_age_limit()
                && (session.getAvailable_capacity_dose1() > minDose1 || session.getAvailable_capacity_dose2() > minDose2));
    }
}
