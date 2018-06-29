package com.iteratec.todo.bc.image.dao.entity;


import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table(name = "image_img")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Lob
    private byte[] image;

    public Image(byte[] image) {
        this.image = image;
    }

    public Image(){}


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

}
