package com.justsayyes.record.serviceimpl;

import com.justsayyes.record.DTO.*;
import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.Entity.Visitor;
import com.justsayyes.record.Entity.Comment;
import com.justsayyes.record.Entity.Status;

import com.justsayyes.record.service.BusinessService;
import com.justsayyes.record.repository.LocationRepository;
import com.justsayyes.record.repository.VisitorRepository;
import com.justsayyes.record.repository.StatusRepository;
import com.justsayyes.record.repository.UserCommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

@Service
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    WebApplicationContext applicationContext;

    @Override
    public ResponseEntity<?> getBusinessComments(long business_id) {
        Optional<Location> l=applicationContext.getBean(LocationRepository.class).findById(business_id);
        if(!l.isPresent()) return new ResponseEntity<>("INVALID BUSINESS",HttpStatus.BAD_REQUEST);
        List<Comment> comments=applicationContext.getBean(UserCommentRepository.class).getLatestComments30(l.get());
        List<UserCommentDTO> commentDTOs = new ArrayList<>();
        for (Comment c : comments) {
            commentDTOs.add(new UserCommentDTO(c.getVisitor().getEmail(),c.getBody(),String.valueOf(c.getCreateDate().getTime())));
        }
        return new ResponseEntity<>(commentDTOs,HttpStatus.OK);
    }
    
    @Override
    public ResponseEntity<?> putBusinessComments(long business_id, UserCommentDTO userCommentDTO) {
        Optional<Location> l=applicationContext.getBean(LocationRepository.class).findById(business_id);
        if(!l.isPresent()) return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        Optional<Visitor> v=applicationContext.getBean(VisitorRepository.class).findById(userCommentDTO.getEmail());
        if(!v.isPresent())return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);

        Comment comment = new Comment(userCommentDTO, v.get(), l.get());
        applicationContext.getBean(UserCommentRepository.class).save(comment);
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getBusinessCovidStatistics(long business_id) {
        Optional<Location> l=applicationContext.getBean(LocationRepository.class).findById(business_id);
        if(!l.isPresent()) return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        List<Status> statuses=applicationContext.getBean(StatusRepository.class).getLocationStatusByDate(l.get(),new Date(System.currentTimeMillis()), new Date(System.currentTimeMillis() - (7 * DAY_IN_MS)));
        List<StatusDTO> statusDTOs = new ArrayList<>();
        for (Status s : statuses) {
            statusDTOs.add(new StatusDTO(s.getVisitor().getEmail(),s.getContent(),String.valueOf(s.getCreateDate().getTime())));
        }
        return new ResponseEntity<>(statusDTOs,HttpStatus.OK);
    }
}