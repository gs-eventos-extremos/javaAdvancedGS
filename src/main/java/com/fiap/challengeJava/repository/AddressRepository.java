package com.fiap.challengeJava.repository;

import com.fiap.challengeJava.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
