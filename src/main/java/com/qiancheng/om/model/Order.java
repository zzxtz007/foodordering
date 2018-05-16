package com.qiancheng.om.model;

import java.sql.Timestamp;

/**
 * 订单
 * @author Ice_Dog
 */
public class Order {
    private String id;
    private String consumerId;
    private String stallId;
    private String phone;
    private Integer appointmentId;
    private Timestamp appointmentTime;
    private String remark;
    private Boolean isPack;
    private Integer status;
    private Timestamp orderTime;
    private Timestamp payTime;
    private Timestamp refundApplicationTime;
    private Timestamp refundEndTime;
    private String insertUser;
    private Timestamp insertTime;
    private String updateUser;
    private Timestamp updateTime;
    private Boolean isDeleted;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(String consumerId) {
        this.consumerId = consumerId;
    }

    public String getStallId() {
        return stallId;
    }

    public void setStallId(String stallId) {
        this.stallId = stallId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Timestamp getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Timestamp appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Boolean getIsPack() {
        return isPack;
    }

    public void setIsPack(Boolean pack) {
        isPack = pack;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    public Timestamp getPayTime() {
        return payTime;
    }

    public void setPayTime(Timestamp payTime) {
        this.payTime = payTime;
    }

    public Timestamp getRefundApplicationTime() {
        return refundApplicationTime;
    }

    public void setRefundApplicationTime(Timestamp refundApplicationTime) {
        this.refundApplicationTime = refundApplicationTime;
    }

    public Timestamp getRefundEndTime() {
        return refundEndTime;
    }

    public void setRefundEndTime(Timestamp refundEndTime) {
        this.refundEndTime = refundEndTime;
    }

    public String getInsertUser() {
        return insertUser;
    }

    public void setInsertUser(String insertUser) {
        this.insertUser = insertUser;
    }

    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
        this.insertTime = insertTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", consumerId='" + consumerId + '\'' +
                ", stallId='" + stallId + '\'' +
                ", phone='" + phone + '\'' +
                ", appointmentId=" + appointmentId +
                ", appointmentTime=" + appointmentTime +
                ", remark='" + remark + '\'' +
                ", isPack=" + isPack +
                ", satus=" + status +
                ", orderTime=" + orderTime +
                ", payTime=" + payTime +
                ", refundApplicationTime=" + refundApplicationTime +
                ", refundEndTime=" + refundEndTime +
                ", insertUser='" + insertUser + '\'' +
                ", insertTime=" + insertTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
