package com.aflac.demo.persistence.dynamodb.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import com.aflac.demo.domain.model.Claim;

@EnableScan
public class ClaimRepository extends AbstractRepository<Claim, String> {

}
