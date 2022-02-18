package com.justsayyes.record.repository;

import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.Entity.Record;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface RecordRepository extends CrudRepository<Record, Long> {

    List<Record> getRecordByLocationIdInAndCreateDateBetween(Collection<Long> locationId, Date createDate, Date createDate2);

    @Query(value = "select record from Record record where record.locationId=:locationId and record.status=:status")
    List<Record> getRecordByLocationIdAndStatusOrderByCreateDate(@Param("locationId")Long locationId,@Param("status") String status);

    long  countByLocationIdAndStatusAndCreateDateBetween(Long locationId, String status, Date createDate, Date createDate2);

    @Query(value = "select count(distinct record.visitor)from Record record where record.locationId in (:locations) and record.status=:status")
    long getActiveCasesAroundYou(List<Long> locations, String status);

    @Query(value = "select count(distinct record.visitor)from Record record where record.locationId in (:locations) and record.status=:status and " +
            "record.createDate between (:createDate) and (:createDate2)")
    long getActiveCasesAroundYouYesterday(List<Long> locations, String status, Date createDate, Date createDate2);

}
