package com.orkhan.library.service;

import com.orkhan.library.dto.OrderRequestDto;
import com.orkhan.library.dto.OrderResponseDto;

import java.util.List;

public interface OrderService {
    List<OrderResponseDto> getAllOrders();

    OrderResponseDto getOrderById(Long id);

    OrderResponseDto createOrder(OrderRequestDto request);

    void deleteOrder(Long id);
}