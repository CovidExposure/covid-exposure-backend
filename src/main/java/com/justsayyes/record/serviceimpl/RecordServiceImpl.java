package com.justsayyes.record.serviceimpl;

import com.justsayyes.record.DTO.*;
import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.Entity.Record;
import com.justsayyes.record.Entity.Status;
import com.justsayyes.record.Entity.Visitor;
import com.justsayyes.record.repository.LocationRepository;
import com.justsayyes.record.repository.RecordRepository;
import com.justsayyes.record.repository.StatusRepository;
import com.justsayyes.record.repository.VisitorRepository;
import com.justsayyes.record.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;


import java.math.BigDecimal;
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
            applicationContext.getBean(VisitorRepository.class).save(visitor);
        }else{
            visitor=v.get();
        }
        Record r=new Record(recordDTO,visitor,l.get());
        applicationContext.getBean(RecordRepository.class).save(r);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> uploadStatus(StatusDTO statusDTO) {
        Optional<Visitor> v=applicationContext.getBean(VisitorRepository.class).findById(statusDTO.getEmail());
        if(!v.isPresent())return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        Date d=new Date();
        if(!statusDTO.getTimestamp().equals("")){
            d=new Date(Long.parseLong(statusDTO.getTimestamp()));
        }
        List<Record> records=v.get().getRecords();
        records.sort(Comparator.comparing(Record::getCreateDate));
        setStatus(v.get(),"ACTIVE",d,records.get(records.size()-1).getLocation());
        return new ResponseEntity<>(getExposureList(statusDTO.getEmail(),d),HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getHeatMap(String keyword) {
        HeatMapDTO heatMapDTO=new HeatMapDTO();
        List<Status> statuses=applicationContext.getBean(StatusRepository.class).getAllByContentEquals(keyword);
        for(Location l:applicationContext.getBean(LocationRepository.class).findAll()){
            HeatMapDetailDTO heatMapDetailDTO=new HeatMapDetailDTO();
            heatMapDetailDTO.setLocationId(l.getId().toString());
            heatMapDetailDTO.setLatitude(l.getLatitude().toString());
            heatMapDetailDTO.setLongitude(l.getLongitude().toString());
            List<Date> ans=applicationContext.getBean(StatusRepository.class).getHeatMap(l,keyword);
            for(Date d:ans){
                heatMapDetailDTO.getStatics().add(d.getTime());
            }
            heatMapDTO.getHeatMapDetailDTOS().add(heatMapDetailDTO);
        }
        return new ResponseEntity<>(heatMapDTO,HttpStatus.OK);
    }





    @Override
    public List<String> getExposureList(String email,Date d) {
        List<String> ans=new ArrayList<>();
        Optional<Visitor> v=applicationContext.getBean(VisitorRepository.class).findById(email);
        if(!v.isPresent()) return ans;
        Visitor visitor=v.get();
        List<Record> records=visitor.getRecords();
        records.sort(Comparator.comparing(Record::getCreateDate));
        List<Location> locations=new ArrayList<>();
        for(int i=records.size()-1;i>=0;i--){
            if(d.getTime()-records.get(i).getCreateDate().getTime()>=15 * 60 * 60 * 24*1000){
                break;
            }
            locations.add(records.get(i).getLocation());
        }
        List<Record> exposed=applicationContext.getBean(RecordRepository.class)
                .getRecordByLocationIdInAndCreateDateBetween(locations,new Date(d.getTime()-15 * 60 * 60 * 24*1000),d,visitor);
        List<String> ret=new ArrayList<>();
        for(Record temp:exposed){
            ret.add(temp.getVisitor().getEmail());
            setStatus(temp.getVisitor(),"EXPOSED",temp.getCreateDate(),temp.getLocation());
        }
        return ret;
    }

    private void setStatus(Visitor v,String content, Date d,Location location){
        Status status=new Status();
        status.setContent(content);
        status.setCreateDate(d);
        status.setVisitor(v);
        status.setLocation(location);
        applicationContext.getBean(StatusRepository.class).save(status);
    }

    @Override
    public ResponseEntity<?> getDailyCases(DailyCasesDTO dailyCasesDTO) {
        int activeCases=(int) applicationContext.getBean(StatusRepository.class).getActiveCases("ACTIVE");
        Date now=new Date();
        double lo=Double.parseDouble(dailyCasesDTO.getLongitude());
        double la=Double.parseDouble(dailyCasesDTO.getLatitude());
        List<Location> locations=applicationContext.getBean(LocationRepository.class).getNearByLocation(BigDecimal.valueOf(lo-0.05),BigDecimal.valueOf(lo+0.05),BigDecimal.valueOf(la-0.05),BigDecimal.valueOf(la+0.05));
        int activeCasesYesterday=(int) applicationContext.getBean(StatusRepository.class).getActiveCasesYesterday("ACTIVE",new Date(now.getTime()-24*60*60*1000),now);
        if(locations.size()==0) return new ResponseEntity<>(new DailyCasesRetDTO(activeCases,0,activeCasesYesterday,0),HttpStatus.OK);
        int activeCasesAroundYou= (int) applicationContext.getBean(StatusRepository.class).getActiveCasesAroundYou(locations,"ACTIVE");
        int activeCasesYesterdayAroundYou = (int) applicationContext.getBean(StatusRepository.class).getActiveCasesAroundYouYesterday(locations,"ACTIVE",new Date(now.getTime()-24*60*60*1000),now);
        return new ResponseEntity<>(new DailyCasesRetDTO(activeCases,activeCasesAroundYou,activeCasesYesterday,activeCasesYesterdayAroundYou),HttpStatus.OK);

    }

    @Override
    public ResponseEntity<?> getStatus(GetStatusDTO getStatusDTO) {
        Optional<Visitor> v=applicationContext.getBean(VisitorRepository.class).findById(getStatusDTO.getEmail());
        if(!v.isPresent()) return new ResponseEntity<>("INVALID USER",HttpStatus.BAD_REQUEST);
        List<Status> statuses=applicationContext.getBean(StatusRepository.class).getLatestStatus(v.get());
        if(statuses.size()==0) return new ResponseEntity<>("UNKNOWN",HttpStatus.OK);
        return new ResponseEntity<>(statuses.get(statuses.size()-1).getContent(),HttpStatus.OK);
    }
}
