package com.justsayyes.record.serviceimpl;

import com.justsayyes.record.service.RecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {
    @Override
    public ResponseEntity<?> getFinishBasicInfo(String CreditorId) {
        return new ResponseEntity<>(CreditorId+" received", HttpStatus.OK);
    }
}
