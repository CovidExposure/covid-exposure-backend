package com.justsayyes.record.Entity;

import com.justsayyes.record.DTO.LocationInfoDTO;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "location")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Location {
    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long id;
    @Column(nullable = false,precision = 32,scale=16)
    private BigDecimal latitude;
    @Column(nullable = false,precision = 32,scale=16)
    private BigDecimal longitude;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String county;
    @Column
    private String state;
    @Column
    private BigDecimal zipcode;
    @OneToMany(mappedBy = "location",cascade = CascadeType.ALL)
    private List<Status> statuses=new ArrayList<>();

    @OneToMany(mappedBy = "location",cascade = CascadeType.ALL)
    private List<Record> records=new ArrayList<>();

    public Location(LocationInfoDTO locationInfoDTO){
        this.latitude=BigDecimal.valueOf(Double.parseDouble(locationInfoDTO.getLatitude()));
        this.longitude=BigDecimal.valueOf(Double.parseDouble(locationInfoDTO.getLongitude()));
        this.name=locationInfoDTO.getName();
        this.type=locationInfoDTO.getType();
        this.county=locationInfoDTO.getCounty();
        this.state=locationInfoDTO.getState();
        this.zipcode=locationInfoDTO.getZipcode().equals("")? BigDecimal.valueOf(0.0) :BigDecimal.valueOf(Double.parseDouble(locationInfoDTO.getZipcode()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Location location = (Location) o;
        return id != null && Objects.equals(id, location.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
