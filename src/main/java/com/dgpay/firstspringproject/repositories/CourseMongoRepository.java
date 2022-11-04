package com.dgpay.firstspringproject.repositories;

import com.dgpay.firstspringproject.models.CourseDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CourseMongoRepository extends
        MongoRepository<CourseDocument,String>, QuerydslPredicateExecutor<CourseDocument> {
}
