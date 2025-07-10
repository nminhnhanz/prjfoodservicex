/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.DbUtils;

/**
 *
 * @author Nghia
 */
public class PaymentDAO {

    private String GET_ALL = "SELECT Payment_ID, Payment_code, Payment_date, Payment_status,User_ID FROM payment";
    private String GET_BY_USER_ID = "SELECT Payment_ID, Payment_code, Payment_date, Payment_status,User_ID FROM payment WHERE User_ID = ?";
    private String GET_BY_DATE = "SELECT Payment_ID, Payment_code, Payment_date, Payment_status,User_ID FROM payment WHERE Payment_date = ?";
    private String CREATE_PAYMENT = "INSERT INTO Payment (Payment_code,Payment_date,Payment_status,User_ID) VALUES(?,?,?,?)";
    private String GET_MAX = "SELECT MAX(ID) AS MaxID FROM payment";

    public PaymentDAO() {
    }

    private List<PaymentDTO> getAll() {
        List<PaymentDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL);

            rs = ps.executeQuery();

            while (rs.next()) {
                PaymentDTO pdto = new PaymentDTO();
                pdto.setPayment_ID(rs.getInt("Payment_ID"));
                pdto.setPayment_CODE(rs.getString("Payment_code"));
                pdto.setPayment_DATE(rs.getDate("Payment_date"));
                pdto.setPayment_STATUS(rs.getString("Payment_status"));
                pdto.setUserID(rs.getInt("User_ID"));
                result.add(pdto);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        return result;

    }

    private List<PaymentDTO> getByUserID(int userID) {
        List<PaymentDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_BY_USER_ID);
            ps.setInt(1, userID);
            rs = ps.executeQuery();

            while (rs.next()) {
                PaymentDTO pdto = new PaymentDTO();
                pdto.setPayment_ID(rs.getInt("Payment_ID"));
                pdto.setPayment_CODE(rs.getString("Payment_code"));
                pdto.setPayment_DATE(rs.getDate("Payment_date"));
                pdto.setPayment_STATUS(rs.getString("Payment_status"));
                pdto.setUserID(rs.getInt("User_ID"));
                result.add(pdto);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        return result;

    }

    private List<PaymentDTO> getByDate(Date date) {
        List<PaymentDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_BY_DATE);
            ps.setDate(1, date);
            rs = ps.executeQuery();

            while (rs.next()) {
                PaymentDTO pdto = new PaymentDTO();
                pdto.setPayment_ID(rs.getInt("Payment_ID"));
                pdto.setPayment_CODE(rs.getString("Payment_code"));
                pdto.setPayment_DATE(rs.getDate("Payment_date"));
                pdto.setPayment_STATUS(rs.getString("Payment_status"));
                pdto.setUserID(rs.getInt("User_ID"));
                result.add(pdto);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PaymentDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

        }
        return result;

    }

    private boolean createPayment(int UserID) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String insertSQL = "INSERT INTO Payment (Payment_code, Payment_date, Payment_status, User_ID) VALUES (?, ?, ?, ?)";
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, null); // or temporary placeholder
            ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            ps.setString(3, "Pending");
            ps.setInt(4, UserID);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet keys = ps.getGeneratedKeys();
                if (keys.next()) {
                    int generatedId = keys.getInt(1);
                    String paymentCode = "PAY" + generatedId;

                    String updateSQL = "UPDATE Payment SET Payment_code = ? WHERE Payment_ID = ?";
                    try ( PreparedStatement ps2 = conn.prepareStatement(updateSQL)) {
                        ps2.setString(1, paymentCode);
                        ps2.setInt(2, generatedId);
                        ps2.executeUpdate();
                    }
                }
                return true;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        PaymentDAO PDAO = new PaymentDAO();
        PaymentDTO testDTO = new PaymentDTO();
        PDAO.createPayment(3);
        
    }
}
