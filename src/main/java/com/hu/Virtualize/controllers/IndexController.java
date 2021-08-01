package com.hu.Virtualize.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@CrossOrigin("*")
public class IndexController {

    /**
     * This function will help you to check the health status of backend.
     * @return status
     */
    @GetMapping({"","/"})
    public String index() {
        log.info("Successfully run health checker");
        return "Hello virtualize";
    }
}
