package com.justsayyes.record.service;

import com.justsayyes.record.DTO.LocationInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RecordService {
    ResponseEntity<?> getLocationIndex(LocationInfoDTO locationInfoDTO);
}
