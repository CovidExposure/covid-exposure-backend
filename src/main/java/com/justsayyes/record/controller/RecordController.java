package com.justsayyes.record.controller;
import com.justsayyes.record.DTO.LocationInfoDTO;
import com.justsayyes.record.DTO.RecordDTO;
import com.justsayyes.record.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

@RestController
@RequestMapping("/record")
@Api("债权人基础信息")
@Retryable(value = Exception.class, maxAttempts = 3)
public class RecordController {

    @Autowired
    WebApplicationContext applicationContext;

    @ApiOperation(value = "Upload location info of diner and get id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class)
    })
    @RequestMapping(
            value = "/getIndexForLocations",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> getIndexForLocations(@RequestBody LocationInfoDTO locationInfoDTO) {
        return applicationContext.getBean(RecordService.class).getLocationIndex(locationInfoDTO);
    }

    @ApiOperation(value = "Upload visitor record")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = Boolean.class),
            @ApiResponse(code = 400, message = "bad request", response = Boolean.class)
    })
    @RequestMapping(
            value = "/uploadRecord",
            method = {RequestMethod.POST},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> uploadRecord(@RequestBody RecordDTO recordDTO) {
        return applicationContext.getBean(RecordService.class).uploadRecord(recordDTO);
    }


}
