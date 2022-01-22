package com.justsayyes.record.service;

import com.justsayyes.record.DTO.LocationInfoDTO;
import com.justsayyes.record.DTO.RecordDTO;
import com.justsayyes.record.Entity.Record;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RecordService {
    ResponseEntity<?> getLocationIndex(LocationInfoDTO locationInfoDTO);
    ResponseEntity<?> uploadRecord(RecordDTO recordDTO);
}
