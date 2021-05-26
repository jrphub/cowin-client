package com.iamjrp.cowin.filters;

import com.iamjrp.cowin.model.Session;
import com.iamjrp.cowin.model.SessionCalendarEntrySchema;

public interface IFilter {
    public boolean filter(Session session, SessionCalendarEntrySchema pojo);
}
