package com.justsayyes.record.controller;
import com.justsayyes.record.service.RecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;

@RestController
@Api("债权人基础信息")
@Retryable(value = Exception.class, maxAttempts = 3)
public class RecordController {

    @Autowired
    WebApplicationContext applicationContext;

    @ApiOperation(value = "获取债权人基础信息是否已提交完全")
    @ApiResponses({
            @ApiResponse(code = 200, message = "ok", response = String.class),
            @ApiResponse(code = 404, message = "wrong creditorId", response = String.class),
    })
    @RequestMapping(
            value = "/getFinishBasicInfo/CreditorId/{CreditorId}",
            method = {RequestMethod.GET, RequestMethod.OPTIONS},
            produces = "application/json;charset=UTF-8"
    )
    public ResponseEntity<?> getFinishBasicInfo(@PathVariable String CreditorId) {
        return applicationContext.getBean(RecordService.class).getFinishBasicInfo(CreditorId);
    }

}
