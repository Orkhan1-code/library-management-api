package com.orkhan.library.dto;

public class OrderItemResponseDto {

    private String bookTitle;
    private Integer quantity;

    public OrderItemResponseDto() {
    }

    public OrderItemResponseDto(String bookTitle, Integer quantity) {
        this.bookTitle = bookTitle;
        this.quantity = quantity;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}