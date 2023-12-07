package com.bardolog.products_microservice.controllers;

import com.bardolog.products_microservice.model.dtos.ProductRequest;
import com.bardolog.products_microservice.model.dtos.ProductResponse;
import com.bardolog.products_microservice.model.entities.ProductEntity;
import com.bardolog.products_microservice.repositories.ProductRepository;
import com.bardolog.products_microservice.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addProduct(@RequestBody ProductRequest productRequest){
        this.productService.addProduct(productRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_USER')")
    public List<ProductResponse> getAllProducts(){

        return this.productService.getAllProducts();
    }



}
