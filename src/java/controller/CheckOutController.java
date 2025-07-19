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
import model.dto.UserDTO;
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
        // Get form parameters
        String name = request.getParameter("order_fullName");
        String address = request.getParameter("order_address");
        String phone = request.getParameter("order_phone"); // optional, not stored in OrderDTO
        String email = request.getParameter("order_email"); // optional, not stored in OrderDTO
        String note = request.getParameter("order_note");
        
            System.out.println(name);
            System.out.println(note);
        // Get current date
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

        // Get user from session
        HttpSession session = request.getSession();
        int userID = 0;
        if (session != null && session.getAttribute("user") != null) {
            UserDTO user = (UserDTO) session.getAttribute("user");
            userID = user.getUser_ID();
        }

        // Assume payment_ID = 1 (or use logic to determine it)
        int paymentID = 1;

        // Build OrderDTO
        OrderDTO orderdto = new OrderDTO();
        orderdto.setFullName(name);
        orderdto.setAddress(address);
        orderdto.setNote(note);
        orderdto.setOrder_time(currentDate);
        orderdto.setUser_ID(userID);
        orderdto.setPayment_ID(paymentID);

        // Save to DB
        OrderDAO orderDAO = new OrderDAO();
        boolean success = orderDAO.create(orderdto);
            System.out.println("SUCCESS " + success);
        if (success) {
            request.setAttribute("message", "Order created successfully!");
            // Redirect or forward to confirmation page
            return "orderSuccess.jsp";
        } else {
            request.setAttribute("error", "Failed to create order.");
            return "checkout.jsp";
        }

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "An error occurred during order creation.");
        return "checkout.jsp";
    }
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

    private String checkOutProcessing(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("menuList", mdao.getAllMenus());
        return "checkout.jsp";
    }
}
