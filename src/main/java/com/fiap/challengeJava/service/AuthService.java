package com.fiap.challengeJava.service;

import com.fiap.challengeJava.domain.LoginLog;
import com.fiap.challengeJava.domain.User;
import com.fiap.challengeJava.dto.AddressDTO;
import com.fiap.challengeJava.dto.EmailDTO;
import com.fiap.challengeJava.dto.UserDTO;
import com.fiap.challengeJava.dto.auth.LoginRequestDTO;
import com.fiap.challengeJava.dto.auth.LoginResponseDTO;
import com.fiap.challengeJava.dto.auth.RegisterRequestDTO;
import com.fiap.challengeJava.dto.auth.RegisterResponseDTO;
import com.fiap.challengeJava.infra.security.TokenService;
import com.fiap.challengeJava.repository.LoginLogRepository;
import com.fiap.challengeJava.service.exceptions.InvalidCredentialsException;
import com.fiap.challengeJava.service.exceptions.UserAlreadyExistsException;
import com.fiap.challengeJava.service.models.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private MessageSenderService emailService;
    @Autowired
    private LoginLogRepository loginLogRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.userService.findByEmail(username);
    }

    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO auth) {
        try {
            User user = (User) this.loadUserByUsername(auth.getEmail());
            if (passwordEncoder.matches(auth.getPassword(), user.getPassword())) {
                String token = this.tokenService.generateToken(user);

                // Salvar log de login bem-sucedido
                LoginLog log = new LoginLog(user.getEmail(), "SUCCESS");
                loginLogRepository.save(log);

                return new LoginResponseDTO(user.getEmail(), token);
            } else {
                // Salvar log de senha incorreta
                LoginLog log = new LoginLog(auth.getEmail(), "FAILED");
                loginLogRepository.save(log);

                throw new InvalidCredentialsException("Senha incorreta!!");
            }
        } catch (UsernameNotFoundException e) {
            // Salvar log de usuário não encontrado
            LoginLog log = new LoginLog(auth.getEmail(), "FAILED");
            loginLogRepository.save(log);

            throw e; // Re-lança a exceção original
        } catch (InvalidCredentialsException e) {
            // A exceção de senha incorreta já foi tratada acima
            throw e;
        } catch (Exception e) {
            // Salvar log de erro inesperado
            LoginLog log = new LoginLog(auth.getEmail(), "ERROR");
            loginLogRepository.save(log);

            throw e;
        }
    }

    @Transactional
    public RegisterResponseDTO signup(RegisterRequestDTO auth) {
        this.userService.loadUserByUsername(auth.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("Conta já existente com este email.");
                });

        String encryptedPassword = passwordEncoder.encode(auth.getPassword());

        UserDTO user = new UserDTO(auth.getName(), auth.getEmail(), encryptedPassword, auth.getRole());

        AddressDTO addressDTO = new AddressDTO(auth.getStreet(), auth.getNum(), auth.getCity(), auth.getState(), auth.getZipCode());

        user = this.userService.insert(user, addressDTO);
        return new RegisterResponseDTO(user.getEmail(), user.getName());
    }

    public void publishMessageToForgotPassword(String email) {
        User user = userService.findByEmail(email);
        emailService.processAndSendMessage(new EmailDTO(user.getEmail()));
    }
}
