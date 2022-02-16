package com.justsayyes.record.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyCasesRetDTO {
    private String status;
    private Integer activeCases;
    private Integer activeCasesAroundYou;
    private Integer activeCasesYesterday;
    private Integer activeCasesYesterdayAroundYou;
}
