package com.aflac.demo.persistence.dynamodb.repository;

import java.util.Optional;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import com.aflac.demo.domain.model.Policy;

@EnableScan
public interface PolicyRepository extends CrudRepository<Policy, String> {

  Optional<Policy> findById(String id);

}
