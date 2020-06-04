package com.aflac.demo.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.aflac.demo.presentation.service.ZipCodeService;

@CrossOrigin
@Controller
public class DemoController {

  @Autowired
  private ZipCodeService zipCodeService;

  @GetMapping("/zipcode")
  public ResponseEntity<?> handleFileConvert(@RequestParam("code") String zipCode) {

    return ResponseEntity.ok(zipCodeService.getZipInformation(zipCode).block());

  }

}
