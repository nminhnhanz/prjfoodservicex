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

/**
 *
 * @author Admin
 */
@WebServlet(name = "MainController", urlPatterns = {"","/MainController", "/mc"})
public class MainController extends HttpServlet {

    private static final String WELCOME = "/DefaultController";

    private boolean isUserAction(String action) {
        return "login".equals(action)
                || "logout".equals(action)
                || "register".equals(action);
    }

    private boolean isMenuAction(String action) {
        return "addMenu".equals(action)
                || "searchMenu".equals(action)
                || "editMenu".equals(action)
                || "updateMenu".equals(action)
                || "deleteMenu".equals(action);
    }
    private boolean isCheckOutAction(String action){
        return "checkOut".equals(action)
                ||"createOrder".equals(action);
    }
    private boolean isCategoryAction(String action) {
        return "searchCategory".equals(action);
    }


    private boolean isCartAction(String action) {
        return "removeFromCart".equals(action)
                || "clearCart".equals(action)
                || "getCart".equals(action)
                || "updateCart".equals(action)
                || "addToCart".equals(action);
    }



    private boolean isFeedbackAction(String action) {
        return "goToFeedback".equals(action)
                || "submitFeedback".equals(action)
                || "viewFeedbackByMenu".equals(action);
    }

    private boolean isPaymentAction(String action) {
        return "createPay".equals(action);
    }
    private boolean isOrderAction(String action) {
        return "viewOrders".equals(action)
                || "viewOrderDetail".equals(action)
                || "searchOrders".equals(action)
                || "cancelOrder".equals(action);
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String url = WELCOME;
        try {
            String action = request.getParameter("action");
            System.out.println(action);

            if (isUserAction(action)) {
                url = "/UserController";
            } else if (utils.AuthUtils.isLoggedIn(request)) {
                if (isMenuAction(action)) {
                    url = "/MenuController";
                } else if (isCategoryAction(action)) {
                    url = "/CategoryController";
                } else if (isCheckOutAction(action)) {
                    url = "/CheckOutController";
                } else if (isCartAction(action)) {
                    url = "/CartController";
                } else if (isFeedbackAction(action)) {
                    url = "/FeedbackController";
                } else if (isPaymentAction(action)) {
                    url = "/PaymentController";
                } else if (isOrderAction(action)) {
                    url = "/OrderController";
                }
                else {
                    url = "/DefaultController";
                }
            }else {
                    url = "/DefaultControllfver";
                }

        } catch (Exception e) {
            e.printStackTrace();
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

}
