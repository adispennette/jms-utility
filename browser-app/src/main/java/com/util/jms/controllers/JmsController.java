package com.util.jms.controllers;

import com.util.jms.JmsUtility;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@CrossOrigin(exposedHeaders = "errors, content-type")
@RequestMapping("/api/v1")
public class JmsController {
    private static final Logger log = LoggerFactory.getLogger(JmsController.class);
    private final JmsUtility utility;

    @Inject
    public JmsController(JmsUtility utility) {
        this.utility = utility;
    }

    @ApiOperation(value = "Send a message to a queue.", response = String.class)
    @PostMapping(value = "/{queueName}/send", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String send(@PathVariable String queueName, @RequestBody String payload){
        try {
            log.info(queueName);
            log.info(payload);
            utility.send(queueName, payload);
        } catch (Exception e) {
            return "Send failed";
        }
        return "sent";
    }

}
