package com.maven.student.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusSenderAsyncClient;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * ServiceBusConfig.
 * This configuration class sets up the Azure Service Bus sender client and ObjectMapper bean.
 *
 * @author Joseph Magallanes
 * @since 2025-11-30
 */
@Configuration
public class ServiceBusConfig {

    @Value("${azure.servicebus.connection-string}")
    private String connectionString;

    @Value("${azure.servicebus.topic-name}")
    private String topicName;

    /**
     * Bean ServiceBusSenderAsyncClient.
     * @return ServiceBusSenderAsyncClient
     *
     * @author Joseph Magallanes
     * @since 2025-11-30
     */
    @Bean
    public ServiceBusSenderAsyncClient serviceBusSenderAsyncClient() {
        return new ServiceBusClientBuilder()
            .connectionString(connectionString)
            .sender()
            .topicName(topicName)
            .buildAsyncClient();
    }

    /**
     * Bean ObjectMapper.
     * @return ObjectMapper
     *
     * @author Joseph Magallanes
     * @since 2025-11-30
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
