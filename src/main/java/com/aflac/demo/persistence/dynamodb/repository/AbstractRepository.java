package com.aflac.demo.persistence.dynamodb.repository;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;

public abstract class AbstractRepository<T, ID extends Serializable> {

  protected DynamoDBMapper mapper;
  protected Class<T> entityClass;

  @SuppressWarnings("unchecked")
  protected AbstractRepository() {
    ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
    this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
  }

  public void save(T t) {
    mapper.save(t);
  }

  public T findOne(ID id) {
    return mapper.load(entityClass, id);
  }

  /**
   * <strong>WARNING:</strong> It is not recommended to perform full table scan targeting the real
   * production environment.
   *
   * @return All items
   */
  public List<T> findAll() {
    DynamoDBScanExpression scanExpression = new DynamoDBScanExpression();
    return mapper.scan(entityClass, scanExpression);
  }

  public void setMapper(DynamoDBMapper dynamoDBMapper) {
    this.mapper = dynamoDBMapper;
  }

}
