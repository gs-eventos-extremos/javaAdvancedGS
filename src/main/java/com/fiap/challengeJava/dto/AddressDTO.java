package com.fiap.challengeJava.dto;

import com.fiap.challengeJava.domain.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id;
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

    public AddressDTO(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.num = address.getNum();
        this.city = address.getCity();
        this.state = address.getState();
        this.zipCode = address.getZipCode();
    }

    public AddressDTO(String street, String num, String city, String state, String zipCode) {
        this.street = street;
        this.num = num;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
    }
}
