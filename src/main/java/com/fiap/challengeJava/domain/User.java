package com.fiap.challengeJava.domain;

import com.fiap.challengeJava.dto.UserDTO;
import com.fiap.challengeJava.enums.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_user_gs")
public class User extends People implements UserDetails {

    @Column(unique = true)
    private String email;
    private String password;

    @OneToOne
    private Address address;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public User(String name, String email, String password, UserRole role) {
        this.setName(name);
        this.setEmail(email);
        this.setPassword(password);
        this.role = role;
    }

    public User(UserDTO userDTO) {
        this.setId(userDTO.getId());
        this.setName(userDTO.getName());
        this.setEmail(userDTO.getEmail());
        this.setPassword(userDTO.getPassword());
        this.role = userDTO.getRole();
        this.setCreatedAt(LocalDate.now());
    }

    public User(User user) {
        this.setId(user.getId());
        this.setName(user.getName());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.role = user.getRole();
        this.setCreatedAt(user.getCreatedAt());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
