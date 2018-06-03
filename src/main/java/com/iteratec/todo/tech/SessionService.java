package com.iteratec.todo.tech;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

    public String getUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

}