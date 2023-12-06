package com.bardolog.products_microservice.model.dtos;

import lombok.*;
import org.springframework.stereotype.Service;

@Data
/*@Getter
@Setter*/
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductRequest {

    private String sku;
    private String name;
    private String description;
    private Double price;
    private Boolean status;
}
