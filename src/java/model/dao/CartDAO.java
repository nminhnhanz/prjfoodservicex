/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

import model.dto.CartDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;

/**
 *
 * @author Admin
 */
public class CartDAO {

    private static final String GET_ALL_CART_ITEMS = "SELECT User_ID, Menu_ID, Quantity FROM [Cart]";
    private static final String GET_CART_BY_USER_ID = "SELECT User_ID, Menu_ID, Quantity FROM [Cart] WHERE User_ID = ?";
    private static final String GET_CART_ITEM = "SELECT User_ID, Menu_ID, Quantity FROM [Cart] WHERE User_ID = ? AND Menu_ID = ?";
    private static final String ADD_TO_CART = "INSERT INTO [Cart](User_ID, Menu_ID, Quantity) VALUES (?, ?, ?)";
    private static final String UPDATE_CART_QUANTITY = "UPDATE [Cart] SET Quantity = ? WHERE User_ID = ? AND Menu_ID = ?";
    private static final String DELETE_CART_ITEM = "DELETE FROM [Cart] WHERE User_ID = ? AND Menu_ID = ?";
    private static final String CLEAR_CART = "DELETE FROM [Cart] WHERE User_ID = ?";

    public List<CartDTO> getAllCartItems() {
        List<CartDTO> cartItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_CART_ITEMS);
            rs = ps.executeQuery();

            while (rs.next()) {
                CartDTO cart = new CartDTO();
                cart.setUser_ID(rs.getInt("User_ID"));
                cart.setMenu_ID(rs.getInt("Menu_ID"));
                cart.setQuantity(rs.getInt("Quantity"));

                cartItems.add(cart);
            }
        } catch (Exception e) {
            System.err.println("Error in getAllCartItems(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return cartItems;
    }

    public List<CartDTO> getCartByUserID(int userId) {
        List<CartDTO> cartItems = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_CART_BY_USER_ID);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                CartDTO cart = new CartDTO();
                cart.setUser_ID(rs.getInt("User_ID"));
                cart.setMenu_ID(rs.getInt("Menu_ID"));
                cart.setQuantity(rs.getInt("Quantity"));

                cartItems.add(cart);
            }
        } catch (Exception e) {
            System.err.println("Error in getCartByUserID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return cartItems;
    }

    public CartDTO getCartItem(int userId, int menuId) {
        CartDTO cart = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_CART_ITEM);
            ps.setInt(1, userId);
            ps.setInt(2, menuId);
            rs = ps.executeQuery();

            if (rs.next()) {
                cart = new CartDTO();
                cart.setUser_ID(rs.getInt("User_ID"));
                cart.setMenu_ID(rs.getInt("Menu_ID"));
                cart.setQuantity(rs.getInt("Quantity"));
            }
        } catch (Exception e) {
            System.err.println("Error in getCartItem(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return cart;
    }

    public boolean addToCart(CartDTO cart) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(ADD_TO_CART);

            ps.setInt(1, cart.getUser_ID());
            ps.setInt(2, cart.getMenu_ID());
            ps.setInt(3, cart.getQuantity());

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in addToCart(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean updateCartQuantity(int userId, int menuId, int quantity) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_CART_QUANTITY);

            ps.setInt(1, quantity);
            ps.setInt(2, userId);
            ps.setInt(3, menuId);

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in updateCartQuantity(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean deleteCartItem(int userId, int menuId) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(DELETE_CART_ITEM);
            ps.setInt(1, userId);
            ps.setInt(2, menuId);

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in deleteCartItem(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean clearCart(int userId) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CLEAR_CART);
            ps.setInt(1, userId);

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);

        } catch (Exception e) {
            System.err.println("Error in clearCart(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
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

    public boolean isCartItemExists(int userId, int menuId) {
        return getCartItem(userId, menuId) != null;
    }

    public int getCartItemCount(int userId) {
        int count = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement("SELECT COUNT(*) FROM [Cart] WHERE User_ID = ?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Error in getCartItemCount(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return count;
    }

    public int getTotalQuantity(int userId) {
        int totalQuantity = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement("SELECT SUM(Quantity) FROM [Cart] WHERE User_ID = ?");
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                totalQuantity = rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println("Error in getTotalQuantity(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return totalQuantity;
    }

    // Method to add or update cart item (if item exists, update quantity; if not, add new item)
    public boolean addOrUpdateCartItem(int userId, int menuId, int quantity) {
        boolean success = false;
        
        if (isCartItemExists(userId, menuId)) {
            // Item exists, update quantity
            CartDTO existingItem = getCartItem(userId, menuId);
            int newQuantity = existingItem.getQuantity() + quantity;
            success = updateCartQuantity(userId, menuId, newQuantity);
        } else {
            // Item doesn't exist, add new item
            CartDTO newItem = new CartDTO();
            newItem.setUser_ID(userId);
            newItem.setMenu_ID(menuId);
            newItem.setQuantity(quantity);
            success = addToCart(newItem);
        }
        
        return success;
    }
}