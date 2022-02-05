package com.justsayyes.record.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatMapDetailDTO {
    String locationId;
    List<Long> statics=new ArrayList<>(); //for frontend counting, better method
    String number;//for backend counting, naive method
}
