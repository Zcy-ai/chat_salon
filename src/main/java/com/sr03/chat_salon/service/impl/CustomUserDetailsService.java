package com.sr03.chat_salon.service.impl;

import com.sr03.chat_salon.model.User;
import com.sr03.chat_salon.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {
        User user = userService.findUserByLogin(login);
        return user;
    }

//    @Transactional
//    public UserDetails loadUserById(Long id) {
//        User user = userService.findU(id).orElseThrow(
//                () -> new ResourceNotFoundException("User", "id", id)
//        );
//
//        return UserPrincipal.create(user);
//    }
}