package com.api.api.repositories;

import java.util.List;

import com.api.api.models.User;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{$or :[{loginId: ?0},{email: ?1}]}")
    List<User> getUserByLoginIdOrEmail(String loginId, String email);
}
