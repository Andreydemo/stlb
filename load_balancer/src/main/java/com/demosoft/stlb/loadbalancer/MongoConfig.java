package com.demosoft.stlb.loadbalancer;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

import java.util.Arrays;

/**
 * Created by Andrii on 3/15/2017.
 */
@Configuration
public class MongoConfig {


    @Bean
    public MongoClient mongo() {
        MongoClient mongoClient = new MongoClient(new ServerAddress("ds131890.mlab.com", 31890), Arrays.asList(MongoCredential.createCredential("admin1", "balancer_db", "admin1".toCharArray())));
        return mongoClient;
    }

    @Bean
    public MongoDbFactory mongoDbFactory() {
        UserCredentials credentials = new UserCredentials("admin1", "admin1");
        return new SimpleMongoDbFactory(mongo(), "balancer_db");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }
}