/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.sun.org.apache.bcel.internal.Constants;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import model.dao.CartDAO;
import model.dao.MenuDAO;
import model.dto.UserDTO;
import model.dto.CartDTO;
import model.dto.MenuDTO;
import sun.net.www.protocol.http.AuthCache;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CartController", urlPatterns = {"/CartController"})
public class CartController extends HttpServlet {

    CartDAO cDAO = new CartDAO();
    MenuDAO mDAO = new MenuDAO();
    UserDTO currentUser;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        currentUser = utils.AuthUtils.getCurrentUser(request);
        String url = "cart.jsp"; // luôn quay về cart.jsp
        try {
            String action = request.getParameter("action");
            if ("removeFromCart".equals(action)) {
                url = removeItem(request, response);
                return; // tránh forward 2 lần
            } else if ("getCart".equals(action)) {
                url = getCart(request, response);
            } else if ("clearCart".equals(action)) {
                url = clearCart(request, response);
                return;
            } else if ("updateCart".equals(action)){
                url = updateCart(request,response);
            } else if ("addToCart".equals(action)){
                url = addToCart(request,response);
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
    private String addToCart(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        int menuId = Integer.parseInt(request.getParameter("menuId"));
        String from = request.getParameter("from");
        List<CartDTO> cartList = cDAO.getCartByUserID(currentUser.getUser_ID());
        for (CartDTO c : cartList){
            if(c.getMenu_ID() == menuId){
                cDAO.updateCartQuantity(currentUser.getUser_ID(),
                                        menuId,
                                        c.getQuantity() + 1);
                session.setAttribute("cart", cartList);
                return from;
            }
        }
        cDAO.addToCart(new CartDTO(currentUser.getUser_ID(),menuId,1));
       session.setAttribute("cart", cartList);

        return from;
    }
    private String removeItem(HttpServletRequest request, HttpServletResponse response) {
        
        return "cart.jsp";
    }

    private String clearCart(HttpServletRequest request, HttpServletResponse response) {
        return "cart.jsp";
    }

    private String getCart(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.setAttribute("cart", cDAO.getCartByUserID(currentUser.getUser_ID()));
        
        request.setAttribute("menuList", mDAO.getAllMenus());
        session.setAttribute("cartSum", calCartSum(currentUser.getUser_ID()));
        return "cart.jsp";
    }

    private String updateCart(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        int UserID = currentUser.getUser_ID();
        for (CartDTO c : cDAO.getCartByUserID(UserID)){
            int newQuantity = Integer.parseInt(request.getParameter("quantity"+String.valueOf(c.getMenu_ID())));
            cDAO.updateCartQuantity(UserID, c.getMenu_ID(), newQuantity);
            
            System.out.println(newQuantity);
        }   
        
        session.setAttribute("cart", cDAO.getCartByUserID(UserID));
        request.setAttribute("menuList", mDAO.getAllMenus());
        session.setAttribute("cartSum", calCartSum(currentUser.getUser_ID()));

        return "cart.jsp";
    }
    private BigDecimal calCartSum(int UserID){
        BigDecimal sum = BigDecimal.ZERO;
        List<CartDTO> cartItemList = (List<CartDTO>) cDAO.getCartByUserID(UserID);
        List<MenuDTO> menuList = (List<MenuDTO>) mDAO.getAllMenus();
        for (CartDTO item : cartItemList) {
            MenuDTO menu = mDAO.getMenuByID(item.getMenu_ID());
            sum = sum.add(menu.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        return sum;
    }
    

}
