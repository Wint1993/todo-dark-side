package com.iteratec.todo.bc.todo.rest;

import com.iteratec.todo.bc.todo.service.TodoService;
import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import com.iteratec.todo.tech.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rest/todo")
public class TodoRest {

    @Autowired
    private TodoService todoService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public List<TodoBasicDTO> findAll() {
        return todoService.findAll();
    }

    @RequestMapping(path = "my",method = RequestMethod.GET)
    public List<TodoBasicDTO> findAllLoggedUser() {
        return todoService.findByUsername(sessionService.getUsername());
    }

    @RequestMapping(path = "future",method = RequestMethod.GET)
    public List<TodoBasicDTO> findAllFutureLoggedUser() {
        return todoService.findFutureByUsername(sessionService.getUsername());
    }

    @RequestMapping(path = "history",method = RequestMethod.GET)
    public List<TodoBasicDTO> findAllOldTodosByLoggedUser() {
        return todoService.findOldByUsername(sessionService.getUsername());
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public TodoBasicDTO findById(@PathVariable("id") Long id) {
        return todoService.findById(id);
    }

    @RequestMapping(path = "create", method = RequestMethod.PUT)
    public TodoBasicDTO create(@RequestBody TodoBasicDTO dto) {
        return todoService.create(dto, sessionService.getUsername());
    }

    @RequestMapping(path = "update/{id}", method = RequestMethod.POST)
    public TodoBasicDTO update(@PathVariable("id") Long id, @RequestBody TodoBasicDTO dto) {
        return todoService.update(id, dto, sessionService.getUsername());
    }

    @RequestMapping(path = "delete/{id}", method = RequestMethod.DELETE)
    public Long delete(@PathVariable("id") Long id) {
        return todoService.delete(id);
    }

}
