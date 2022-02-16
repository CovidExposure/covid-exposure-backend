package com.justsayyes.record.service;

import com.justsayyes.record.DTO.DailyCasesDTO;
import com.justsayyes.record.DTO.LocationInfoDTO;
import com.justsayyes.record.DTO.RecordDTO;
import com.justsayyes.record.DTO.StatusDTO;
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
    ResponseEntity<?> getHeatMapBetween(Long start,Long end);
    void setSelfRecord(String email, Date d);
    List<String> getExposureList(String email,Date d);
    ResponseEntity<?> getDailyCases(DailyCasesDTO dailyCasesDTO);

}
