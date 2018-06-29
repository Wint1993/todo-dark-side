package com.iteratec.todo.bc.user.dao.entity;

import com.iteratec.todo.bc.ouathusers.dao.entity.OAuthUser;
import com.iteratec.todo.bc.todo.dao.entity.Todo;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "todo_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String role;
    private Boolean enabled;

    @OneToMany(mappedBy = "user")
    private List<Todo> todos = new ArrayList<>();

    @OneToOne(mappedBy = "user")
    //@JoinColumn(name = "useroauth_id")
    private OAuthUser oAuthUser;

    public User() {
    }

    public User(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public OAuthUser getoAuthUser() {
        return oAuthUser;
    }

    public void setoAuthUser(OAuthUser oAuthUser) {
        this.oAuthUser = oAuthUser;
    }
}
