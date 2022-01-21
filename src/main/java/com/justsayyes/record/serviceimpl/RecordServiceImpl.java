package com.justsayyes.record.serviceimpl;

import com.justsayyes.record.DTO.LocationInfoDTO;
import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.repository.LocationRepository;
import com.justsayyes.record.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    WebApplicationContext applicationContext;

    @Override
    public ResponseEntity<?> getLocationIndex(LocationInfoDTO locationInfoDTO) {
        Location l=new Location(locationInfoDTO);
        applicationContext.getBean(LocationRepository.class).save(l);
        return new ResponseEntity<>(l.getId()+" received!!!!!!!", HttpStatus.OK);
    }
}
