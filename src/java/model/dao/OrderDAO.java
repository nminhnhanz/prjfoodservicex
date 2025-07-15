/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author ASUS
 */
import model.dto.OrderDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

public class OrderDAO {

    private static final String GET_ALL_ORDERS
            = "SELECT o.order_ID, o.order_time, o.user_ID, o.payment_ID, "
            + "oi.menu_ID, oi.order_quantity, oi.order_price_time "
            + "FROM [Order] o "
            + "JOIN [Order_Item] oi ON o.order_ID = oi.order_ID";

    private static final String GET_ORDER_BY_ID
            = "SELECT o.order_ID, o.order_time, o.user_ID, o.payment_ID, "
            + "oi.menu_ID, oi.order_quantity, oi.order_price_time "
            + "FROM [Order] o "
            + "JOIN [Order_Item] oi ON o.order_ID = oi.order_ID "
            + "WHERE o.order_ID = ?";

    private static final String CREATE_ORDER
            = "INSERT INTO [Order](order_time, user_ID, payment_ID) VALUES (?, ?, ?)";

    private static final String CREATE_ORDER_ITEM
            = "INSERT INTO [Order_Item](order_ID, menu_ID, order_quantity, order_price_time) VALUES (?, ?, ?, ?)";

    private static final String UPDATE_ORDER
            = "UPDATE [Order] SET order_time = ?, user_ID = ?, payment_ID = ? WHERE order_ID = ?";

    private static final String DELETE_ORDER
            = "DELETE FROM [Order] WHERE order_ID = ?";

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orders = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_ORDERS);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderDTO order = new OrderDTO();
                order.setOrder_ID(rs.getInt("order_ID"));
                order.setOrder_time(rs.getDate("order_time"));
                order.setUser_ID(rs.getInt("user_ID"));
                order.setPayment_ID(rs.getInt("payment_ID"));
                order.setMenu_ID(rs.getInt("menu_ID"));
                order.setOrder_quantity(rs.getInt("order_quantity"));
                order.setOrder_price_time(rs.getDate("order_price_time"));

                orders.add(order);
            }
        } catch (Exception e) {
            System.err.println("Error in getAllOrders(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return orders;
    }
    
        public List<OrderDTO> getOrderItemsByOrderId(int orderId) {
        List<OrderDTO> orderItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ORDER_BY_ID);
            ps.setInt(1, orderId);
            rs = ps.executeQuery();

            while (rs.next()) {
                OrderDTO orderItem = new OrderDTO();
                orderItem.setOrder_ID(rs.getInt("order_ID"));
                orderItem.setOrder_time(rs.getDate("order_time"));
                orderItem.setUser_ID(rs.getInt("user_ID"));
                orderItem.setPayment_ID(rs.getInt("payment_ID"));
                orderItem.setMenu_ID(rs.getInt("menu_ID"));
                orderItem.setOrder_quantity(rs.getInt("order_quantity"));
                orderItem.setOrder_price_time(rs.getDate("order_price_time"));
                orderItems.add(orderItem);
            }
        } catch (Exception e) {
            System.err.println("Error in getCartItemsByCartId(): " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }

        return orderItems;
    }

    public OrderDTO getOrderByID(String id) {
        OrderDTO order = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ORDER_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                order = new OrderDTO();
                order.setOrder_ID(rs.getInt("order_ID"));
                order.setOrder_time(rs.getDate("order_time"));
                order.setUser_ID(rs.getInt("user_ID"));
                order.setPayment_ID(rs.getInt("payment_ID"));
                order.setMenu_ID(rs.getInt("menu_ID"));
                order.setOrder_quantity(rs.getInt("order_quantity"));
                order.setOrder_price_time(rs.getDate("order_price_time"));
            }
        } catch (Exception e) {
            System.err.println("Error in getOrderByID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return order;
    }

    public boolean create(OrderDTO order) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psItem = null;
        ResultSet generatedKeys = null;

        try {
            conn = DbUtils.getConnection();
            conn.setAutoCommit(false); // bắt đầu transaction

            // Insert into Order
            psOrder = conn.prepareStatement(CREATE_ORDER, Statement.RETURN_GENERATED_KEYS);
            psOrder.setDate(1, order.getOrder_time());
            psOrder.setInt(2, order.getUser_ID());
            psOrder.setInt(3, order.getPayment_ID());
            psOrder.executeUpdate();

            // Lấy order_ID vừa được tạo
            generatedKeys = psOrder.getGeneratedKeys();
            if (generatedKeys.next()) {
                int generatedOrderID = generatedKeys.getInt(1);
                order.setOrder_ID(generatedOrderID);
            } else {
                throw new SQLException("Không lấy được order_ID sau khi insert");
            }

            // Insert into Order_Item
            psItem = conn.prepareStatement(CREATE_ORDER_ITEM);
            psItem.setInt(1, order.getOrder_ID());
            psItem.setInt(2, order.getMenu_ID());
            psItem.setInt(3, order.getOrder_quantity());
            psItem.setDate(4, order.getOrder_price_time());
            psItem.executeUpdate();

            conn.commit();
            success = true;
        } catch (Exception e) {
            success = false;
            System.err.println(" Lỗi khi tạo đơn hàng: " + e.getMessage());
            e.printStackTrace();
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception rollbackEx) {
                System.err.println("Rollback thất bại: " + rollbackEx.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            closeResources(null, psOrder, generatedKeys);
            closeResources(conn, psItem, null);
        }

        return success;
    }

    public boolean update(OrderDTO order) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_ORDER);
            ps.setDate(1, order.getOrder_time());
            ps.setInt(2, order.getUser_ID());
            ps.setInt(3, order.getPayment_ID());
            ps.setInt(4, order.getOrder_ID());

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean delete(String id) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(DELETE_ORDER);
            ps.setString(1, id);

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
        } catch (Exception e) {
            System.err.println("Error in delete(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean isOrderExists(String id) {
        return getOrderByID(id) != null;
    }

    private void closeResources(Connection conn, PreparedStatement ps, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            System.err.println("Error closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
