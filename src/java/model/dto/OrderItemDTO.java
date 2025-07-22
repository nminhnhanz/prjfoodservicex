package model.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class OrderItemDTO {

    private int orderID;
    private int menuID;
    private int quantity;
    private Date orderTime;
    private BigDecimal price;

    public OrderItemDTO() {
    }

    public OrderItemDTO(int orderID, int menuID, int quantity, Date orderTime, BigDecimal price) {
        this.orderID = orderID;
        this.menuID = menuID;
        this.quantity = quantity;
        this.orderTime = orderTime;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

}
