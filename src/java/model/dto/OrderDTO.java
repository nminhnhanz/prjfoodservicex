/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dto;

import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class OrderDTO {
    private int order_ID;
    private Date order_time;
    private int user_ID;
    private int payment_ID;   
    private int menu_ID;
    private int order_quantity;
    private Date order_price_time;  

    public OrderDTO() {
    }

    public OrderDTO(int order_ID, Date order_time, int user_ID, int payment_ID, int menu_ID, int order_quantity, Date order_price_time) {
        this.order_ID = order_ID;
        this.order_time = order_time;
        this.user_ID = user_ID;
        this.payment_ID = payment_ID;
        this.menu_ID = menu_ID;
        this.order_quantity = order_quantity;
        this.order_price_time = order_price_time;
    }

    public int getOrder_ID() {
        return order_ID;
    }

    public void setOrder_ID(int order_ID) {
        this.order_ID = order_ID;
    }

    public Date getOrder_time() {
        return order_time;
    }

    public void setOrder_time(Date order_time) {
        this.order_time = order_time;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public int getPayment_ID() {
        return payment_ID;
    }

    public void setPayment_ID(int payment_ID) {
        this.payment_ID = payment_ID;
    }

    public int getMenu_ID() {
        return menu_ID;
    }

    public void setMenu_ID(int menu_ID) {
        this.menu_ID = menu_ID;
    }

    public int getOrder_quantity() {
        return order_quantity;
    }

    public void setOrder_quantity(int order_quantity) {
        this.order_quantity = order_quantity;
    }

    public Date getOrder_price_time() {
        return order_price_time;
    }

    public void setOrder_price_time(Date order_price_time) {
        this.order_price_time = order_price_time;
    }   
}

