package com.example.eugene.Model;

import java.util.List;

public class RequestOrder {
    int branchId,orderType,customerType,subtotalItem,isEnable,isCancel;
    private String createByUser,nameCustomer,createDateTime,date,time,billDateTime,status,notes,cancelReason,editedByUser,editDateTime;
    double subtotalPrice,nominalPayment,change;
    private List<Orders> orderDetail;
    private long createDateTimeServer;

    public RequestOrder() {
    }

    public RequestOrder(int branchId, int orderType, int customerType, int subtotalItem, int isEnable, int isCancel, String createByUser, String nameCustomer, String createDateTime, String date, String time, String billDateTime, String status, String notes, String cancelReason, String editedByUser, String editDateTime, double subtotalPrice, double nominalPayment, double change, List<Orders> orderDetail, long createDateTimeServer) {
        this.branchId = branchId;
        this.orderType = orderType;
        this.customerType = customerType;
        this.subtotalItem = subtotalItem;
        this.isEnable = isEnable;
        this.isCancel = isCancel;
        this.createByUser = createByUser;
        this.nameCustomer = nameCustomer;
        this.createDateTime = createDateTime;
        this.date = date;
        this.time = time;
        this.billDateTime = billDateTime;
        this.status = status;
        this.notes = notes;
        this.cancelReason = cancelReason;
        this.editedByUser = editedByUser;
        this.editDateTime = editDateTime;
        this.subtotalPrice = subtotalPrice;
        this.nominalPayment = nominalPayment;
        this.change = change;
        this.orderDetail = orderDetail;
        this.createDateTimeServer = createDateTimeServer;
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

    public String getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(String createDateTime) {
        this.createDateTime = createDateTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBillDateTime() {
        return billDateTime;
    }

    public void setBillDateTime(String billDateTime) {
        this.billDateTime = billDateTime;
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

    public long getCreateDateTimeServer() {
        return createDateTimeServer;
    }

    public void setCreateDateTimeServer(long createDateTimeServer) {
        this.createDateTimeServer = createDateTimeServer;
    }
}
