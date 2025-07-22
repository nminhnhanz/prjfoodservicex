/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dto;

import java.math.BigDecimal;
import java.sql.Date;

/**
 *
 * @author ASUS
 */
public class CartDTO {

    private int User_ID;
    private int Menu_ID;
    private int Quantity;

    public CartDTO() {
    }

    public CartDTO(int User_ID, int Menu_ID, int Quantity) {
        this.User_ID = User_ID;
        this.Menu_ID = Menu_ID;
        this.Quantity = Quantity;
    }

    public int getUser_ID() {
        return User_ID;
    }

    public void setUser_ID(int User_ID) {
        this.User_ID = User_ID;
    }

    public int getMenu_ID() {
        return Menu_ID;
    }

    public void setMenu_ID(int Menu_ID) {
        this.Menu_ID = Menu_ID;
    }

    public int getQuantity() {
        return Quantity;
    }
    
    public void setQuantity(int Quantity) {
        this.Quantity = Quantity;
    }

}
