package com.bardolog.inventory_microservice.model.dtos;


public record BaseResponse(String[] errorMessages) {
    public boolean hasErrors(){
        return errorMessages != null && errorMessages.length>0;
    }
}
