package com.iteratec.todo.bc.todo.service;

import com.iteratec.todo.bc.todo.dao.TodoRepo;
import com.iteratec.todo.bc.todo.dao.entity.Todo;
import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import com.iteratec.todo.bc.user.service.UserService;
import com.iteratec.todo.tech.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private UserService userService;

    public List<TodoBasicDTO> findAll() {
        return todoRepo.findAll().stream().filter(x -> !x.isDeleteStatus()).map(TodoBasicDTO::new).collect(toList());
    }

    public boolean ifExistsWithTheSameCreationDate(LocalDateTime localDateTime){
           if(todoRepo.findByCreadtionDate(localDateTime).isEmpty())
           {
               return false;
           }
           return true;
    }

    public List<TodoBasicDTO> findByUsername(String username) {
        System.out.println();
        return todoRepo.findByUsername(username).stream().filter(x ->!x.isDeleteStatus()).map(TodoBasicDTO::new).collect(toList());
    }

    public List<TodoBasicDTO> findFutureByUsername(String username) {
        return todoRepo.findByUsername(username).stream().filter(x ->!x.isDeleteStatus()).filter(x-> x.getDate().compareTo(LocalDate.now()) >= 0).map(TodoBasicDTO::new).collect(toList());
    }

    public List<TodoBasicDTO> findOldByUsername(String username) {
        return todoRepo.findByUsername(username).stream().filter(x ->!x.isDeleteStatus()).filter(x-> x.getDate().compareTo(LocalDate.now()) < 0).map(TodoBasicDTO::new).collect(toList());
    }
    public TodoBasicDTO findById(Long id) {
        return new TodoBasicDTO(findTodoEntityById(id));
    }

    public TodoBasicDTO create(TodoBasicDTO dto, String username) {
        return new TodoBasicDTO(todoRepo.save(new Todo(dto, userService.findUserEntityByUsername(username))));
    }

    public TodoBasicDTO update(Long id, TodoBasicDTO dto, String username) {
        Todo todo = todoRepo.findByIdAndUsername(id, username).orElseThrow(() -> new ResourceNotFoundException("No TODO with id " + id + " found."));

        todo.setDate(dto.getDate());
        todo.setDescription(dto.getDescription());

        return new TodoBasicDTO(todoRepo.save(todo));
    }

    private Todo findTodoEntityById(Long id) {
        return todoRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("No TODO with id " + id + " found."));
    }

    public Long delete(Long id) {
        Optional<Todo> todo = todoRepo.findById(id);
        if(todo.get().getCreadtionDate() != null){
            todo.get().setDeleteStatus(true);
            todoRepo.save(todo.get());
        }else{
            todoRepo.delete(findTodoEntityById(id));
        }
        return id;
    }
}
