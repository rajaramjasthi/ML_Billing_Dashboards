package com.billingreports.repositories.gcp;

import com.billingreports.entities.gcp.Gcp;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@EnableMongoRepositories
public interface GcpRespository extends MongoRepository<Gcp, String> {

    @Query(value = "{'ServiceDescription' : {$exists : true}}", fields = "{'ServiceDescription' : 1, '_id':0}")
    List<String> findDistinctServiceDescriptionBy();

    // to get all data between dates and months
    @Query("{ 'date': { $gte: ?0, $lte: ?1 } }")
    List<Gcp> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    // to get the data based on the serviceDesp and date range
    @Query("{'ServiceDescription': ?0, 'date': { $gte: ?1, $lte: ?2 } }")
    List<Gcp> findByServiceDescriptionAndDateRange(String serviceDescription, LocalDateTime startDate, LocalDateTime endDate);


}

