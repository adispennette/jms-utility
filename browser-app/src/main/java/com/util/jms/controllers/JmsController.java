package com.util.jms.controllers;

import com.util.jms.JmsUtility;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

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

    @ApiOperation(value = "Configure the dynamic connection factory.", response = String.class)
    @PostMapping(value = "/configure", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> configure(@RequestParam(value = "host", defaultValue = "tcp://localhost:61616") String host,
                                            @RequestParam(value = "principle", defaultValue = "admin") String principle,
                                            @RequestParam(value = "credential", defaultValue = "admin") String credential) {
        try {
            utility.reload(host, principle, credential);
            return new ResponseEntity<>("Success, Host updated to " + host, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            log.error("An error occurred while browsing queue.", e);
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ApiOperation(value = "Send a message to a queue.", response = String.class)
    @PostMapping(value = "/{queueName}/send", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<String> send(@PathVariable String queueName, @RequestBody String payload) {
        try {
            log.info(queueName);
            log.info(payload);
            utility.send(queueName, payload);
        } catch (Exception e) {
            return new ResponseEntity<>("Send failed " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("sent", HttpStatus.OK);
    }

    @ApiOperation(value = "Browse messages on a queue.", response = String.class)
    @GetMapping(value = "/browse", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<String>> browse(@RequestParam(value = "queueName") String queueName) {
        try {
            log.info(queueName);
            return new ResponseEntity<>(utility.browse(queueName), HttpStatus.OK);
        } catch (Exception e) {
            log.error("An error occurred while browsing queue.", e);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.NO_CONTENT);
    }

}
