package model.dao;

import java.math.BigDecimal;
import model.dto.OrderDTO;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class OrderDAO {

    private static final String GET_ALL_ORDERS
            = "SELECT order_ID, order_time, user_ID, fullName, address, phone, email, payment_method, note, status FROM [Order]";

    private static final String GET_ORDER_BY_ID
            = "SELECT order_ID, order_time, user_ID, fullName, address, phone, email, payment_method, note, status FROM [Order] WHERE order_ID = ?";

    private static final String CREATE_ORDER
            = "INSERT INTO [Order] (Order_time, User_ID, FullName, Address, Phone, Email, Payment_Method, Note, Status) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_ORDER
            = "UPDATE [Order] SET order_time = ?, user_ID = ?, fullName = ?, address = ?, phone = ?, email = ?, payment_method = ?, note = ?, status = ? WHERE order_ID = ?";

    private static final String DELETE_ORDER
            = "DELETE FROM [Order] WHERE order_ID = ?";

    private static final String INSERT_ORDER_ITEM
            = "INSERT INTO Order_Item (Order_ID, Menu_ID, Quantity, Order_Time) VALUES (?, ?, ?, ?)";

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
                order.setPayment_method(rs.getString("payment_method"));
                order.setNote(rs.getString("note"));
                order.setStatus(rs.getString("status"));
                orders.add(order);
            }
        } catch (Exception e) {
            System.err.println("Error in getAllOrders(): " + e.getMessage());
            e.printStackTrace();
        }
        return orders;
    }

    public OrderDTO getOrderByID(int id) {
        OrderDTO order = null;
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(GET_ORDER_BY_ID)) {

            ps.setInt(1, id);
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
                    order.setPayment_method(rs.getString("payment_method"));
                    order.setNote(rs.getString("note"));
                    order.setStatus(rs.getString("status"));
                }
            }
        } catch (Exception e) {
            System.err.println("Error in getOrderByID(): " + e.getMessage());
            e.printStackTrace();
        }
        return order;
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
            ps.setString(7, order.getPayment_method());
            ps.setString(8, order.getNote());
            ps.setString(9, order.getStatus());
            ps.setInt(10, order.getOrder_ID());

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

    public boolean insertOrderItem(int orderID, int menuID, int quantity, Date orderTime) {
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(INSERT_ORDER_ITEM)) {

            ps.setInt(1, orderID);
            ps.setInt(2, menuID);
            ps.setInt(3, quantity);
            ps.setDate(4, orderTime);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            System.err.println("Error in insertOrderItem(): " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public int create(OrderDTO order) {
        int generatedID = -1;
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, order.getOrder_time());
            ps.setInt(2, order.getUser_ID());
            ps.setString(3, order.getFullName());
            ps.setString(4, order.getAddress());
            ps.setString(5, order.getPhone());
            ps.setString(6, order.getEmail());
            ps.setString(7, order.getPayment_method());
            ps.setString(8, order.getNote());
            ps.setString(9, order.getStatus());

            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try ( ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    generatedID = generatedKeys.getInt(1);
                    order.setOrder_ID(generatedID);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }

        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        }
        return generatedID;
    }

    public List<OrderDTO> getOrdersByUserAndStatus(int userId, String statusFilter) {
        List<OrderDTO> list = new ArrayList<>();
        Connection conn = null;

        try {
            conn = DbUtils.getConnection();
            String sql = "SELECT * FROM [Order] WHERE User_ID = ?"
                    + (statusFilter != null && !statusFilter.isEmpty() ? " AND Status = ?" : "");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            if (statusFilter != null && !statusFilter.isEmpty()) {
                ps.setString(2, statusFilter);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDTO order = new OrderDTO(
                        rs.getInt("Order_ID"),
                        rs.getDate("Order_Time"),
                        rs.getInt("User_ID"),
                        rs.getString("FullName"),
                        rs.getString("Address"),
                        rs.getString("Phone"),
                        rs.getString("Email"),
                        rs.getString("Payment_Method"),
                        rs.getString("Note"),
                        rs.getString("Status")
                );
                list.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<OrderDTO> getOrdersByStatus(String status) throws SQLException {
        List<OrderDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM [Order] WHERE Status = ?";
        try {
            Connection conn = DbUtils.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrder_ID(rs.getInt("Order_ID"));
                order.setOrder_time(rs.getDate("Order_Time"));
                order.setUser_ID(rs.getInt("User_ID"));
                order.setFullName(rs.getString("FullName"));
                order.setAddress(rs.getString("Address"));
                order.setPhone(rs.getString("Phone"));
                order.setEmail(rs.getString("Email"));
                order.setPayment_method(rs.getString("Payment_Method"));
                order.setNote(rs.getString("Note"));
                order.setStatus(rs.getString("Status"));
                list.add(order);
            }
        } catch (Exception e) {
            System.err.println("Error in getOrdersByStatus(): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public List<OrderDTO> getOrdersByUser(int userId) throws SQLException {
        List<OrderDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM [Order] WHERE User_ID = ?";
        try (
                 Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrder_ID(rs.getInt("Order_ID"));
                order.setOrder_time(rs.getDate("Order_Time"));
                order.setUser_ID(rs.getInt("User_ID"));
                order.setFullName(rs.getString("FullName"));
                order.setAddress(rs.getString("Address"));
                order.setPhone(rs.getString("Phone"));
                order.setEmail(rs.getString("Email"));
                order.setPayment_method(rs.getString("Payment_Method"));
                order.setNote(rs.getString("Note"));
                order.setStatus(rs.getString("Status"));
                list.add(order);
            }
        } catch (Exception e) {
            System.err.println("Error in getOrdersByUser(): " + e.getMessage());
            e.printStackTrace();
        }
        return list;
    }

    public BigDecimal getTotalPriceForOrder(int orderID) throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(m.Price * oi.Quantity) "
               + "FROM Order_Item oi "
               + "JOIN Menu m ON oi.Menu_ID = m.Menu_ID "
               + "WHERE oi.Order_ID = ?";

        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                BigDecimal total = rs.getBigDecimal(1);
                return total != null ? total : BigDecimal.ZERO;
            }
        }
        return BigDecimal.ZERO;
    }

    

    public boolean isOrderExists(int id) {
        return getOrderByID(id) != null;
    }
}
