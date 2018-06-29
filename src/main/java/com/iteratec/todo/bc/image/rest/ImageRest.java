package com.iteratec.todo.bc.image.rest;

import com.iteratec.todo.bc.image.dao.entity.Image;
import com.iteratec.todo.bc.image.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/rest/image")
public class ImageRest {

    @Autowired
    private ImageService imageService;

    @RequestMapping(path = "create", method = RequestMethod.PUT)
    public Image create(@RequestBody String description, Long id) throws IOException {
        return imageService.create(description,id);
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public Image findById(@PathVariable("id") Long id) {
        return imageService.findById(id);
    }

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public List<Image> findAll() {
        return imageService.findAll();
    }

}
