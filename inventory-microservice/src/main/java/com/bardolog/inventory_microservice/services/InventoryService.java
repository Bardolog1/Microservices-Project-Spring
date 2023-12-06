package com.bardolog.inventory_microservice.services;

import com.bardolog.inventory_microservice.model.dtos.BaseResponse;
import com.bardolog.inventory_microservice.model.dtos.OrderItemRequest;
import com.bardolog.inventory_microservice.model.entities.InventoryEntity;
import com.bardolog.inventory_microservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public boolean isInStock(String sku) {
        var inventory = inventoryRepository.findBySku(sku);
        return inventory.filter( val -> val.getQuantity() > 0).isPresent();
    }

    public BaseResponse areInStock(List<OrderItemRequest> orderItemRequests) {

        var erroList = new ArrayList<String>();
        List<String> skus = orderItemRequests.stream().map(OrderItemRequest::getSku).toList();
        List<InventoryEntity> inventoryList = inventoryRepository.findBySkuIn(skus);
        orderItemRequests.forEach(oI->{
            var inventory = inventoryList.stream().filter(val ->val.getSku().equals(oI.getSku())).findFirst();

            if(inventory.isEmpty()){
                erroList.add("Product with sku "+ oI.getSku() + " does not exist");
            } else if (inventory.get().getQuantity() < oI.getQuantity()) {
                erroList.add("Product with sku "+oI.getSku()+" has insufficient quntity");
            }
        });
        return erroList.size()> 0 ? new BaseResponse(erroList.toArray(new String[0])) : new BaseResponse(null);

       }
}
