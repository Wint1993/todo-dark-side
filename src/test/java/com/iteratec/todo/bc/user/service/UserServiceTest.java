package com.iteratec.todo.bc.user.service;

import com.iteratec.todo.Application;
import com.iteratec.todo.bc.user.service.dto.UserDTO;
import com.iteratec.todo.tech.exception.ResourceNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {

    @Autowired
    private UserService testedObject;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void findByIdShouldFindUserWithId_1() {
        // given
        final Long userId = 1L;

        // when
        UserDTO userDTO = testedObject.findById(userId);

        // then
        assertNotNull(userDTO);
        assertEquals(new Long(1L), userDTO.getId());
        assertEquals("t", userDTO.getUsername());
    }

    @Test
    public void findByUsernameShouldFindUserWithUsername_t() {
        // given
        final String username = "t";

        // when
        UserDTO userDTO = testedObject.findByUsername(username);

        // then
        assertNotNull(userDTO);
        assertEquals(username, userDTO.getUsername());
    }

    @Test
    public void findByUsernameShouldThrowResourceNotFoundException() {
        // given
        final String username = "mgocz";

        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("User with username: " + username + " not found.");

        // when
        testedObject.findByUsername(username);
    }
}