package com.aflac.demo.persistence.dynamodb.repository;

import java.util.Optional;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import com.aflac.demo.domain.model.Claim;

@EnableScan
public interface ClaimRepository extends CrudRepository<Claim, String> {

  Optional<Claim> findById(String id);

}
