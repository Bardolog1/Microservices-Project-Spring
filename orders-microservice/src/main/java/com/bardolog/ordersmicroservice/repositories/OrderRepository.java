package com.bardolog.ordersmicroservice.repositories;


import com.bardolog.ordersmicroservice.model.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
