package com.justsayyes.record.service;

import com.justsayyes.record.DTO.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface RecordService {
    ResponseEntity<?> getLocationIndex(LocationInfoDTO locationInfoDTO);
    ResponseEntity<?> uploadRecord(RecordDTO recordDTO);
    ResponseEntity<?> uploadStatus(StatusDTO statusDTO);
    ResponseEntity<?> getHeatMap(String keyword);
    List<String> getExposureList(String email,Date d);
    ResponseEntity<?> getDailyCases(DailyCasesDTO dailyCasesDTO);
    ResponseEntity<?> getStatus(GetStatusDTO getStatusDTO);
    ResponseEntity<?> getCasesByDate();


}
