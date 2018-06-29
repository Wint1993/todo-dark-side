package com.iteratec.todo.bc.todo.rest;

import com.iteratec.todo.bc.ouathusers.dao.OAuthUserRepo;
import com.iteratec.todo.bc.todo.service.TodoService;
import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import com.iteratec.todo.tech.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/other")
public class OtherRest {

    @Autowired
    private TodoService todoService;

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public List<TodoBasicDTO> findAll() {
        return todoService.findAll();
    }

}
