package com.iteratec.todo.bc.todo.batch;

import com.iteratec.todo.bc.todo.service.TodoService;
import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Writer implements ItemWriter<List<TodoBasicDTO>> {

    @Autowired
    private TodoService todoService;

    @Override
    public void write(List<? extends List<TodoBasicDTO>> todoBasicDTOS) throws Exception {

        for (List<TodoBasicDTO> todo: todoBasicDTOS) {
            for(TodoBasicDTO t1 : todo){
                todoService.create(t1,"admin");
            }
        }
    }
}
