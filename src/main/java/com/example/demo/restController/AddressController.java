package com.example.demo.restController;

import com.example.demo.request.AddressRequest;
import com.example.demo.response.AddressResponse;
import com.example.demo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping
    public List<AddressResponse> getAllAddresses(){
        return addressService.findAll();
    }

    @GetMapping("/{id}")
    public AddressResponse getProductById(@PathVariable String id){
        return addressService.findAddressById(Integer.parseInt(id));
    }

    @PostMapping("/{userID}")
    ResponseEntity<HttpStatus> createAddress(@RequestBody AddressRequest addressRequest, @PathVariable String userID){
        addressService.createAddress(addressRequest, Integer.parseInt(userID));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateAddress(@RequestBody AddressRequest addressRequest, @PathVariable String id){
        addressService.updateAddress(addressRequest, Integer.parseInt(id));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable String id){
        addressService.deleteAddress(Integer.parseInt(id));
    }
}
