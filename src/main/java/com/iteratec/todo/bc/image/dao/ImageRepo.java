package com.iteratec.todo.bc.image.dao;

import com.iteratec.todo.bc.image.dao.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepo extends JpaRepository<Image,Long> {



}
