package com.justsayyes.record.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeatMapDTO {
    List<HeatMapDetailDTO> heatMapDetailDTOS=new ArrayList<>();
}