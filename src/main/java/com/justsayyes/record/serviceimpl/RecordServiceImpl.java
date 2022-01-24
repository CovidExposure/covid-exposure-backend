package com.justsayyes.record.serviceimpl;

import com.justsayyes.record.DTO.LocationInfoDTO;
import com.justsayyes.record.DTO.RecordDTO;
import com.justsayyes.record.DTO.StatusDTO;
import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.Entity.Record;
import com.justsayyes.record.Entity.Visitor;
import com.justsayyes.record.repository.LocationRepository;
import com.justsayyes.record.repository.RecordRepository;
import com.justsayyes.record.repository.VisitorRepository;
import com.justsayyes.record.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    WebApplicationContext applicationContext;

    @Override
    public ResponseEntity<?> getLocationIndex(LocationInfoDTO locationInfoDTO) {
        Location l=new Location(locationInfoDTO);
        applicationContext.getBean(LocationRepository.class).save(l);
        return new ResponseEntity<>(l.getId(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> uploadRecord(RecordDTO recordDTO) {
        Optional<Location> l=applicationContext.getBean(LocationRepository.class).findById(Long.parseLong(recordDTO.getLocationId()));
        if(!l.isPresent()) return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        Optional<Visitor> v=applicationContext.getBean(VisitorRepository.class).findById(recordDTO.getEmail());
        Visitor visitor=new Visitor();
        if(!v.isPresent()){
            visitor.setEmail(recordDTO.getEmail());
        }else{
            visitor=v.get();
        }
        Record r=new Record(recordDTO,visitor);
        visitor.getRecords().add(r);
        applicationContext.getBean(VisitorRepository.class).save(visitor);
        applicationContext.getBean(RecordRepository.class).save(r);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> uploadStatus(StatusDTO statusDTO) {
        Optional<Visitor> v=applicationContext.getBean(VisitorRepository.class).findById(statusDTO.getEmail());
        if(!v.isPresent())return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        Visitor visitor=v.get();
        visitor.setStatus(statusDTO.getStatus());
        applicationContext.getBean(VisitorRepository.class).save(visitor);
        return new ResponseEntity<>(true,HttpStatus.OK);

    }
}
