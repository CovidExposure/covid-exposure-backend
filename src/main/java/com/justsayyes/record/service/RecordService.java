package com.justsayyes.record.service;

import org.springframework.http.ResponseEntity;

public interface RecordService {
    ResponseEntity<?> getFinishBasicInfo(String CreditorId);
}
