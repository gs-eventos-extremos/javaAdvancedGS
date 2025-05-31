package com.fiap.challengeJava.service.models;

import com.fiap.challengeJava.domain.Address;
import com.fiap.challengeJava.domain.User;
import com.fiap.challengeJava.dto.AddressDTO;
import com.fiap.challengeJava.dto.UserDTO;
import com.fiap.challengeJava.enums.UserRole;
import com.fiap.challengeJava.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AddressService addressService;

    @Transactional
    public UserDTO insert(UserDTO userDTO, AddressDTO addressDTO) {
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        Address address = new Address(addressService.insert(addressDTO));
        user.setAddress(address);
        user.setCreatedAt(LocalDate.now());
        user = userRepository.save(user);
        return new UserDTO(user);
    }

    @Transactional
    public UserDTO updatePassword(UserDTO userDTO) {
        User user = this.userRepository.findByEmail(userDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Conta não encontrada"));

        user.setPassword(userDTO.getPassword());
        return new UserDTO(this.userRepository.save(user));
    }

    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Conta não encontrada"));
    }

    public Optional<User> loadUserByUsername(String email) {
        return this.userRepository.findByEmail(email);
    }

    public List<UserDTO> findAll() {
        return this.userRepository.findAll().stream().map(UserDTO::new).toList();
    }

    public User findById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

    public List<User> findByRole(UserRole role) {
        return this.userRepository.findByRole(role);
    }

    @Transactional
    public void delete(Long id) {
        this.userRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, String newName) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        user.setName(newName);
        this.userRepository.save(user);
    }

}
