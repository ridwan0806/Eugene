package com.example.eugene.Model;

import java.util.List;

public class Request {
    private String createByUser;
    private String nameCustomer;
    private String total;
    private List<Order> foods;

    public Request() {
    }

    public Request(String createByUser, String nameCustomer, String total, List<Order> foods) {
        this.createByUser = createByUser;
        this.nameCustomer = nameCustomer;
        this.total = total;
        this.foods = foods;
    }

    public String getCreateByUser() {
        return createByUser;
    }

    public void setCreateByUser(String createByUser) {
        this.createByUser = createByUser;
    }

    public String getNameCustomer() {
        return nameCustomer;
    }

    public void setNameCustomer(String nameCustomer) {
        this.nameCustomer = nameCustomer;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
