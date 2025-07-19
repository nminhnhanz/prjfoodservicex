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
import java.util.ArrayList;
import java.util.List;
import model.dao.MenuDAO;
import model.dto.MenuDTO;
import model.dao.OrderDAO;
import model.dto.OrderDTO;
import model.dao.CartDAO;
/**
 *
 * @author ASUS
 */
@WebServlet(name = "CheckOutController", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

    OrderDAO odao = new OrderDAO();
    MenuDAO mdao = new MenuDAO();
    CartDAO cdao = new CartDAO();
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = "";
        try {
            String action = request.getParameter("action");
            if (action.equals("createOrder")) {
                url = OrderCreatingCenter(request, response);
            } else if (action.equals("goToOrder")) {
                url = OrderFormingCenter(request, response);
            } else if (action.equals("checkOut")){
                url = checkOutProcessing(request,response);
            }
        } catch (Exception e) {
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private String OrderCreatingCenter(HttpServletRequest request, HttpServletResponse response) {
        try {
            // Lấy tham số từ form
            String menu_ID_str = request.getParameter("menu_ID");
            String quantity_str = request.getParameter("order_quantity");
            String priceTime_str = request.getParameter("order_price_time");
            String note = request.getParameter("order_note");
            System.out.println("Received Parameter:");
            System.out.println("menu_ID = " + menu_ID_str);
            System.out.println("quantity = " + quantity_str);
            System.out.println("priceTime = " + priceTime_str);

            // Kiểm tra hợp lệ
            if (menu_ID_str == null || quantity_str == null || priceTime_str == null
                    || menu_ID_str.isEmpty() || quantity_str.isEmpty() || priceTime_str.isEmpty()) {
                request.setAttribute("error", "Missing order information.");
                return "orderFail.jsp";
            }

        }
        finally{
            
        }
        return "";
    }

    private String OrderFormingCenter(HttpServletRequest request, HttpServletResponse response) {
        try {
            String menu_ID = request.getParameter("menu_ID");
            MenuDAO mdao = new MenuDAO();

            int menu_id_value = 0;
            try {
                menu_id_value = Integer.parseInt(menu_ID);

            } catch (Exception e) {
            }

            MenuDTO menu = mdao.getMenuByID(menu_id_value);

            if (menu != null) {
                request.setAttribute("menu", menu);
                return "order.jsp";
            } else {
                request.setAttribute("error", "Menu not found");
                return "orderFail.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error OrderFormingCenter: " + e.getMessage());
            return "orderFail.jsp";
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
     * Calculates total price for all items in an order
     */
    private double calculateOrderTotal(int orderId) {
        BigDecimal total = BigDecimal.ZERO;
        try {
            List<OrderDTO> orderItems = odao.getOrderItemsByOrderId(orderId);

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

    private String checkOutProcessing(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("menuList", mdao.getAllMenus());
        return "checkout.jsp";
    }
}
