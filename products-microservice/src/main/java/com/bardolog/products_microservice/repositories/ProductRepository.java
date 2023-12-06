package com.bardolog.products_microservice.repositories;


import com.bardolog.products_microservice.model.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

}
