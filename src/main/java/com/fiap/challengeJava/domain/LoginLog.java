package com.fiap.challengeJava.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "login_logs")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginLog {

    @Id
    private String id;
    private LocalDateTime timestamp;
    private String usuario;
    private String status;

    public LoginLog(String usuario, String status) {
        this.timestamp = LocalDateTime.now();
        this.usuario = usuario;
        this.status = status;
    }
}