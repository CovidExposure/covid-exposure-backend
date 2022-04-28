package com.justsayyes.record.controller;
import com.justsayyes.record.DTO.*;
import com.justsayyes.record.service.BusinessService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;


@RestController
@RequestMapping("/business")
@Api("controller for commenting on business homepage")
@Retryable(value = Exception.class, maxAttempts = 3)
public class BusinessController {

    @Autowired
    WebApplicationContext applicationContext;

    @ApiOperation(value = "Get user comments on business homepage")
    @ApiResponses({
        @ApiResponse(code = 200, message = "ok", response = UserCommentDTO.class, responseContainer = "List"),
        @ApiResponse(code = 400, message = "bad request", response = String.class)
    })
    @RequestMapping(
            value = "/{business_id}/comments",
            method = {RequestMethod.GET},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> getBusinessComments(@PathVariable("business_id") long business_id) {
        return applicationContext.getBean(BusinessService.class).getBusinessComments(business_id);
    }

    @ApiOperation(value = "Put user comments on business homepage")
    @ApiResponses({
        @ApiResponse(code = 200, message = "ok", response = Boolean.class),
        @ApiResponse(code = 400, message = "bad request", response = Boolean.class)
    })
    @RequestMapping(
            value = "/{business_id}/comments",
            method = {RequestMethod.PUT},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> putBusinessComments(@PathVariable("business_id") long business_id, @RequestBody UserCommentDTO userCommentDTO) {
        return applicationContext.getBean(BusinessService.class).putBusinessComments(business_id, userCommentDTO);
    }

}