package com.justsayyes.record.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationInfoDTO {
    private String latitude;
    private String longitude;
    private String name;
    private String type;
    private String county;
    private String state;
    private String zipcode;
}
