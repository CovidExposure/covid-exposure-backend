package com.justsayyes.record.controller;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api("债权人基础信息")
@Retryable(value = Exception.class, maxAttempts = 3)
public class RecordController {

}
