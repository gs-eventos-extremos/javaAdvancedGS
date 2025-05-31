package com.fiap.challengeJava.service;

import com.fiap.challengeJava.domain.User;
import com.fiap.challengeJava.dto.UserDTO;
import com.fiap.challengeJava.dto.auth.RegisterRequestDTO;
import com.fiap.challengeJava.dto.auth.RegisterResponseDTO;
import com.fiap.challengeJava.enums.UserRole;
import com.fiap.challengeJava.service.exceptions.UserAlreadyExistsException;
import com.fiap.challengeJava.service.models.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthService authService;

    @Test
    void shouldCreateUserSuccessfully() {
        RegisterRequestDTO request = new RegisterRequestDTO(
                "João Silva",
                "joao@email.com",
                "senha123",
                UserRole.ADMIN,
                "Rua A",
                "123",
                "São Paulo",
                "SP",
                "01234-567"
        );


        when(userService.loadUserByUsername("joao@email.com"))
                .thenReturn(Optional.empty());


        when(passwordEncoder.encode("senha123"))
                .thenReturn("senha_criptografada");


        UserDTO userRetornado = new UserDTO("João Silva", "joao@email.com", "senha_criptografada", UserRole.ADMIN);
        when(userService.insert(any(), any()))
                .thenReturn(userRetornado);


        RegisterResponseDTO result = authService.signup(request);


        assertEquals("joao@email.com", result.getEmail());
        assertEquals("João Silva", result.getName());


        verify(userService).loadUserByUsername("joao@email.com");
        verify(passwordEncoder).encode("senha123");
        verify(userService).insert(any(), any());
    }

    @Test
    void shouldThrowExceptionWhenUserAlreadyExists() {

        RegisterRequestDTO request = new RegisterRequestDTO(
                "Maria Silva",
                "maria@email.com",
                "senha456",
                UserRole.ADMIN,
                "Rua B",
                "456",
                "Rio de Janeiro",
                "RJ",
                "20000-000"
        );


        UserDTO usuarioExistente = new UserDTO("Maria", "maria@email.com", "senha", UserRole.ADMIN);
        User user = new User(usuarioExistente);
        when(userService.loadUserByUsername("maria@email.com"))
                .thenReturn(Optional.of(user));


        assertThrows(UserAlreadyExistsException.class, () -> {
            authService.signup(request);
        });

        verify(userService, never()).insert(any(), any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    void shouldEncryptPassword() {

        RegisterRequestDTO request = new RegisterRequestDTO(
                "Pedro Santos",
                "pedro@email.com",
                "minhasenha",
                UserRole.ADMIN,
                "Av. Central",
                "789",
                "Brasília",
                "DF",
                "70000-000"
        );


        when(userService.loadUserByUsername("pedro@email.com"))
                .thenReturn(Optional.empty());


        when(passwordEncoder.encode("minhasenha"))
                .thenReturn("$2a$10$senha_super_criptografada");


        UserDTO userRetornado = new UserDTO("Pedro Santos", "pedro@email.com", "$2a$10$senha_super_criptografada", UserRole.ADMIN);
        when(userService.insert(any(), any()))
                .thenReturn(userRetornado);


        authService.signup(request);


        verify(passwordEncoder).encode("minhasenha");


        verify(userService).insert(any(), any());
    }
}
