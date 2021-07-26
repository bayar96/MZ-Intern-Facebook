package com.pgs.news.service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.pgs.news.domain.News;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.ion.Timestamp;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
public class AmazonClient {

    private DynamoDBMapper mapper;
    @Value("${amazonProperties.region}")
    private String region;
    @Value("${amazonProperties.tableName}")
    private String tableName;

    @PostConstruct
    public void initialize() {
        AmazonDynamoDB dynamoDbClient = AmazonDynamoDBClientBuilder
                .standard()
                .withRegion(region)
                .build();
        mapper = new DynamoDBMapper(dynamoDbClient);
    }

    public List<News> getNewsFromLastXDays(int lastDays) {
        Timestamp timestamp = Timestamp.now().addDay(-lastDays);
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(timestamp.toString()));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("CreateDate > :val1").withExpressionAttributeValues(eav);
        return mapper.scan(News.class, scanExpression)
                .stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
    }

    public Optional<News> getNewsById(String id) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1", new AttributeValue().withS(id));
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("Id = :val1").withExpressionAttributeValues(eav);
        List<News> newsList = mapper.scan(News.class, scanExpression);
        if (!newsList.isEmpty()) {
            return Optional.of(newsList.get(0));
        } else {
            return Optional.empty();
        }
    }

    public void createNews(News news) {
        mapper.save(news);
    }

    public void deleteNews(News news) {
        mapper.delete(news);
    }
}
