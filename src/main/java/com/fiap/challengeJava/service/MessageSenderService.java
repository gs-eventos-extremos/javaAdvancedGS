package com.fiap.challengeJava.service;


import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.challengeJava.dto.EmailDTO;
import com.fiap.challengeJava.service.exceptions.JsonConvertException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageSenderService {

    private final ObjectMapper objectMapper;

    @Value("${azure.connection-bus-send}")
    private String connectionBusSend;

    @Value("${azure.queue-name}")
    private String queueName;

    public MessageSenderService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String processAndSendMessage(EmailDTO email) {

        if (email.getEmail() == null || email.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        try {
            String messageJson = writeValueAsString(email);
            sendToAzureBus(messageJson);

            return messageJson;

        } catch (JsonProcessingException e) {
            throw new JsonConvertException(e.getMessage());
        }
    }

    public void sendToAzureBus(String messageJson) {
        ServiceBusSenderClient senderClient = new ServiceBusClientBuilder()
                .connectionString(connectionBusSend)
                .sender()
                .queueName(queueName)
                .buildClient();

        senderClient.sendMessage(new ServiceBusMessage(messageJson));
    }

    public String writeValueAsString(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

}
