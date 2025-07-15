/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import model.dao.CartDAO;
import model.dto.CartDTO;
import model.dao.MenuDAO;
import model.dto.MenuDTO;
import model.dao.OrderDAO;
import model.dto.OrderDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "TotalPriceController", urlPatterns = {"/TotalPriceController"})
public class TotalPriceController extends HttpServlet {

    CartDAO cartdao = new CartDAO();
    OrderDAO ordao = new OrderDAO();
    MenuDAO mdao = new MenuDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = "";
        try {
            String action = request.getParameter("action");
            if (action.equals("sumMoney")) {
                url = sumMoney(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private String sumMoney(HttpServletRequest request, HttpServletResponse response) {
        try {
            String type = request.getParameter("type"); // "cart" or "order"
            String idParam = request.getParameter("id"); // Cart_ID or Order_ID

            if (type == null || idParam == null) {
                request.setAttribute("error", "Missing required parameters: type and id");
                return "cart".equals(type) ? "cart.jsp" : "orderSuccess.jsp";
            }

            double totalPrice = 0.0;

            if ("cart".equals(type)) {
                // Tính tổng từ SESSION cart thay vì database
                totalPrice = calculateSessionCartTotal(request);
                request.setAttribute("cartId", idParam);

                // Set attributes for JSP
                request.setAttribute("totalPrice", totalPrice);
                request.setAttribute("type", type);
                request.setAttribute("formattedTotal", String.format("%.2f", totalPrice));

                return "cart.jsp";

            } else if ("order".equals(type)) {
                // Tính tổng từ database cho order
                int id = Integer.parseInt(idParam);
                totalPrice = calculateOrderTotal(id);
                request.setAttribute("orderId", id);

                // Set attributes for JSP
                request.setAttribute("totalPrice", totalPrice);
                request.setAttribute("type", type);
                request.setAttribute("formattedTotal", String.format("%.2f", totalPrice));

                return "orderSuccess.jsp";

            } else {
                request.setAttribute("error", "Invalid type parameter. Use 'cart' or 'order'");
                return "cart.jsp";
            }

        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid ID format. Please provide a valid number.");
            String type = request.getParameter("type");
            return "cart".equals(type) ? "cart.jsp" : "orderSuccess.jsp";

        } catch (Exception e) {
            request.setAttribute("error", "Error calculating total price: " + e.getMessage());
            String type = request.getParameter("type");
            return "cart".equals(type) ? "cart.jsp" : "orderSuccess.jsp";
        }
    }

    /**
     * Tính tổng tiền từ session cart (cùng nguồn dữ liệu với cart.jsp)
     */
    private double calculateSessionCartTotal(HttpServletRequest request) {
        BigDecimal total = BigDecimal.ZERO;
        try {
            HttpSession session = request.getSession();
            @SuppressWarnings("unchecked")
            List<OrderDTO> cart = (List<OrderDTO>) session.getAttribute("cart");
            
            if (cart != null && !cart.isEmpty()) {
                for (OrderDTO item : cart) {
                    // Get menu item details by Menu_ID
                    MenuDTO menuItem = mdao.getMenuByID(item.getMenu_ID());
                    if (menuItem != null) {
                        // Calculate: Price * Order_quantity
                        BigDecimal quantity = BigDecimal.valueOf(item.getOrder_quantity());
                        total = total.add(menuItem.getPrice().multiply(quantity));
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calculating session cart total: " + e.getMessage());
        }
        return total.doubleValue();
    }

    /**
     * Calculates total price for all items in a cart from database
     */
    private double calculateCartTotal(int cartId) {
        BigDecimal total = BigDecimal.ZERO;
        try {
            List<CartDTO> cartItems = cartdao.getCartItemsByCartId(cartId);

            for (CartDTO cartItem : cartItems) {
                MenuDTO menuItem = mdao.getMenuByID(cartItem.getMenu_ID());
                if (menuItem != null) {
                    BigDecimal quantity = BigDecimal.valueOf(cartItem.getFood_quantity());
                    total = total.add(menuItem.getPrice().multiply(quantity));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calculating cart total: " + e.getMessage());
        }
        return total.doubleValue();
    }

    /**
     * Calculates total price for all items in an order
     */
    private double calculateOrderTotal(int orderId) {
        BigDecimal total = BigDecimal.ZERO;
        try {
            List<OrderDTO> orderItems = ordao.getOrderItemsByOrderId(orderId);

            for (OrderDTO item : orderItems) {
                MenuDTO menuItem = mdao.getMenuByID(item.getMenu_ID());
                if (menuItem != null) {
                    BigDecimal quantity = BigDecimal.valueOf(item.getOrder_quantity());
                    total = total.add(menuItem.getPrice().multiply(quantity));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error calculating order total: " + e.getMessage());
        }
        return total.doubleValue();
    }
}
