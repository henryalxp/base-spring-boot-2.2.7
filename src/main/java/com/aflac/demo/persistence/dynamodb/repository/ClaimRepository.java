package com.aflac.demo.persistence.dynamodb.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.stereotype.Component;
import com.aflac.demo.domain.model.Claim;

@EnableScan
@Component
public class ClaimRepository extends AbstractRepository<Claim, String> {

}
