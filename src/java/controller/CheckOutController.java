package controller;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import model.dao.MenuDAO;
import model.dao.OrderDAO;
import model.dto.CartDTO;
import model.dto.MenuDTO;
import model.dto.OrderDTO;
import model.dto.UserDTO;
import model.dao.CartDAO;

@WebServlet(name = "CheckOutController", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

    OrderDAO odao = new OrderDAO();
    MenuDAO mdao = new MenuDAO();
    CartDAO cDAO = new CartDAO();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = "";
        try {
            String action = request.getParameter("action");
            if ("createOrder".equals(action)) {
                url = handleCreateOrder(request, response);
            } else if ("checkOut".equals(action)) {
                url = prepareCheckoutPage(request, response);
            } else {
                url = "error.jsp";
            }
        } catch (Exception e) {
            e.printStackTrace();
            url = "error.jsp";
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

    private String handleCreateOrder(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = request.getParameter("order_fullName");
            String address = request.getParameter("order_address");
            String phone = request.getParameter("order_phone");
            String email = request.getParameter("order_email");
            String note = request.getParameter("order_note");
            String paymentMethod = request.getParameter("paymentMethod");
            String status = "pending";
            Date orderTime = new Date(System.currentTimeMillis());

            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("user");
            List<CartDTO> cartList = (List<CartDTO>) session.getAttribute("cart");

            if (user == null || cartList == null || cartList.isEmpty()) {
                request.setAttribute("error", "No user or cart found.");
                return "checkout.jsp";
            }

            OrderDTO order = new OrderDTO();
            order.setFullName(name);
            order.setAddress(address);
            order.setPhone(phone);
            order.setEmail(email);
            order.setNote(note);
            order.setOrder_time(orderTime);
            order.setUser_ID(user.getUser_ID());
            order.setPayment_method(paymentMethod);
            order.setStatus(status);
            int orderID = odao.create(order); // returns generated Order_ID

            if (orderID == -1) {
                request.setAttribute("error", "Failed to create order.");
                return "checkout.jsp";
            }
            BigDecimal totalPrice = BigDecimal.ZERO;
            for (CartDTO cart : cartList) {
                int menuID = cart.getMenu_ID();
                int quantity = cart.getQuantity();

                MenuDTO menu = mdao.getMenuByID(menuID);
                BigDecimal price = (menu != null) ? menu.getPrice() : BigDecimal.ZERO;
                totalPrice = totalPrice.add(price.multiply(BigDecimal.valueOf(quantity)));
                boolean inserted = odao.insertOrderItem(orderID, menuID, quantity, orderTime);
                if (!inserted) {
                    System.out.println("Failed to insert item: " + menuID);
                }
            }

            cDAO.clearCart(user.getUser_ID());
            session.removeAttribute("cart"); // clear cart
            request.setAttribute("totalPrice", totalPrice);
            request.setAttribute("orderID",orderID);
            request.setAttribute("paymentMethod",paymentMethod);

            request.setAttribute("message", "Order placed successfully!");

            return "orderSuccess.jsp";

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unexpected error occurred.");
            return "checkout.jsp";
        }
    }

    private String prepareCheckoutPage(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("menuList", mdao.getAllMenus());
        return "checkout.jsp";
    }

    @Override
    public String getServletInfo() {
        return "Handles checkout process";
    }
}
