/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Date;

/**
 *
 * @author Nghia
 */
public class PaymentDTO {
    private int payment_ID;
    private String payment_CODE;
    private Date payment_DATE;
    private String payment_STATUS;
    private int userID;

    public PaymentDTO() {
    }

    public PaymentDTO(int payment_ID, String payment_CODE, Date payment_DATE, String payment_STATUS, int userID) {
        this.payment_ID = payment_ID;
        this.payment_CODE = payment_CODE;
        this.payment_DATE = payment_DATE;
        this.payment_STATUS = payment_STATUS;
        this.userID = userID;
    }
    
    public int getPayment_ID() {
        return payment_ID;
    }

    public void setPayment_ID(int payment_ID) {
        this.payment_ID = payment_ID;
    }

    public String getPayment_CODE() {
        return payment_CODE;
    }

    public void setPayment_CODE(String payment_CODE) {
        this.payment_CODE = payment_CODE;
    }

    public Date getPayment_DATE() {
        return payment_DATE;
    }

    public void setPayment_DATE(Date payment_DATE) {
        this.payment_DATE = payment_DATE;
    }

    public String getPayment_STATUS() {
        return payment_STATUS;
    }

    public void setPayment_STATUS(String payment_STATUS) {
        this.payment_STATUS = payment_STATUS;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }
    
}
