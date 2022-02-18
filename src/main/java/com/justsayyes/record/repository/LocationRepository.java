package com.justsayyes.record.repository;

import com.justsayyes.record.Entity.Location;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface LocationRepository extends CrudRepository<Location, Long> {

    @Query(value="select location from Location location where location.longitude between (:longitude1) and (:longitude2) and location.latitude between" +
            "(:latitude1) and (:latitude2)")
    List<Location> getNearByLocation(BigDecimal longitude1, BigDecimal longitude2, BigDecimal latitude1, BigDecimal latitude2);

//    List<Long> getLocationsAroundYou();
}
