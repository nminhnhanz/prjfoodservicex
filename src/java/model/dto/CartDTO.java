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
public class CartDTO {
    private int cart_ID;
    private String cart_status;
    private int user_ID;
    private int menu_ID;
    private int food_quantity;
    private Date cart_price_time;

    public CartDTO() {
    }

    public CartDTO(int cart_ID, String cart_status, int promote_ID, int user_ID, int menu_ID, int food_quantity, Date cart_price_time) {
        this.cart_ID = cart_ID;
        this.cart_status = cart_status;;
        this.user_ID = user_ID;
        this.menu_ID = menu_ID;
        this.food_quantity = food_quantity;
        this.cart_price_time = cart_price_time;
    }

    public int getCart_ID() {
        return cart_ID;
    }

    public void setCart_ID(int cart_ID) {
        this.cart_ID = cart_ID;
    }

    public String getCart_status() {
        return cart_status;
    }

    public void setCart_status(String cart_status) {
        this.cart_status = cart_status;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public int getMenu_ID() {
        return menu_ID;
    }

    public void setMenu_ID(int menu_ID) {
        this.menu_ID = menu_ID;
    }

    public int getFood_quantity() {
        return food_quantity;
    }

    public void setFood_quantity(int food_quantity) {
        this.food_quantity = food_quantity;
    }

    public Date getCart_price_time() {
        return cart_price_time;
    }

    public void setCart_price_time(Date cart_price_time) {
        this.cart_price_time = cart_price_time;
    }
    
}

