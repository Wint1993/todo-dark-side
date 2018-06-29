package com.iteratec.todo.bc.todo.rest;

import com.iteratec.todo.bc.ouathusers.dao.OAuthUserRepo;
import com.iteratec.todo.bc.ouathusers.dao.entity.OAuthUser;
import com.iteratec.todo.bc.todo.service.TodoService;
import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import com.iteratec.todo.tech.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/rest/todo")
public class TodoRest {

    @Autowired
    private TodoService todoService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private OAuthUserRepo oAuthUserRepo;

    @RequestMapping(path = "all", method = RequestMethod.GET)
    public List<TodoBasicDTO> findAll() {
        return todoService.findAll();
    }

    @RequestMapping(path = "all1", method = RequestMethod.GET)
    public List<TodoBasicDTO> findByAuth(OAuth2Authentication auth) {
        String userOauth2 = auth.getUserAuthentication().getName();
        OAuthUser oAuthUser = oAuthUserRepo.findOneByUsername(userOauth2);
        return todoService.findAllByUserId(oAuthUser.getUser());
    }

    @RequestMapping(path = "my",method = RequestMethod.GET)
    public List<TodoBasicDTO> findAllLoggedUser() {
        return todoService.findByUsername(sessionService.getUsername());
    }

    @RequestMapping(path = "future",method = RequestMethod.GET)
    public List<TodoBasicDTO> findAllFutureLoggedUser() {
        String c = "";
        return todoService.findFutureByUsername(sessionService.getUsername());
    }

    @RequestMapping(path = "history",method = RequestMethod.GET)
    public List<TodoBasicDTO> findAllOldTodosByLoggedUser() {
        return todoService.findOldByUsername(sessionService.getUsername());
    }

    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public TodoBasicDTO findById(@PathVariable("id") Long id) throws SQLException {

        return todoService.findById(id);
    }

    @RequestMapping(path = "create", method = RequestMethod.PUT)
    public TodoBasicDTO create(@RequestBody TodoBasicDTO dto) throws IOException {
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
