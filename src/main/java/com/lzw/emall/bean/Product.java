package com.lzw.emall.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Product implements Serializable {
    private Integer productId;

    private String productName;

    private BigDecimal productPrice;

    private String productImage;

    private String productInfo;

    private Integer categoryId;

    private Integer isHot;

    private Date putonTime;

    private Integer productStore;

    private static final long serialVersionUID = 1L;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo == null ? null : productInfo.trim();
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getIsHot() {
        return isHot;
    }

    public void setIsHot(Integer isHot) {
        this.isHot = isHot;
    }

    public Date getPutonTime() {
        return putonTime;
    }

    public void setPutonTime(Date putonTime) {
        this.putonTime = putonTime;
    }

    public Integer getProductStore() {
        return productStore;
    }

    public void setProductStore(Integer productStore) {
        this.productStore = productStore;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", productId=").append(productId);
        sb.append(", productName=").append(productName);
        sb.append(", productPrice=").append(productPrice);
        sb.append(", productImage=").append(productImage);
        sb.append(", productInfo=").append(productInfo);
        sb.append(", categoryId=").append(categoryId);
        sb.append(", isHot=").append(isHot);
        sb.append(", putonTime=").append(putonTime);
        sb.append(", productStore=").append(productStore);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}