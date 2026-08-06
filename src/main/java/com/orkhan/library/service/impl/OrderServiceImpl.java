package com.orkhan.library.service.impl;

import com.orkhan.library.dto.OrderRequestDto;
import com.orkhan.library.dto.OrderResponseDto;
import com.orkhan.library.repository.BookRepository;
import com.orkhan.library.repository.OrderItemRepository;
import com.orkhan.library.repository.OrderRepository;
import com.orkhan.library.service.OrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.orkhan.library.entity.Book;
import com.orkhan.library.entity.Order;
import com.orkhan.library.entity.OrderItem;
import com.orkhan.library.dto.OrderItemRequestDto;
import com.orkhan.library.dto.OrderItemResponseDto;
import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final BookRepository bookRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository, BookRepository bookRepository) {

        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<OrderResponseDto> getAllOrders() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public OrderResponseDto getOrderById(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
    
    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto request) {
        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setOrderDate(LocalDate.now());
        List<OrderItemResponseDto> responseItems = new ArrayList<>();
        
        for (OrderItemRequestDto itemRequest : request.getItems()) {
            Book book = bookRepository.findById(itemRequest.getBookId()).orElseThrow(() -> new RuntimeException("Book not found: " + itemRequest.getBookId()));
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setBook(book);
            orderItem.setQuantity(itemRequest.getQuantity());
            order.getOrderItems().add(orderItem);
            
            responseItems.add(new OrderItemResponseDto(book.getTitle(), itemRequest.getQuantity()));
    }

    Order savedOrder = orderRepository.save(order);

    return new OrderResponseDto(
            savedOrder.getId(),
            savedOrder.getCustomerName(),
            savedOrder.getOrderDate(),
            responseItems
    );
}

    @Override
    public void deleteOrder(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}