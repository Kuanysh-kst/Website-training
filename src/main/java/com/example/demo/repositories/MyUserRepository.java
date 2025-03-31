package com.example.demo.repositories;

import com.example.demo.models.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByFirstname(String userName);

    Optional<MyUser> findByEmail(String email);
}
