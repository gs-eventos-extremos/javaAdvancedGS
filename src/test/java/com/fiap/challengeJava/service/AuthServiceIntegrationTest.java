package com.fiap.challengeJava.service;

import com.fiap.challengeJava.dto.auth.RegisterRequestDTO;
import com.fiap.challengeJava.dto.auth.RegisterResponseDTO;
import com.fiap.challengeJava.enums.UserRole;
import com.fiap.challengeJava.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AuthServiceIntegrationTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldCreateUserInDatabase() {
        RegisterRequestDTO request = new RegisterRequestDTO(
                "Ana Silva",
                "ana@teste.com",
                "senha123",
                UserRole.ADMIN,
                "Rua Teste",
                "100",
                "São Paulo",
                "SP",
                "01000-000"
        );

        RegisterResponseDTO response = authService.signup(request);

        assertEquals("ana@teste.com", response.getEmail());
        assertEquals("Ana Silva", response.getName());

        assertTrue(userRepository.findByEmail("ana@teste.com").isPresent());
    }
}