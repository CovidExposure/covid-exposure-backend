package com.justsayyes.record.repository;

import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.Entity.Record;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface RecordRepository extends CrudRepository<Record, Long> {

    List<Record> getRecordByLocationIdInAndCreateDateBetween(Collection<Long> locationId, Date createDate, Date createDate2);
}
