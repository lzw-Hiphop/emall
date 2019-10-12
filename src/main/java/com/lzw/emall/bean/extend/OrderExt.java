package com.lzw.emall.bean.extend;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderExt implements Serializable {
    private Integer itemId;

    private String orderTime;

    private String  pimage;

    private String pname;

    private BigDecimal price;

    private Integer count;

    private BigDecimal subtotal;

    private String orderId;

    private Integer payState;

    public Integer getPayState() {
        return payState;
    }

    public void setPayState(Integer payState) {
        this.payState = payState;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPimage() {
        return pimage;
    }

    public void setPimage(String pimage) {
        this.pimage = pimage;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderExt{" +
                "itemId=" + itemId +
                ", orderTime='" + orderTime + '\'' +
                ", pimage='" + pimage + '\'' +
                ", pname='" + pname + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", subtotal=" + subtotal +
                ", orderId='" + orderId + '\'' +
                ", payState=" + payState +
                '}';
    }
}
