package com.justsayyes.record.repository;

import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.Entity.Status;
import com.justsayyes.record.Entity.Visitor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface StatusRepository extends CrudRepository<Status, Long> {
    List<Status> getAllByContentEquals(String content);

    @Query(value = "select count(status) from Status status where status.content=:keyword")
    long getActiveCases(String keyword);

    @Query(value = "select count(status) from Status status where status.content=:keyword and status.createDate between :createDate and :createDate2")
    long getActiveCasesYesterday(String keyword, Date createDate, Date createDate2);

    @Query(value = "select status.createDate from Status status where status.location=:location and status.content=:content order by status.createDate")
    List<Date> getHeatMap(Location location,String content);

    @Query(value = "select status from Status status where status.visitor=:v order by status.createDate")
    List<Status> getLatestStatus(Visitor v);

    @Query(value = "select count(status) from Status status where status.location in :locations and status.content=:keyword")
    long getActiveCasesAroundYou(List<Location> locations,String keyword);

    @Query(value = "select count(status) from Status status where status.location in :locations and status.content=:keyword and status.createDate between :createDate and :createDate2")
    long getActiveCasesAroundYouYesterday(List<Location> locations,String keyword, Date createDate, Date createDate2);

    @Query(value = "SELECT COUNT(DISTINCT visitor_email) totalCount, DATE(create_date) date FROM status WHERE content=?1 GROUP BY DATE(create_date) ORDER BY DATE(create_date)", nativeQuery = true)
    List<Object[]> getCasesByDay(String content);


}
