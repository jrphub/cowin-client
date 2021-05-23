package com.iamjrp.cowin.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineFee {
    private String vaccine;
    private String fee;
}
