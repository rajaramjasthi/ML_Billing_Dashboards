package com.billingreports.repositories.azure;

import com.billingreports.entities.azure.Azure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.List;

@EnableMongoRepositories
public interface AzureRepository extends MongoRepository<Azure,String> {
    // getting the data months and dates
    List<Azure> findByusageDateBetween(String startDate, String endDate);

    @Query(value = "{'ResourceType' : {$exists : true}}", fields = "{'ResourceType' : 1, '_id':0}")
    List<String> findDistinctResourceTypeBy();

//    @Aggregation("{ $match: { SubscriptionName: ?0 } }, { $group: { _id: '$ResourceType' } }")
    List<String> findDistinctResourceTypeBySubscriptionName(String subscriptionName);

    // to get the data based on the serviceDesp and date range
    @Query("{'resourceType': ?0, 'usageDate': {'$gte': ?1, '$lte': ?2}}")
    List<Azure> findByResourceTypeAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual(String resourseType, String startDate, String endDate);

    @Query("{'subscriptionName': ?0, 'usageDate': {'$gte': ?1, '$lte': ?2}}")
    List<Azure> findBySubscriptionNameAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual(String subscriptionName, String startDate, String endDate);

    @Query("{'resourceType': ?0, 'subscriptionName': ?1, 'usageDate': {'$gte': ?2, '$lte': ?3}}")
    List<Azure> findByResourceTypeAndSubscriptionNameAndUsageDateGreaterThanEqualAndUsageDateLessThanEqual(String resourceType, String subscriptionName, String startDate, String endDate);

    @Query("{'usageDate': {'$gte': ?0, '$lte': ?1}}")
    List<Azure> findByUsageDateGreaterThanEqualAndUsageDateLessThanEqual(String startDate, String endDate);

}
