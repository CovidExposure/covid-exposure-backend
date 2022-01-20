package com.justsayyes.record.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface RecordService {
    ResponseEntity<?> getFinishBasicInfo(String CreditorId);
}
