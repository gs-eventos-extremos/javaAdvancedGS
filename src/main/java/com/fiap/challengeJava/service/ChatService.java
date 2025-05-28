package com.fiap.challengeJava.service;

import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.boot.actuate.health.HealthComponent;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final VertexAiGeminiChatModel chatModel;
    private final HealthEndpoint healthEndpoint;

    private static final String DEFAULT_MESSAGE =
            "PRECISO QUE SE APRESENTE DIZENDO QUE É UM AGENTE DE MONITORIA DO HEALTH DA APLICAÇÃO, O CheckBot, ALEM DISSO VOU TE PASSAR ALGUMAS INFORMAÇÕES SOBRE O HEALTH DA MINHA APLICAÇÃO, FORMULE UMA FRASE EXPLICANDO COMO ESTÁ O HEALTH DA MINHA APLICAÇÃO, ALEM DISSO INFORME QUE SE CASO DESEJA-SE MAIS INFORMAÇÕES PODERIA ACESSAR O ENDPOINT http://localhost:8080/actuator/health";

    public ChatService(VertexAiGeminiChatModel chatModel, HealthEndpoint healthEndpoint) {
        this.chatModel = chatModel;
        this.healthEndpoint = healthEndpoint;
    }

    public String generate() {
        HealthComponent health = healthEndpoint.health();
        String dadosDoHealth = health.getStatus().toString();

        String message = DEFAULT_MESSAGE + " Dados do health: " + dadosDoHealth;

        return this.chatModel.call(message);
    }
}
