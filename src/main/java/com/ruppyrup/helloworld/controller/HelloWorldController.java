package com.ruppyrup.helloworld.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class HelloWorldController {

    @GetMapping("/")
    public ResponseEntity<String> start() {
        log.info("Start endpoint pinged");
        String output = """
                { "message": "Try /helloworld | /hello/{name} | /ipaddress" }
                """;
        return new ResponseEntity<>(output, HttpStatus.OK);
    }
    @GetMapping("/helloworld")
    public ResponseEntity<String> helloWorld() {
        log.info("Hello world got pinged");
        String output = """
                { "message": "Hello from Hello-World" }
                """;
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/hello/{name}")
    public ResponseEntity<String> getFooById(@PathVariable String name) {
        log.info("Hello got pinged with {}", name);
        String output = """
                { "message": "Hello %s" }
                """.formatted(name);
        return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/ipaddress")
    public ResponseEntity<String>  getFooById() {
        try {
            final String pageContents = this.getPageContents("https://checkip.amazonaws.com");
            String output = String.format("{ \"message\": \"hello world\", \"location\": \"%s\" }", pageContents);
            log.info(output);
            return new ResponseEntity<>(output, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private String getPageContents(String address) throws IOException {
        URL url = new URL(address);
        try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }
}
