package com.springboot.blog.repository;

import com.springboot.blog.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<Users, Long> {

    Optional<Users> findByUsernameOrEmail(String username, String email);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail( String email);
    Boolean existsByEmail( String email);
    Boolean existsByUsername(String username);
}
