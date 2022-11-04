package com.dgpay.firstspringproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableMongoRepositories
@EntityScan
public class FirstSpringProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(FirstSpringProjectApplication.class, args);
    }
//
//    @Bean
//    MongoTransactionManager mongoTransactionManager(MongoDatabaseFactory dbFactory){
//        return new MongoTransactionManager(dbFactory);
//    }
}
