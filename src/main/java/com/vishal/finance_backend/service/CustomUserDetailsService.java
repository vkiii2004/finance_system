package com.vishal.finance_backend.service;

import com.vishal.finance_backend.Entity.User;
import com.vishal.finance_backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException{
        User user = userRepository.findByUsername(username)
                .orElseThrow(()->
                        new UsernameNotFoundException("user not found"));

        if (!user.isActive()) {
            throw new UsernameNotFoundException("User is inactive");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(
                        new SimpleGrantedAuthority(
                                "ROLE_" + user.getRole().getName().name()
                        )
                )
        );
    }

}
