package com.justsayyes.record.Entity;

import com.justsayyes.record.DTO.UserCommentDTO;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "business")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Comment {
    @Id
    @Column(nullable = false)
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Visitor visitor;

    @ManyToOne(fetch = FetchType.LAZY)
    private Location location;

    @Column
    private Date createDate;

    @Column
    private String body;

    public Comment(UserCommentDTO userCommentDTO, Visitor v, Location l){
        this.visitor=v;
        this.location=l;
        if(!userCommentDTO.getTimestamp().equals("")){
            this.createDate=new Date(Long.parseLong(userCommentDTO.getTimestamp()));
        }else{
            this.createDate=new Date();
        }
        this.body = userCommentDTO.getBody();
    }
}
