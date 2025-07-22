package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.sql.Date;
import model.dto.OrderItemDTO;
import utils.DbUtils;

public class OrderItemDAO {

    public List<OrderItemDTO> getItemsByOrderId(int orderId) throws SQLException, ClassNotFoundException {
        List<OrderItemDTO> list = new ArrayList<>();
        String sql = "SELECT * FROM Order_Item WHERE Order_ID = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int menuId = rs.getInt("Menu_ID");
                int quantity = rs.getInt("Quantity");
                Date orderTime = rs.getDate("Order_Time");
                BigDecimal price = rs.getBigDecimal("Price");

                list.add(new OrderItemDTO(orderId, menuId, quantity, orderTime, price));
            }
        }
        return list;
    }

    public void insert(OrderItemDTO item) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO Order_Item (Order_ID, Menu_ID, Quantity, Order_Time, Price) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, item.getOrderID());
            stmt.setInt(2, item.getMenuID());
            stmt.setInt(3, item.getQuantity());
            stmt.setDate(4, item.getOrderTime());
            stmt.setBigDecimal(5, item.getPrice());
            stmt.executeUpdate();
        }
    }

    public void delete(int orderId, int menuId) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM Order_Item WHERE Order_ID = ? AND Menu_ID = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, menuId);
            stmt.executeUpdate();
        }
    }

    public void updateQuantity(int orderId, int menuId, int quantity) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Order_Item SET Quantity = ? WHERE Order_ID = ? AND Menu_ID = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, quantity);
            stmt.setInt(2, orderId);
            stmt.setInt(3, menuId);
            stmt.executeUpdate();
        }
    }

    public void updatePrice(int orderId, int menuId, BigDecimal price) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE Order_Item SET Price = ? WHERE Order_ID = ? AND Menu_ID = ?";

        try (Connection conn = DbUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBigDecimal(1, price);
            stmt.setInt(2, orderId);
            stmt.setInt(3, menuId);
            stmt.executeUpdate();
        }
    }
}
