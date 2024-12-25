package com.example.demo.repositories;

import com.example.demo.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByName(String userName);

    Boolean existsByName(String name);

    Boolean existsByEmail(String email);
}
