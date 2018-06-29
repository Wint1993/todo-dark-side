package com.iteratec.todo.bc.todo.service;

import com.iteratec.todo.bc.image.dao.ImageRepo;
import com.iteratec.todo.bc.image.dao.entity.Image;
import com.iteratec.todo.bc.ouathusers.dao.entity.OAuthUser;
import com.iteratec.todo.bc.todo.dao.TodoRepo;
import com.iteratec.todo.bc.todo.dao.entity.Todo;
import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import com.iteratec.todo.bc.user.dao.entity.User;
import com.iteratec.todo.bc.user.service.UserService;
import com.iteratec.todo.tech.exception.ResourceNotFoundException;
import org.apache.commons.io.FileUtils;
import org.postgresql.PGConnection;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static java.util.stream.Collectors.toList;

@Service
public class TodoService  {

    @Autowired
    private TodoRepo todoRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ImageRepo imageRepo;

    public List<TodoBasicDTO> findAll() {
        return todoRepo.findAll().stream().filter(x -> !x.isDeleteStatus()).map(TodoBasicDTO::new).collect(toList());
    }

    public List<TodoBasicDTO> findAllByUserId(User user) {
        return todoRepo.findAllByUser(user).stream().map(TodoBasicDTO::new).collect(toList());
    }

    public boolean ifExistsWithTheSameCreationDate(LocalDateTime localDateTime){
           if(todoRepo.findByCreadtionDate(localDateTime).isEmpty())
           {
               return false;
           }
           return true;
    }

    public List<TodoBasicDTO> findByUsername(String username) {
        return todoRepo.findByUsername(username).stream().filter(x ->!x.isDeleteStatus()).map(TodoBasicDTO::new).collect(toList());
    }

    public List<TodoBasicDTO> findFutureByUsername(String username) {
        //return todoRepo.findByUsername(username).stream().filter(x ->!x.isDeleteStatus()).filter(x-> x.getDate().compareTo(LocalDate.now()) >= 0).map(TodoBasicDTO::new).collect(toList());
        return todoRepo.findByUsername(username).stream().map(TodoBasicDTO::new).collect(toList());
    }

    public List<TodoBasicDTO> findOldByUsername(String username) {
        return todoRepo.findByUsername(username).stream().filter(x ->!x.isDeleteStatus()).filter(x-> x.getDate().compareTo(LocalDate.now()) < 0).map(TodoBasicDTO::new).collect(toList());
    }
    public TodoBasicDTO findById(Long id) throws SQLException {
      //  byte[] result = findTodoEntityById(id).getImage();

        String url = "jdbc:postgresql://localhost:5432/iteratec";
        Properties props = new Properties();
        props.setProperty("user","postgres");
        props.setProperty("password","");
        //props.setProperty("ssl","true");
        Connection conn = DriverManager.getConnection(url, props);
        PreparedStatement ps = conn.prepareStatement("SELECT image FROM image_img WHERE id = ?");
        ps.setInt(1, 1);
        ResultSet rs = ps.executeQuery();
        byte[] imgBytes1 = null;
        while (rs.next()) {
            byte[] imgBytes = rs.getBytes(1);
            imgBytes1 = imgBytes;
            // use the data in some way here
        }
        rs.close();
        ps.close();

        PreparedStatement pa = conn.prepareStatement("SELECT image FROM image_img WHERE id = ?");
        pa.setInt(1, 1);

        ResultSet rb = pa.executeQuery();
        LargeObjectManager lobj = ((org.postgresql.PGConnection)conn).getLargeObjectAPI();
        while (rb.next()) {
            // Open the large object for reading
            int oid = rb.getInt(1);
            LargeObject obj = lobj.open(oid, LargeObjectManager.READ,true);

            // Read the data
            byte buf[] = new byte[obj.size()];
            obj.read(buf, 0, obj.size());
            // Do something with the data read here

            // Close the object
            obj.close();
        }
        rb.close();
        pa.close();


        Todo todo = todoRepo.findOneById(id);
        byte[] image = todo.getImage().getImage();
        return new TodoBasicDTO(findTodoEntityById(id));
    }


    public TodoBasicDTO create(TodoBasicDTO dto, String username) throws IOException {

        byte[] example = FileUtils.readFileToByteArray(new File("C:\\Users\\brejnowski\\Desktop\\myfile.txt"));
        Image image = new Image(example);
        image.setImage(example);
        imageRepo.save(image);

        return new TodoBasicDTO(todoRepo.save(new Todo(dto, userService.findUserEntityByUsername(username),image)));
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
        if(todo.get().getCreationDate() != null){
            todo.get().setDeleteStatus(true);
            todoRepo.save(todo.get());
        }else{
            todoRepo.delete(findTodoEntityById(id));
        }
        return id;
    }


}
