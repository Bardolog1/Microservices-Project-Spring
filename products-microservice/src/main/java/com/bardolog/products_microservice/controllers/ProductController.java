package com.bardolog.products_microservice.controllers;

import com.bardolog.products_microservice.model.dtos.ProductRequest;
import com.bardolog.products_microservice.model.dtos.ProductResponse;
import com.bardolog.products_microservice.model.entities.ProductEntity;
import com.bardolog.products_microservice.repositories.ProductRepository;
import com.bardolog.products_microservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody ProductRequest productRequest){
        this.productService.addProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts(){

        return this.productService.getAllProducts();
    }



}
