package com.example.demo.service;

import com.example.demo.hibController.AddressHibController;
import com.example.demo.hibController.UserHibController;
import com.example.demo.model.Address;
import com.example.demo.model.User;
import com.example.demo.request.AddressRequest;
import com.example.demo.response.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private AddressHibController addressHibController = new AddressHibController(entityManagerFactory);
    private UserHibController userHibController = new UserHibController(entityManagerFactory);

    @Autowired
    public AddressService() {
    }

    public List<AddressResponse> findAll(){
        List<AddressResponse> addressResponses = new ArrayList<>();
        for(Address address: addressHibController.getAddressList()){
            addressResponses.add(new AddressResponse(address));
        }
        return addressResponses;
    }

    public AddressResponse findAddressByUserId(int userID){
        return new AddressResponse(addressHibController.getAddressByUserId(userID));
    }

    public AddressResponse findAddressById(int id){
        return new AddressResponse(addressHibController.getAddressById(id));
    }

    public void createAddress(final AddressRequest addressRequest, int userID){
        User user = userHibController.getUserById(userID);
        Address address = new Address(addressRequest.getStreet(), addressRequest.getCity(), addressRequest.getPostalCode(), addressRequest.getCountry(), user);
        addressHibController.createAddress(address);
    }

    public void updateAddress(AddressRequest addressRequest, int id){
        Address address = addressHibController.getAddressById(id);

        address.setCity(addressRequest.getCity());
        address.setCountry(addressRequest.getCountry());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setStreet(addressRequest.getStreet());

        addressHibController.updateAddress(address);
    }

    public void deleteAddress(int id){
        addressHibController.deleteAddress(id);
    }
}
