package com.justsayyes.record.serviceimpl;

import com.justsayyes.record.DTO.*;
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

import java.util.*;

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
        Date d=new Date();
        if(!statusDTO.getTimestamp().equals("")){
            d=new Date(Long.parseLong(statusDTO.getTimestamp()));
        }
        visitor.setUpdateDate(d);
        applicationContext.getBean(VisitorRepository.class).save(visitor);
        return new ResponseEntity<>(getExposureList(statusDTO.getEmail(),d),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getHeatMap(String keyword) {
        HeatMapDTO heatMapDTO=new HeatMapDTO();
        for(Location l:applicationContext.getBean(LocationRepository.class).findAll()){
            HeatMapDetailDTO heatMapDetailDTO=new HeatMapDetailDTO();
            heatMapDetailDTO.setLocationId(l.getId().toString());
            heatMapDetailDTO.setLatitude(l.getLatitude().toString());
            heatMapDetailDTO.setLongitude(l.getLongitude().toString());
            List<Record> records=applicationContext.getBean(RecordRepository.class).getRecordByLocationIdAndStatusOrderByCreateDate(l.getId(),keyword);
            for(Record record:records){
                heatMapDetailDTO.getStatics().add(record.getCreateDate().getTime());
            }
            heatMapDTO.getHeatMapDetailDTOS().add(heatMapDetailDTO);
        }
        return new ResponseEntity<>(heatMapDTO,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getHeatMapBetween(Long start, Long end) {
        Date begin=new Date(start);
        Date stop=new Date(end);
        HeatMapDTO heatMapDTO=new HeatMapDTO();
        for(Location l:applicationContext.getBean(LocationRepository.class).findAll()){
            HeatMapDetailDTO heatMapDetailDTO=new HeatMapDetailDTO();
            heatMapDetailDTO.setLocationId(l.getId().toString());
            heatMapDetailDTO.setLatitude(l.getLatitude().toString());
            heatMapDetailDTO.setLongitude(l.getLongitude().toString());
            long num=applicationContext.getBean(RecordRepository.class).countByLocationIdAndStatusAndCreateDateBetween(l.getId(),"ACTIVE",begin,stop);
            heatMapDetailDTO.setNumber(String.valueOf(num));
        }
        return new ResponseEntity<>(heatMapDTO,HttpStatus.OK);
    }

    @Override
    public void setSelfRecord(String email,Date d) {
        List<String> ans=new ArrayList<>();
        Optional<Visitor> v=applicationContext.getBean(VisitorRepository.class).findById(email);
        if(!v.isPresent()) return;
        Visitor visitor=v.get();
        List<Record> records=visitor.getRecords();
        records.sort(Comparator.comparing(Record::getCreateDate));
        for(int i=records.size()-1;i>=0;i--){
            if(d.getTime()-records.get(i).getCreateDate().getTime()>=15 * 60 * 60 * 24*1000){
                break;
            }
            records.get(i).setStatus("ACTIVE");
            applicationContext.getBean(RecordRepository.class).save(records.get(i));
        }

    }

    @Override
    public List<String> getExposureList(String email,Date d) {
        List<String> ans=new ArrayList<>();
        Optional<Visitor> v=applicationContext.getBean(VisitorRepository.class).findById(email);
        if(!v.isPresent()) return ans;
        Visitor visitor=v.get();
        List<Record> records=visitor.getRecords();
        records.sort(Comparator.comparing(Record::getCreateDate));
        Set<Long> locationIds=new HashSet<>();
        for(int i=records.size()-1;i>=0;i--){
            if(d.getTime()-records.get(i).getCreateDate().getTime()>=15 * 60 * 60 * 24*1000){
                break;
            }
            locationIds.add(records.get(i).getLocationId());
        }
        List<Record> exposed=applicationContext.getBean(RecordRepository.class)
                .getRecordByLocationIdInAndCreateDateBetween(locationIds,new Date(d.getTime()-15 * 60 * 60 * 24*1000),d);
        Set<String> names=new HashSet<>();
        for(Record r:exposed){
            r.setStatus("EXPOSED");
            applicationContext.getBean(RecordRepository.class).save(r);
            names.add(r.getVisitor().getEmail());
        }
        setSelfRecord(email,d);
        return new ArrayList<>(names);

    }

    @Override
    public ResponseEntity<?> getDailyCases(DailyCasesDTO dailyCasesDTO) {
        int cnt=0;
        for(Visitor v:applicationContext.getBean(VisitorRepository.class).findAll()){
            if(v.getStatus().equals("ACTIVE")){
                cnt++;
            }
        }



    }
}
