package com.example.eugene.Model;

public class Order {
    private String Id;
    private String ProductId;
    private String ProductName;
    private String Quantity;
    private String Price;
    private String Subtotal;

    public Order() {
    }

    public Order(String id, String productId, String productName, String quantity, String price, String subtotal) {
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

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(String subtotal) {
        Subtotal = subtotal;
    }
}
