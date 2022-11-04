package com.dgpay.firstspringproject.repositories;

import com.dgpay.firstspringproject.models.StudentDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface StudentMongoRepository extends
        MongoRepository<StudentDocument,String>, QuerydslPredicateExecutor<StudentDocument> {

}
