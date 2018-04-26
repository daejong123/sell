package com.daejong.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Daejong on 2017/9/9.
 */
@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public String sayHello() {

        return "hello world...";
    }
}
