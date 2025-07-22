package controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.dao.OrderDAO;
import model.dto.OrderDTO;
import model.dto.OrderItemDTO;
import model.dao.OrderItemDAO;
import model.dao.MenuDAO;
import model.dto.MenuDTO;

import model.dto.UserDTO;

@WebServlet(name = "OrderController", urlPatterns = {"/OrderController"})
public class OrderController extends HttpServlet {

    private final OrderDAO oDAO = new OrderDAO();
    private final OrderItemDAO iDAO = new OrderItemDAO();
    private final MenuDAO mDAO = new MenuDAO();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String url = null;

        try {
            if ("viewOrders".equals(action)) {
                url = handleViewOrders(request, response);
            } else if ("viewOrderDetail".equals(action)) {
                url = handleViewOrderDetail(request, response);
            } else {
                // Unknown action, redirect to home or main controller
                response.sendRedirect("MainController");
                return;
            }

            if (url != null) {
                request.getRequestDispatcher(url).forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("MainController?action=viewOrders");
        }
    }

    private String handleViewOrders(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return null;
        }

        String status = request.getParameter("status");
        List<OrderDTO> orders;

        if (status != null && !status.isEmpty()) {
            if ("manager".equalsIgnoreCase(user.getRole())) {
                orders = oDAO.getOrdersByStatus(status);
            } else {
                orders = oDAO.getOrdersByUserAndStatus(user.getUser_ID(), status);
            }
        } else {
            if ("manager".equalsIgnoreCase(user.getRole())) {
                orders = oDAO.getAllOrders();
            } else {
                orders = oDAO.getOrdersByUser(user.getUser_ID());
            }
        }

        request.setAttribute("orders", orders);
        return "viewOrders.jsp";
    }

    private String handleViewOrderDetail(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));

            OrderDTO order = oDAO.getOrderByID(orderId);
            List<OrderItemDTO> items = iDAO.getItemsByOrderId(orderId);
            List<MenuDTO> menuList = mDAO.getAllMenus(); // or getMenuByIds if more efficient

            BigDecimal totalPrice = BigDecimal.ZERO;
            for (OrderItemDTO item : items) {
                BigDecimal itemTotal = item.getPrice().multiply(new BigDecimal(item.getQuantity()));
                totalPrice = totalPrice.add(itemTotal);
            }

            request.setAttribute("order", order);
            request.setAttribute("items", items);
            request.setAttribute("menuList", menuList);
            request.setAttribute("totalPrice", totalPrice);

            return "viewDetail.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("MainController?action=viewOrders");
            return null;
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
        return "Handles viewing and managing user orders";
    }
}
