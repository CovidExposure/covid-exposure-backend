package com.justsayyes.record.service;

import com.justsayyes.record.DTO.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public interface BusinessService {
    ResponseEntity<?> getBusinessComments(long business_id);
    ResponseEntity<?> putBusinessComments(long business_id, UserCommentDTO userCommentDTO);
    ResponseEntity<?> getBusinessCovidStatistics(long business_id);
}