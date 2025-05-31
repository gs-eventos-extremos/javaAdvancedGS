package com.fiap.challengeJava.repository;


import com.fiap.challengeJava.domain.User;
import com.fiap.challengeJava.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true, value = """
            SELECT * FROM tb_user_gs WHERE email = :email
            """)
    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole role);
}
