/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    private String SELECT_ALL = "SELECT Payment_ID, Payment_code, Payment_date, Payment_status,User_ID FROM payment";

    public PaymentDAO() {
    }
    
    private List<PaymentDTO> getAll(){
        List<PaymentDTO> result = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(SELECT_ALL);
            rs = ps.executeQuery();
            
            while(rs.next()){
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
        } finally
        {
            
            
        }
        return result;
        
    }
    public static void main(String[] args) {
        PaymentDAO PDAO = new PaymentDAO();
        for (PaymentDTO p : PDAO.getAll()){
            System.out.println(p.getPayment_ID());
            System.out.println(p.getPayment_CODE());
            System.out.println(p.getPayment_DATE().toString());
            System.out.println(p.getPayment_STATUS());
            System.out.println(p.getUserID());
            System.out.println("----------------------------");
        }
    }
}
