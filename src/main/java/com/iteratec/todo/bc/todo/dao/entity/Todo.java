package com.iteratec.todo.bc.todo.dao.entity;

import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import com.iteratec.todo.bc.user.dao.entity.User;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate date;
    private LocalDateTime creadtionDate;
    private String description;

    @Column(name = "deletestatus")
    private boolean deleteStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Todo() {
    }

    public Todo(TodoBasicDTO dto, User user) {
        this.date = dto.getDate();
        this.description = dto.getDescription();
        this.creadtionDate = dto.getCreadtionDate();
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getCreadtionDate() {
        return creadtionDate;
    }

    public void setCreadtionDate(LocalDateTime creadtionDate) {
        this.creadtionDate = creadtionDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }
}
