package com.orkhan.library.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class OrderRequestDto {

    @NotBlank
    private String customerName;

    @Valid
    private List<OrderItemRequestDto> items;

    public OrderRequestDto() {
    }

    public OrderRequestDto(String customerName, List<OrderItemRequestDto> items) {
        this.customerName = customerName;
        this.items = items;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<OrderItemRequestDto> getItems() {
        return items;
    }

    public void setItems(List<OrderItemRequestDto> items) {
        this.items = items;
    }
}