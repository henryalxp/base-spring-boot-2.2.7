package com.aflac.demo.presentation.service.impl;

import java.util.List;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aflac.demo.domain.model.Claim;
import com.aflac.demo.persistence.dynamodb.repository.ClaimRepository;
import com.aflac.demo.presentation.service.ClaimService;

@Service
public class ClaimServiceImpl implements ClaimService {

  @Autowired
  private ClaimRepository claimRepository;

  @Override
  public List<Claim> getClaims() {
    return IterableUtils.toList(claimRepository.findAll());
  }

}
