package com.maven.student.domain.services;

import com.maven.student.application.dto.ObjectStudent;
import org.springframework.stereotype.Service;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maven.student.application.usecases.StudentPublisherService;
import com.maven.student.application.dto.StudentDto;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * Implementation of the StudentPublisherService interface.
 * This service is responsible for publishing student information to Azure Service Bus.
 *
 * @author Joseph Magallanes
 * @since 2025-11-30
 */
@Service
@RequiredArgsConstructor
public class PersonPublisherServiceImpl implements StudentPublisherService {

    private final ServiceBusSenderAsyncClient sender;
    private final ObjectMapper mapper;

    @Override
    public Mono<Void> publish(StudentDto student) {
        final String json;
        try {
            json = mapper.writeValueAsString(student);
        }
        catch (JsonProcessingException e) {
            return Mono.error(e);
        }

        final ServiceBusMessage message = new ServiceBusMessage(json)
            .setContentType("application/json")
            .setSubject("person-created");

        return Mono.fromFuture(sender.sendMessage(message).toFuture());
    }

    @Override
    public Mono<Void> publishObject(ObjectStudent student) {
        final String json;
        try {
            json = mapper.writeValueAsString(student);
        }
        catch (JsonProcessingException e) {
            return Mono.error(e);
        }

        final ServiceBusMessage message = new ServiceBusMessage(json)
            .setContentType("application/json")
            .setSubject("person-created");

        return Mono.fromFuture(sender.sendMessage(message).toFuture());
    }
}
