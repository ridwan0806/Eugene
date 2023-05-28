package com.example.eugene.Model;

import java.util.List;

public class RequestOrder {
    int branchId;
    private String createByUser;
    private String nameCustomer;
    private String timeStamp;
    private String date;
    int orderType;
    int customerType;
    private String status;
    private String notes;
    int subtotalItem;
    double subtotalPrice;
    double nominalPayment;
    double change;
    int isEnable;
    int isCancel;
    private String cancelReason;
    private String editedByUser;
    private String editDateTime;
    private List<Orders> orderDetail;

    public RequestOrder() {
    }

    public RequestOrder(int branchId, String createByUser, String nameCustomer, String timeStamp, String date, int orderType, int customerType, String status, String notes, int subtotalItem, double subtotalPrice, double nominalPayment, double change, int isEnable, int isCancel, String cancelReason, String editedByUser, String editDateTime, List<Orders> orderDetail) {
        this.branchId = branchId;
        this.createByUser = createByUser;
        this.nameCustomer = nameCustomer;
        this.timeStamp = timeStamp;
        this.date = date;
        this.orderType = orderType;
        this.customerType = customerType;
        this.status = status;
        this.notes = notes;
        this.subtotalItem = subtotalItem;
        this.subtotalPrice = subtotalPrice;
        this.nominalPayment = nominalPayment;
        this.change = change;
        this.isEnable = isEnable;
        this.isCancel = isCancel;
        this.cancelReason = cancelReason;
        this.editedByUser = editedByUser;
        this.editDateTime = editDateTime;
        this.orderDetail = orderDetail;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getSubtotalItem() {
        return subtotalItem;
    }

    public void setSubtotalItem(int subtotalItem) {
        this.subtotalItem = subtotalItem;
    }

    public double getSubtotalPrice() {
        return subtotalPrice;
    }

    public void setSubtotalPrice(double subtotalPrice) {
        this.subtotalPrice = subtotalPrice;
    }

    public double getNominalPayment() {
        return nominalPayment;
    }

    public void setNominalPayment(double nominalPayment) {
        this.nominalPayment = nominalPayment;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public int getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(int isEnable) {
        this.isEnable = isEnable;
    }

    public int getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(int isCancel) {
        this.isCancel = isCancel;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getEditedByUser() {
        return editedByUser;
    }

    public void setEditedByUser(String editedByUser) {
        this.editedByUser = editedByUser;
    }

    public String getEditDateTime() {
        return editDateTime;
    }

    public void setEditDateTime(String editDateTime) {
        this.editDateTime = editDateTime;
    }

    public List<Orders> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<Orders> orderDetail) {
        this.orderDetail = orderDetail;
    }
}
