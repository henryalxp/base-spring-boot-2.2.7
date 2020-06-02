package com.aflac.demo.presentation.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@CrossOrigin
@Controller
public class DemoController {

  @GetMapping("/greet")
  public ResponseEntity<?> handleFileConvert(@RequestParam("name") String name) {

    return ResponseEntity.ok(String.format("Hello %s", name));

  }

}
