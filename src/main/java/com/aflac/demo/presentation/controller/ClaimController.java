package com.aflac.demo.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.aflac.demo.persistence.dynamodb.repository.ClaimRepository;

@CrossOrigin
@Controller
public class ClaimController {

  @Autowired
  private ClaimRepository claimRepository;

  @GetMapping("/claims")
  public ResponseEntity<?> getClaims() {

    // XXX move to a service
    return ResponseEntity.ok(claimRepository.findAll());

  }


}
