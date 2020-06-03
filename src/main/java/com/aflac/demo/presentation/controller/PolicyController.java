package com.aflac.demo.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import com.aflac.demo.presentation.service.PolicyService;

@CrossOrigin
@Controller
public class PolicyController {

  private PolicyService policyService;

  @Autowired
  public void setPolicyService(PolicyService policyService) {
    this.policyService = policyService;
  }

  @GetMapping("/policies")
  public ResponseEntity<?> getClaims() {

    return ResponseEntity.ok(policyService.getPolicies());

  }

}
