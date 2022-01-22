package com.justsayyes.record.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "visitor")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Visitor {
    @Id
    @Column(nullable = false)
    String email;

    @Column
    String status;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "update_date")
    private Date updateDate;

    @OneToMany(mappedBy = "visitor")
    private List<Record> records=new ArrayList<>();
}
