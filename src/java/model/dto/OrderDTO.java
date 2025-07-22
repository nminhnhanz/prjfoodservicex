package model.dto;

import java.math.BigDecimal;
import java.sql.Date;

public class OrderDTO {

    private int order_ID;
    private Date order_time;
    private int user_ID;
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private String payment_method; // new field
    private String status;         // new field
    private String note;
    private BigDecimal totalPrice;

    public OrderDTO() {
    }

    public OrderDTO(int order_ID, Date order_time, int user_ID, String fullName, String address, String phone, String email, String payment_method, String status, String note) {
        this.order_ID = order_ID;
        this.order_time = order_time;
        this.user_ID = user_ID;
        this.fullName = fullName;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.payment_method = payment_method;
        this.status = status;
        this.note = note;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Deprecated-style getter/setter for compatibility with old code
    public int getPayment_ID() {
        return -1; // or throw new UnsupportedOperationException();
    }

    public void setPayment_ID(int payment_ID) {
        // do nothing or throw exception, since it's deprecated
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

}
