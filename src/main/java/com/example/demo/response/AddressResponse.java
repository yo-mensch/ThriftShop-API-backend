package com.example.demo.response;

import com.example.demo.model.Address;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private int id;
    private String street;
    private String city;
    private int postalCode;
    private String country;

    public AddressResponse(Address address) {
        this.id = address.getId();
        this.street = address.getStreet();
        this.city = address.getCity();
        this.postalCode = address.getPostalCode();
        this.country = address.getCountry();
    }
}
