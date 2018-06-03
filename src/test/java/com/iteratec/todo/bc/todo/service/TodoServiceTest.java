package com.iteratec.todo.bc.todo.service;

import com.iteratec.todo.Application;
import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import com.iteratec.todo.bc.user.dao.entity.User;
import com.iteratec.todo.bc.user.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class TodoServiceTest {

    private static final Long USER_ID = 2L;
    private static final String USERNAME = "t";
    private static final User USER = new User(USER_ID, USERNAME);

    @InjectMocks
    @Autowired
    protected TodoService testedObject;

    @Spy
    @Autowired
    private UserService userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        Mockito.doReturn(USER).when(userService).findUserEntityByUsername(USERNAME);
    }

    @Test
    public void createShouldCreateTodo() {
        // given
        final String description = "Testowy opis";
        final LocalDate date = LocalDate.now();
        final LocalDateTime date1 = LocalDateTime.now();

        // when
        TodoBasicDTO result = testedObject.create(new TodoBasicDTO(date,date1, description), USERNAME);

        // when
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(date, result.getDate());
        assertEquals(description, result.getDescription());
    }
}