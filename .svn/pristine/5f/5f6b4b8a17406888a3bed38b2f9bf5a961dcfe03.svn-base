package com.webdb.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;


@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration  {
	 
    @Value("${mongodb.username}")     //application.properties에서 정의한 MongoDB에 계정 아이디
    private String userName;
    
    
    @Value("${mongodb.password}")    //application.properties에서 정의한 MongoDB에 계정 비밀번호
    private String password;

    
    @Value("${mongodb.database}") //application.properties에서 정의한 MongoDB에있는 데이터베이스
    private String database;
    
    @Value("${mongodb.uri}") //application.properties에서 정의한 MongoDB에있는 데이터베이스
    private String mongoUri;
    
    
    @Override
    protected String getDatabaseName() {
        return database;
    }
    
    @Override
    protected void configureClientSettings(MongoClientSettings.Builder builder) {
        // customization hook
        builder.applyConnectionString(new ConnectionString(mongoUri));
    }

    public @Bean MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate((MongoClient) mongoClient(), database);
    }
}
