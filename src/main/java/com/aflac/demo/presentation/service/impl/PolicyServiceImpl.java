package com.aflac.demo.presentation.service.impl;

import java.util.List;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aflac.demo.domain.model.Policy;
import com.aflac.demo.persistence.dynamodb.repository.PolicyRepository;
import com.aflac.demo.presentation.service.PolicyService;

@Service
public class PolicyServiceImpl implements PolicyService {

  @Autowired
  private PolicyRepository policyRepository;

  @Override
  public List<Policy> getPolicies() {
    return IterableUtils.toList(policyRepository.findAll());
  }

}
