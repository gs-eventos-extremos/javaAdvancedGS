package com.fiap.challengeJava.controller;

import com.fiap.challengeJava.dto.AddressDTO;
import com.fiap.challengeJava.dto.auth.RegisterRequestDTO;
import com.fiap.challengeJava.dto.auth.RegisterResponseDTO;
import com.fiap.challengeJava.service.models.AddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(path = "/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAll() {
        List<AddressDTO> address = addressService.findAll();
        return ResponseEntity.ok(address);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ModelAndView insert(@ModelAttribute @Valid AddressDTO addressDTO, Model model) {
        AddressDTO address = addressService.insert(addressDTO);
        model.addAttribute("message", "O Endereço foi cadastrado com sucesso.");
        return new ModelAndView("success-cadastro");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long id, @RequestParam String newStreet) {
        addressService.update(id, newStreet);
        return ResponseEntity.ok("Endereço atualizado com sucesso.");
    }
}
