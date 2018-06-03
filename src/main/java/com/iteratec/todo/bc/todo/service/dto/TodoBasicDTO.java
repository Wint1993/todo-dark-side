package com.iteratec.todo.bc.todo.service.dto;

import com.iteratec.todo.bc.todo.dao.entity.Todo;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TodoBasicDTO {
    private Long id;
    private LocalDate date;
    private LocalDateTime creadtionDate;
    private String description;

    public TodoBasicDTO() {
    }

    public TodoBasicDTO(Todo todo) {
        this.id = todo.getId();
        this.date = todo.getDate();
        this.description = todo.getDescription();
        this.creadtionDate = todo.getCreadtionDate();
    }

    public TodoBasicDTO(LocalDate date,LocalDateTime creationDate ,String description) {
        this.date = date;
        this.description = description;
        this.creadtionDate = creationDate;
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


}
