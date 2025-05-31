package com.fiap.challengeJava.dto.auth;

import com.fiap.challengeJava.enums.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotBlank(message = "Nome é obrigatório")
    public String name;
    @NotBlank(message = "Email é obrigatório")
    public String email;
    @NotBlank(message = "Senha é obrigatória")
    public String password;
    @NotNull(message = "A função/papel é obrigatória")
    UserRole role;
    @NotBlank(message = "Rua é obrigatória")
    private String street;
    @NotBlank(message = "Número é obrigatório")
    private String num;
    @NotBlank(message = "Cidade é obrigatória")
    private String city;
    @NotBlank(message = "Estado é obrigatório")
    private String state;
    @NotBlank(message = "CEP é obrigatório")
    private String zipCode;
}
