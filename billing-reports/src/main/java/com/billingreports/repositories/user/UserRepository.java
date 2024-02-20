package com.billingreports.repositories.user;

import com.billingreports.entities.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Optional;

@EnableMongoRepositories
public interface UserRepository extends MongoRepository<User,String>{
    Optional<User> findByUserName(String userName);

    Optional<Object> findByEmail(String email);
}
