package com.iteratec.todo.bc.image.service;

import com.iteratec.todo.bc.image.dao.ImageRepo;
import com.iteratec.todo.bc.image.dao.entity.Image;
import com.iteratec.todo.bc.todo.dao.TodoRepo;
import com.iteratec.todo.tech.exception.ResourceNotFoundException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepo imageRepo;

    public Image create (String description, Long id) throws IOException {
        byte[] example =  FileUtils.readFileToByteArray(new File("C:\\Users\\brejnowski\\Desktop\\myfile.txt"));
        Image image = new Image(example);
        return imageRepo.save(image);
    }

    public Image findById(Long id){
        return imageRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No Image with id " + id + " found."));
    }

    public List<Image> findAll() {
        return imageRepo.findAll();
    }


}
