package com.example.demo.services;

import com.example.demo.config.MyUserDetails;
import com.example.demo.models.MyUser;
import com.example.demo.repositories.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private MyUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = repository
                .findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return MyUserDetails.build(user);
    }
}
