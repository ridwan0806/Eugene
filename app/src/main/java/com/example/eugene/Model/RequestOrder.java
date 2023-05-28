package com.example.eugene.Model;

import java.util.List;

public class RequestOrder {
    int branchId,orderType,customerType,subtotalItem,isEnable,isCancel;
    private String createByUser,nameCustomer,date,status,notes,cancelReason,editedByUser,editDateTime;
    double subtotalPrice,nominalPayment,change;
    private List<Orders> orderDetail;
    private long createDateTime;

    public RequestOrder(int branchId, int orderTypeId, int customerTypeId, int i, int i1, int i2, String fullName, String s, String s1, String aNew, String toString, String s2, String s3, String s4, double v, int i3, int i4, List<Orders> orderItem) {
    }

    public RequestOrder(int branchId, int orderType, int customerType, int subtotalItem, int isEnable, int isCancel, String createByUser, String nameCustomer, String date, String status, String notes, String cancelReason, String editedByUser, String editDateTime, double subtotalPrice, double nominalPayment, double change, List<Orders> orderDetail, long createDateTime) {
        this.branchId = branchId;
        this.orderType = orderType;
        this.customerType = customerType;
        this.subtotalItem = subtotalItem;
        this.isEnable = isEnable;
        this.isCancel = isCancel;
        this.createByUser = createByUser;
        this.nameCustomer = nameCustomer;
        this.date = date;
        this.status = status;
        this.notes = notes;
        this.cancelReason = cancelReason;
        this.editedByUser = editedByUser;
        this.editDateTime = editDateTime;
        this.subtotalPrice = subtotalPrice;
        this.nominalPayment = nominalPayment;
        this.change = change;
        this.orderDetail = orderDetail;
        this.createDateTime = createDateTime;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
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

    public int getSubtotalItem() {
        return subtotalItem;
    }

    public void setSubtotalItem(int subtotalItem) {
        this.subtotalItem = subtotalItem;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public List<Orders> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<Orders> orderDetail) {
        this.orderDetail = orderDetail;
    }

    public long getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(long createDateTime) {
        this.createDateTime = createDateTime;
    }
}
