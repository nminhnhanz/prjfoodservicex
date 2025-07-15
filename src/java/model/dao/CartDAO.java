/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import model.dto.CartDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author ASUS
 */
public class CartDAO {

    private static final String GET_ALL_CARTS = "SELECT c.cart_ID, c.cart_status, c.user_ID, ci.menu_ID, ci.food_quantity, ci.cart_price_time FROM [Cart] c join [Cart_Item] ci on c.cart_ID = ci.cart_ID";
    private static final String GET_CART_BY_ID = "SELECT c.cart_ID, c.cart_status, c.user_ID, ci.menu_ID, ci.food_quantity, ci.cart_price_time FROM [Cart] c join [Cart_Item] ci on c.cart_ID = ci.cart_ID WHERE c.cart_ID = ?";
    private static final String CREATE_CART = "INSERT INTO [Cart](cart_status, user_ID) VALUES (?, ?)";
    private static final String CREATE_CART_ITEM = "INSERT INTO [Cart_Item](cart_ID, menu_ID, food_quantity, cart_price_time) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_CART = "UPDATE [Cart] SET cart_status = ?, user_ID = ? WHERE cart_ID = ?";
    private static final String DELETE_CART = "DELETE FROM [Cart] WHERE cart_ID = ?";
    private static final String DELETE_CART_ITEM = "DELETE FROM [Cart_Item] WHERE cart_ID = ?";

    public List<CartDTO> getAllCarts() {
        List<CartDTO> carts = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_CARTS);
            rs = ps.executeQuery();

            while (rs.next()) {
                CartDTO cart = new CartDTO();
                cart.setCart_ID(rs.getInt("cart_ID"));
                cart.setCart_status(rs.getString("cart_status"));
                cart.setUser_ID(rs.getInt("user_ID"));
                cart.setMenu_ID(rs.getInt("menu_ID"));
                cart.setFood_quantity(rs.getInt("food_quantity"));
                cart.setCart_price_time(rs.getDate("cart_price_time"));
                carts.add(cart);
            }
        } catch (Exception e) {
            System.err.println("Error in getAllCarts(): " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }
        return carts;
    }

    // Get by ID
    public CartDTO getCartByID(String id) {
        CartDTO cart = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_CART_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                cart = new CartDTO();
                cart.setCart_ID(rs.getInt("cart_ID"));
                cart.setCart_status(rs.getString("cart_status"));
                cart.setUser_ID(rs.getInt("user_ID"));
                cart.setMenu_ID(rs.getInt("menu_ID"));
                cart.setFood_quantity(rs.getInt("food_quantity"));
                cart.setCart_price_time(rs.getDate("cart_price_time"));
            }
        } catch (Exception e) {
            System.err.println("Error in getCartByID(): " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }

        return cart;
    }

    /**
     * Get all cart items for a specific cart ID This method returns all items
     * in the cart, which is what you need for total price calculation
     *
     * @param cartId The cart ID to get items for
     * @return List of CartDTO objects representing all items in the cart
     */
    public List<CartDTO> getCartItemsByCartId(int cartId) {
        List<CartDTO> cartItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_CART_BY_ID);
            ps.setInt(1, cartId);
            rs = ps.executeQuery();

            while (rs.next()) {
                CartDTO cartItem = new CartDTO();
                cartItem.setCart_ID(rs.getInt("cart_ID"));
                cartItem.setCart_status(rs.getString("cart_status"));
                cartItem.setUser_ID(rs.getInt("user_ID"));
                cartItem.setMenu_ID(rs.getInt("menu_ID"));
                cartItem.setFood_quantity(rs.getInt("food_quantity"));
                cartItem.setCart_price_time(rs.getDate("cart_price_time"));
                cartItems.add(cartItem);
            }
        } catch (Exception e) {
            System.err.println("Error in getCartItemsByCartId(): " + e.getMessage());
        } finally {
            closeResources(conn, ps, rs);
        }

        return cartItems;
    }

    // Create cart and cart items (transaction)
    public boolean create(CartDTO cart) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement psCart = null;
        PreparedStatement psItem = null;
        ResultSet generatedKeys = null;

        try {
            conn = DbUtils.getConnection();
            conn.setAutoCommit(false);

            psCart = conn.prepareStatement(CREATE_CART, Statement.RETURN_GENERATED_KEYS);
            psCart.setString(1, cart.getCart_status());
            psCart.setInt(2, cart.getUser_ID());
            psCart.executeUpdate();

            generatedKeys = psCart.getGeneratedKeys();
            if (generatedKeys.next()) {
                int cartID = generatedKeys.getInt(1);
                cart.setCart_ID(cartID);
            } else {
                throw new SQLException("Failed to get cart_ID");
            }

            psItem = conn.prepareStatement(CREATE_CART_ITEM);
            psItem.setInt(1, cart.getCart_ID());
            psItem.setInt(2, cart.getMenu_ID());
            psItem.setInt(3, cart.getFood_quantity());
            psItem.setDate(4, cart.getCart_price_time());
            psItem.executeUpdate();

            conn.commit();
            success = true;
        } catch (Exception e) {
            System.err.println("Error creating cart: " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                System.err.println("Rollback failed: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            closeResources(null, psCart, generatedKeys);
            closeResources(conn, psItem, null);
        }

        return success;
    }

    // Update cart only
    public boolean update(CartDTO cart) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_CART);
            ps.setString(1, cart.getCart_status());
            ps.setInt(2, cart.getUser_ID());
            ps.setInt(3, cart.getCart_ID());

            int rows = ps.executeUpdate();
            success = (rows > 0);
        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    // Delete cart (and its items first)
    public boolean delete(String id) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement psItem = null;
        PreparedStatement psCart = null;

        try {
            conn = DbUtils.getConnection();
            conn.setAutoCommit(false);

            psItem = conn.prepareStatement(DELETE_CART_ITEM);
            psItem.setString(1, id);
            psItem.executeUpdate();

            psCart = conn.prepareStatement(DELETE_CART);
            psCart.setString(1, id);
            psCart.executeUpdate();

            conn.commit();
            success = true;
        } catch (Exception e) {
            System.err.println("Error in delete(): " + e.getMessage());
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (Exception ex) {
                System.err.println("Rollback failed: " + ex.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            closeResources(null, psItem, null);
            closeResources(conn, psCart, null);
        }

        return success;
    }

    public boolean isCartExists(String id) {
        return getCartByID(id) != null;
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

    public int getOrCreateCartIdByUserId(int userId) throws Exception {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();

            // 1. Kiểm tra cart đã tồn tại chưa (chỉ lấy cart chưa hoàn tất)
            String checkSql = "SELECT cart_ID FROM [Cart] WHERE user_ID = ? AND cart_status = 'pending'";
            ps = conn.prepareStatement(checkSql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("cart_ID");
            }

            // 2. Nếu chưa có, tạo mới
            closeResources(null, ps, rs); // đóng trước khi tạo mới

            String insertSql = "INSERT INTO [Cart](cart_status, user_ID) VALUES (?, ?)";
            ps = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, "pending"); // default status
            ps.setInt(2, userId);
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1); // trả về cart_ID mới tạo
            } else {
                throw new SQLException("Không thể tạo cart mới cho user.");
            }

        } finally {
            closeResources(conn, ps, rs);
        }
    }
}
