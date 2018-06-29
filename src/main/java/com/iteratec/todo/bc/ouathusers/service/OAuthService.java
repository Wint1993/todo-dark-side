package com.iteratec.todo.bc.ouathusers.service;


import com.iteratec.todo.bc.ouathusers.dao.OAuthUserRepo;
import com.iteratec.todo.bc.ouathusers.dao.entity.OAuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service(value ="userDetailsService")
public class OAuthService implements UserDetailsService
{
    @Autowired
    private OAuthUserRepo authUserRepo;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        OAuthUser user = authUserRepo.findOneByUsername(s);
        return new User(user.getUsername(),user.getPassword(),getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }

}
