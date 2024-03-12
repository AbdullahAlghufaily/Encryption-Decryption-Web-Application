package com.youtube.jwt.dao;

import com.youtube.jwt.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserDao extends CrudRepository<User,String> {
    Optional<User> findByuserName(String username);

    Optional<User> findByuserEmail(String Email);
}
