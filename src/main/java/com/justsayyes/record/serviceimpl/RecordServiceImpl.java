package com.justsayyes.record.serviceimpl;

import com.justsayyes.record.DTO.LocationInfoDTO;
import com.justsayyes.record.service.RecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl implements RecordService {
    @Override
    public ResponseEntity<?> getLocationIndex(LocationInfoDTO locationInfoDTO) {
        return new ResponseEntity<>(locationInfoDTO.getName()+" received!!!!!!!", HttpStatus.OK);
    }
}
