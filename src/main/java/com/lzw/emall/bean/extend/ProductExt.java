package com.lzw.emall.bean.extend;

import java.io.Serializable;
import java.math.BigDecimal;

public class ProductExt implements Serializable {
    private Integer productId;

    private String productName;

    private BigDecimal productPrice;

    private String productImage;

    private String productInfo;

    private String categoryName;

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
        this.productName = productName;
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
        this.productImage = productImage;
    }

    public String getProductInfo() {
        return productInfo;
    }

    public void setProductInfo(String productInfo) {
        this.productInfo = productInfo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "ProductExt{" +
                "productId=" + productId +
                ", productName='" + productName + '\'' +
                ", productPrice=" + productPrice +
                ", productImage='" + productImage + '\'' +
                ", productInfo='" + productInfo + '\'' +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
