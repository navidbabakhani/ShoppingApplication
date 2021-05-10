package com.egs.shoppingapplication.service;

import com.egs.shoppingapplication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Collections;

@Service
public class UserAuthService implements UserDetailsService {

    private final String ROLE_PREFIX = "ROLE_";
    private final UserRepository repository;

    @Autowired
    public UserAuthService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByEmail(username)
                .map(user -> new User(
                        user.getUsername(),
                        user.getPassword(),
                        user.isActive(), true, true, user.isActive(),
                        Collections.singletonList(new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().name()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException(
                        MessageFormat.format("User with username {0} is not found.", username)));
    }
}
