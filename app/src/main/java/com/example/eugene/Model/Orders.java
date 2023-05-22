package com.example.eugene.Model;

public class Orders {

    private String Id;
    private String ProductId;
    private String ProductName;
    int Quantity;
    double Price;
    double Subtotal;

    public Orders() {
    }

    public Orders(String id, String productId, String productName, int quantity, double price, double subtotal) {
        Id = id;
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        Subtotal = subtotal;
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

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(double subtotal) {
        Subtotal = subtotal;
    }
}
