package model.dao;

import model.dto.OrderDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class OrderDAO {

    private static final String GET_ALL_ORDERS
            = "SELECT order_ID, order_time, user_ID, fullName, address, phone, email, payment_ID, note FROM [Order]";

    private static final String GET_ORDER_BY_ID
            = "SELECT order_ID, order_time, user_ID, fullName, address, phone, email, payment_ID, note FROM [Order] WHERE order_ID = ?";

    private static final String CREATE_ORDER
            = "INSERT INTO [Order] (Order_time, User_ID, FullName, Address, Phone, Email, Payment_ID, Note) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_ORDER
            = "UPDATE [Order] SET order_time = ?, user_ID = ?, fullName = ?, address = ?, phone = ?, email = ?, payment_ID = ?, note = ? WHERE order_ID = ?";

    private static final String DELETE_ORDER
            = "DELETE FROM [Order] WHERE order_ID = ?";

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orders = new ArrayList<>();
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(GET_ALL_ORDERS);  ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrder_ID(rs.getInt("order_ID"));
                order.setOrder_time(rs.getDate("order_time"));
                order.setUser_ID(rs.getInt("user_ID"));
                order.setFullName(rs.getString("fullName"));
                order.setAddress(rs.getString("address"));
                order.setPhone(rs.getString("phone"));
                order.setEmail(rs.getString("email"));
                order.setPayment_ID(rs.getInt("payment_ID"));
                order.setNote(rs.getString("note"));
                orders.add(order);
            }
        } catch (Exception e) {
            System.err.println("Error in getAllOrders(): " + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }

    public OrderDTO getOrderByID(String id) {
        OrderDTO order = null;
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(GET_ORDER_BY_ID)) {

            ps.setString(1, id);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    order = new OrderDTO();
                    order.setOrder_ID(rs.getInt("order_ID"));
                    order.setOrder_time(rs.getDate("order_time"));
                    order.setUser_ID(rs.getInt("user_ID"));
                    order.setFullName(rs.getString("fullName"));
                    order.setAddress(rs.getString("address"));
                    order.setPhone(rs.getString("phone"));
                    order.setEmail(rs.getString("email"));
                    order.setPayment_ID(rs.getInt("payment_ID"));
                    order.setNote(rs.getString("note"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error in getOrderByID(): " + e.getMessage());
            e.printStackTrace();
        }
        return order;
    }

    public boolean create(OrderDTO order) {
        boolean success = false;
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, order.getOrder_time());
            ps.setInt(2, order.getUser_ID());
            ps.setString(3, order.getFullName());
            ps.setString(4, order.getAddress());
            ps.setString(5, order.getPhone());
            ps.setString(6, order.getEmail());
            ps.setInt(7, order.getPayment_ID());
            ps.setString(8, order.getNote());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setOrder_ID(generatedKeys.getInt(1));
                    success = true;
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    public boolean update(OrderDTO order) {
        boolean success = false;
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(UPDATE_ORDER)) {

            ps.setDate(1, order.getOrder_time());
            ps.setInt(2, order.getUser_ID());
            ps.setString(3, order.getFullName());
            ps.setString(4, order.getAddress());
            ps.setString(5, order.getPhone());
            ps.setString(6, order.getEmail());
            ps.setInt(7, order.getPayment_ID());
            ps.setString(8, order.getNote());
            ps.setInt(9, order.getOrder_ID());

            success = (ps.executeUpdate() > 0);
        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    public boolean delete(String id) {
        boolean success = false;
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(DELETE_ORDER)) {

            ps.setString(1, id);
            success = (ps.executeUpdate() > 0);
        } catch (Exception e) {
            System.err.println("Error in delete(): " + e.getMessage());
            e.printStackTrace();
        }
        return success;
    }

    public boolean isOrderExists(String id) {
        return getOrderByID(id) != null;
    }
}
