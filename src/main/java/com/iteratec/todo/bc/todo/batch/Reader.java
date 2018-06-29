package com.iteratec.todo.bc.todo.batch;

import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.iteratec.todo.bc.todo.service.GoogleService;
import com.iteratec.todo.bc.todo.service.TodoService;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class Reader implements ItemReader<List<Event>> {

    @Autowired
    public GoogleService googleService;

    @Autowired
    private TodoService todoService;

    @Override
    public List<Event> read() throws Exception {
        if(googleService.transportEventsFromGoogle().stream().filter(x -> !todoService.ifExistsWithTheSameCreationDate(convertToLocalDateTime(x.getCreated()))).collect(Collectors.toList()).isEmpty()){
            return null;
        }
        return googleService.transportEventsFromGoogle();
    }

    private LocalDateTime convertToLocalDateTime(DateTime dateTime){
        long time = dateTime.getValue();
        return Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

}
