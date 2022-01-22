package com.justsayyes.record.Entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "visitor")
    private List<Record> records=new ArrayList<>();
}
