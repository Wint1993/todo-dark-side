package com.iteratec.todo.bc.todo.batch;

import com.google.api.services.calendar.model.Event;
import com.iteratec.todo.bc.todo.service.dto.TodoBasicDTO;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@Configuration
@EnableBatchProcessing
@EnableScheduling
public class BatchConfiguration {

   @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    public Processor processor;

    @Autowired
    public Reader reader;

    @Autowired
    public Writer writer;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("job")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

   @Bean
    public Step step1(){
        return stepBuilderFactory.get("step1")
                .<List<Event>,List<TodoBasicDTO>> chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}
