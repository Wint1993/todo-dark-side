package com.iteratec.todo.bc.user.service;

import com.iteratec.todo.bc.user.dao.UserRepo;
import com.iteratec.todo.bc.user.dao.entity.User;
import com.iteratec.todo.bc.user.service.dto.UserDTO;
import com.iteratec.todo.tech.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    public List<UserDTO> findAll() {
        return userRepo.findAll().stream().map(UserDTO::new).collect(toList());
    }

    public UserDTO findById(Long id) {
        return userRepo.findById(id).map(UserDTO::new).orElse(null);
    }

    public UserDTO findByUsername(String username) {
        return new UserDTO(findUserEntityByUsername(username));
    }

    public User findUserEntityByUsername(String username) {
        return userRepo.findByUsername(username).orElseThrow(() -> new ResourceNotFoundException("User with username: " + username + " not found."));
    }

    public boolean checkIfLoggedIn() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName().equals("admin")){
            return true;
        }
        return false;
    }

    public UserDTO checkIfLoggedIn1(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(userRepo.findByUsername(auth.getName()) != null){
            return findByUsername(auth.getName());
        } else {
            return null;
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepo.findOneByUsername(username);
        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),getAuthority());
    }

    private List<SimpleGrantedAuthority> getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
