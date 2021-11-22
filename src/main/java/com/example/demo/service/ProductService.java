package com.example.demo.service;

import com.example.demo.hibController.ProductHibController;
import com.example.demo.hibController.UserHibController;
import com.example.demo.model.Product;
import com.example.demo.model.User;
import com.example.demo.request.ProductRequest;
import com.example.demo.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    private EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("ThriftShop");
    private ProductHibController productHibController = new ProductHibController(entityManagerFactory);
    private UserHibController userHibController = new UserHibController(entityManagerFactory);

    @Autowired
    public ProductService() {
    }

    public List<ProductResponse> findAll(){
        List<Product> productList = productHibController.getProductList();
        List<ProductResponse> productJsonList = new ArrayList<>();

        for(Product product: productList){
            productJsonList.add(new ProductResponse(product));
        }
        return productJsonList;
    }

    public ProductResponse findProductById(int id){
        return new ProductResponse(productHibController.getProductById(id));
    }

    public List<ProductResponse> findProductsByAuthorId(int authorId){
        List<Product> foundProducts = productHibController.getProductsByAuthorId(authorId);
        List<ProductResponse> productJsonList = new ArrayList<>();

        for(Product product: foundProducts){
            productJsonList.add(new ProductResponse(product));
        }
        return productJsonList;
    }

    public void createProduct(final ProductRequest productRequest, int authorID){
        User author = userHibController.getUserById(authorID);
        Product product = new Product(productRequest.getName(), productRequest.getDescription(), productRequest.getPrice(), author);
    }

    public void updateProduct(ProductRequest productUpdated, int id) {

        Product product = productHibController.getProductById(id);

        product.setName(productUpdated.getName());
        product.setDescription(productUpdated.getDescription());
        product.setPrice(productUpdated.getPrice());

        productHibController.updateProduct(product);
    }

    public void deleteProduct(int id){
        productHibController.deleteProduct(id);
    }
}
