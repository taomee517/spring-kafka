package com.demo.kafka.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = "测试案例CONTROLLER", description = "INDEX")
public class IndexController {

    @GetMapping(value = "index")
    @ApiOperation(value = "打个招呼")
    public String index(){
        return "hello!";
    }
}
