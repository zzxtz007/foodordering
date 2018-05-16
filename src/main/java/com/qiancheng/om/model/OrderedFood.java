package com.qiancheng.om.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 订单菜品
 *
 * @author Ice_Dog
 */
public class OrderedFood {
    private Integer id;
    private Integer foodId;
    private String orderId;
    private Integer count;
    private Boolean isMember;
    private BigDecimal packFee;
    private BigDecimal standardPrice;
    private BigDecimal memberPriceCost;
    private BigDecimal memberPrice;
    private Integer mark;
    private String insertUser;
    private Timestamp insertTime;
    private String updateUser;
    private Timestamp updateTime;
    private Boolean isDeleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFoodId() {
        return foodId;
    }

    public void setFoodId(Integer foodId) {
        this.foodId = foodId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getIsMember() {
        return isMember;
    }

    public void setIsMember(Boolean member) {
        isMember = member;
    }

    public BigDecimal getPackFee() {
        return packFee;
    }

    public void setPackFee(BigDecimal packFee) {
        this.packFee = packFee;
    }

    public BigDecimal getStandardPrice() {
        return standardPrice;
    }

    public void setStandardPrice(BigDecimal standardPrice) {
        this.standardPrice = standardPrice;
    }

    public BigDecimal getMemberPriceCost() {
        return memberPriceCost;
    }

    public void setMemberPriceCost(BigDecimal memberPriceCost) {
        this.memberPriceCost = memberPriceCost;
    }

    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
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

    public void setIsIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "OrderedFood{" +
                "id=" + id +
                ", foodId=" + foodId +
                ", orderId=" + orderId +
                ", count=" + count +
                ", isMember=" + isMember +
                ", packFee=" + packFee +
                ", standardPrice=" + standardPrice +
                ", memberPriceCost=" + memberPriceCost +
                ", memberPrice=" + memberPrice +
                ", mark=" + mark +
                ", insertUser='" + insertUser + '\'' +
                ", insertTime=" + insertTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
