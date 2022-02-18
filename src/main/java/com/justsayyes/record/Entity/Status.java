package com.justsayyes.record.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "status")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Status {
    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Visitor visitor;

    @Column
    private Date createDate;

    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

}
