package com.aflac.demo.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.aflac.demo.presentation.service.ClaimService;

@CrossOrigin
@Controller
public class ClaimController {

  private ClaimService claimService;

  @Autowired
  public void setClaimService(ClaimService claimService) {
    this.claimService = claimService;
  }

  @GetMapping("/claims")
  public ResponseEntity<?> getClaims() {

    return ResponseEntity.ok(claimService.getClaims());

  }


}
