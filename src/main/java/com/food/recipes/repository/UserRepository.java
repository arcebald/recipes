package com.food.recipes.repository;

import com.food.recipes.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    //to register
    boolean existsByEmailAddress(String userEmailAddress);

    //to login
    User findUserByEmailAddress(String emailAddress);
}
