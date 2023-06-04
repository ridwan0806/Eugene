package com.example.eugene.Model;

public class OrderItems {
    private String Id,ProductId,ProductName;
    int qty;
    double price,subtotal;

    public OrderItems() {
    }

    public OrderItems(String id, String productId, String productName, int qty, double price, double subtotal) {
        Id = id;
        ProductId = productId;
        ProductName = productName;
        this.qty = qty;
        this.price = price;
        this.subtotal = subtotal;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
