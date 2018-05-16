package com.qiancheng.om.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author Ice_Dog
 */
public class Food {
    private Integer id;
    private String name;
    private String imageUrl;
    private Integer categoryId;
    private String introduction;
    private Boolean isOnSale;
    private BigDecimal packFee;
    private BigDecimal standardPrice;
    private BigDecimal memberPrice;
    private BigDecimal memberPriceCost;
    private Integer memberPriceLimit;
    private Integer memberPriceRemaining;
    private Integer saleCount;
    private Integer weight;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public Boolean getIsOnSale() {
        return isOnSale;
    }

    public void setIsOnSale(Boolean onSale) {
        isOnSale = onSale;
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

    public BigDecimal getMemberPrice() {
        return memberPrice;
    }

    public void setMemberPrice(BigDecimal memberPrice) {
        this.memberPrice = memberPrice;
    }

    public BigDecimal getMemberPriceCost() {
        return memberPriceCost;
    }

    public void setMemberPriceCost(BigDecimal memberPriceCost) {
        this.memberPriceCost = memberPriceCost;
    }

    public Integer getMemberPriceLimit() {
        return memberPriceLimit;
    }

    public void setMemberPriceLimit(Integer memberPriceLimit) {
        this.memberPriceLimit = memberPriceLimit;
    }

    public Integer getMemberPriceRemaining() {
        return memberPriceRemaining;
    }

    public void setMemberPriceRemaining(Integer memberPriceRemaining) {
        this.memberPriceRemaining = memberPriceRemaining;
    }

    public Integer getSaleCount() {
        return saleCount;
    }

    public void setSaleCount(Integer saleCount) {
        this.saleCount = saleCount;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
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
        return "Food{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", categoryId=" + categoryId +
                ", introduction='" + introduction + '\'' +
                ", isOnSale=" + isOnSale +
                ", packFee=" + packFee +
                ", standardPrice=" + standardPrice +
                ", memberPrice=" + memberPrice +
                ", memberPriceCost=" + memberPriceCost +
                ", memberPriceLimit=" + memberPriceLimit +
                ", memberPriceRemaining=" + memberPriceRemaining +
                ", saleCount=" + saleCount +
                ", weight=" + weight +
                ", insertUser='" + insertUser + '\'' +
                ", insertTime=" + insertTime +
                ", updateUser='" + updateUser + '\'' +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
