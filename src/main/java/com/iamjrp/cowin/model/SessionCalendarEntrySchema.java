package com.iamjrp.cowin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCalendarEntrySchema {
    private long center_id;
    private String name;
    private String address;
    private String state_name;
    private String district_name;
    private String block_name;
    private String pincode;
    private String from;
    private String to;
    private String fee_type;
    private VaccineFee[] vaccine_fees;
    private Session[] sessions;
}
