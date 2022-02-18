package com.justsayyes.record.repository;

import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.Entity.Record;
import com.justsayyes.record.Entity.Visitor;
import org.hibernate.query.criteria.internal.expression.function.AggregationFunction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface RecordRepository extends CrudRepository<Record, Long> {

    @Query(value="select record from Record record where record.location in :locations and record.createDate" +
            " between :createDate and :createDate2 and not record.visitor =:v ")
    List<Record> getRecordByLocationIdInAndCreateDateBetween(List<Location> locations, Date createDate, Date createDate2,Visitor v);




}
