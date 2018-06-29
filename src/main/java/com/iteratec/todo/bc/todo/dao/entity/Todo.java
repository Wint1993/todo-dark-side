package com.iteratec.todo.bc.todo.dao.entity;

import com.iteratec.todo.bc.image.dao.entity.Image;
import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import com.iteratec.todo.bc.user.dao.entity.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @SequenceGenerator(name = "id_seq", sequenceName = "id_seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_seq")
    private Long id;

    private LocalDate date;
    private LocalDateTime creationDate;
    private String description;

    //@Column
   // @Lob
 //   @Basic(fetch = FetchType.LAZY)
   // private byte[] image;

    @Column(name = "deletestatus")
    private boolean deleteStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    //@OneToOne(mappedBy = "todo",fetch = FetchType.LAZY)
    //@JoinColumn(name = "useroauth_id")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "image_id")
    private Image image;


    public Todo() {
    }

    public Todo(TodoBasicDTO dto, User user) {
        this.date = dto.getDate();
        this.description = dto.getDescription();
        this.creationDate = dto.getCreadtionDate();
        this.user = user;
    }

    public Todo(TodoBasicDTO dto, User user, Image image) {
        this.date = dto.getDate();
        this.description = dto.getDescription();
        this.creationDate = dto.getCreadtionDate();
        this.user = user;
        this.image = image;
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

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }


}
