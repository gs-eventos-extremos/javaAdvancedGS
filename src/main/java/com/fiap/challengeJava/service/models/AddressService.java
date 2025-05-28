package com.fiap.challengeJava.service.models;

import com.fiap.challengeJava.domain.Address;
import com.fiap.challengeJava.dto.AddressDTO;
import com.fiap.challengeJava.repository.AddressRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Transactional
    public AddressDTO insert(AddressDTO addressDTO) {
        Address address = new Address();
        BeanUtils.copyProperties(addressDTO, address);
        address = addressRepository.save(address);
        return new AddressDTO(address);
    }

    public List<AddressDTO> findAll() {
        return addressRepository.findAll().stream().map(AddressDTO::new).toList();
    }

    public AddressDTO findById(Long id) {
        return new AddressDTO(addressRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Endereço não encontrado")));
    }

    @Transactional
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }

    @Transactional
    public void update(Long id, String newStreet) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Endereço não encontrado"));
        address.setStreet(newStreet);
        addressRepository.save(address);
    }
}