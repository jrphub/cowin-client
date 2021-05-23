package com.iamjrp.cowin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    private String session_id;
    private String date;
    private long available_capacity;
    private long available_capacity_dose1;
    private long available_capacity_dose2;
    private int min_age_limit;
    private String vaccine;
    private String[] slots;

}
