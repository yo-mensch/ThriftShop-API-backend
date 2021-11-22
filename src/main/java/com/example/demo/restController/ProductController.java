package com.example.demo.restController;

import com.example.demo.request.ProductRequest;
import com.example.demo.response.ProductResponse;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts(){
        return productService.findAll();
    }

    @GetMapping("/author/{authorID}")
    public List<ProductResponse> getProductsByAuthorId(@PathVariable String authorID){
        return productService.findProductsByAuthorId(Integer.parseInt(authorID));
    }

    @GetMapping("/{id}")
    public ProductResponse findProductById(@PathVariable String id){
        return productService.findProductById(Integer.parseInt(id));
    }

    @PostMapping("/{authorID}")
    ResponseEntity<HttpStatus> createProduct(@RequestBody ProductRequest productRequest, @PathVariable String authorID){
        productService.createProduct(productRequest, Integer.parseInt(authorID));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @PutMapping("/{id}")
    ResponseEntity<HttpStatus> updateProduct(@RequestBody ProductRequest productRequest, @PathVariable String id){
        productService.updateProduct(productRequest, Integer.parseInt(id));
        return new ResponseEntity<>(HttpStatus.valueOf(204));
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable String id){
        productService.deleteProduct(Integer.parseInt(id));
    }
}

