package com.iamjrp.cowin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String pincode;
    private String address;
    private String feeType;
    private String date;
    private String minAgeLimit;
    private String vaccine;
    private String availableDose1;
    private String availableDose2;

    @Override
    public String toString() {
        return "[" + minAgeLimit + "+]" + "[" + pincode + "]" + "[" + vaccine + "]" +
                "\n <b>date</b> : " + date +
                "\n <b>dose1</b> : " + availableDose1 +
                "\n <b>dose2</b> : " + availableDose2 +
                "\n <b>address</b> : " + address +
                "\n <b>feeType</b> : " + feeType;
    }
}
