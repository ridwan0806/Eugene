package com.example.eugene.Model;

import java.util.List;

public class Request {
    private String createByUser;
    private String nameCustomer;
    double total;
    private List<Orders> foods;

    public Request() {
    }

    public Request(String createByUser, String nameCustomer, double total, List<Orders> foods) {
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Orders> getFoods() {
        return foods;
    }

    public void setFoods(List<Orders> foods) {
        this.foods = foods;
    }
}
