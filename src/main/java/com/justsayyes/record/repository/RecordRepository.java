package com.justsayyes.record.repository;

import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.Entity.Record;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface RecordRepository extends CrudRepository<Record, Long> {

    List<Record> getRecordByLocationIdInAndCreateDateBetween(Collection<Long> locationId, Date createDate, Date createDate2);

    @Query(value = "select record from Record record where record.locationId=:locationId and record.status=:status")
    List<Record> getRecordByLocationIdAndStatusOrderByCreateDate(Long locationId,String status);



    long  countByLocationIdAndStatusAndCreateDateBetween(Long locationId, String status, Date createDate, Date createDate2);
}
