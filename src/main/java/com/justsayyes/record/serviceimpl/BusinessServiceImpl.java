package com.justsayyes.record.serviceimpl;

import com.justsayyes.record.DTO.*;
import com.justsayyes.record.Entity.Location;
import com.justsayyes.record.Entity.Visitor;
import com.justsayyes.record.Entity.Comment;

import com.justsayyes.record.service.BusinessService;
import com.justsayyes.record.repository.LocationRepository;
import com.justsayyes.record.repository.VisitorRepository;
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
        List<UserCommentDTO> commetDTOs = new ArrayList<>();
        for (Comment c : comments) {
            commetDTOs.add(new UserCommentDTO(c.getVisitor().getEmail(),c.getBody(),String.valueOf(c.getCreateDate().getTime())));
        }
        return new ResponseEntity<>(commetDTOs,HttpStatus.OK);
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
}