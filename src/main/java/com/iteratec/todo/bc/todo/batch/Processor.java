package com.iteratec.todo.bc.todo.batch;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.iteratec.todo.bc.todo.service.GoogleService;
import com.iteratec.todo.bc.todo.service.TodoService;
import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Processor implements ItemProcessor<List<Event>,List<TodoBasicDTO>> {

    @Autowired
    public GoogleService googleService;

    @Autowired
    private TodoService todoService;

    @Override
    public List<TodoBasicDTO> process(List<Event> events) throws Exception {
        System.out.println("Process");
        Logger logger = LoggerFactory.getLogger(this.getClass());

        return events.stream().
                filter(x -> !todoService.ifExistsWithTheSameCreationDate(convertToLocalDateTime(x.getCreated())))
                .map(x -> new TodoBasicDTO(convertToLocalDate(x.getStart().getDateTime()),convertToLocalDateTime(x.getCreated()),x.getSummary()))
                .collect(Collectors.toList());
    }

    private LocalDate convertToLocalDate(DateTime dateTime){
        long time = dateTime.getValue();
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private LocalDateTime convertToLocalDateTime(DateTime dateTime){
        long time = dateTime.getValue();
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
}
