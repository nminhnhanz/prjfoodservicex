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
import java.util.List;
import model.dao.FeedbackDAO;
import model.dto.FeedbackDTO;
import model.dao.MenuDAO;
import model.dto.MenuDTO;
import model.dto.UserDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "FeedbackController", urlPatterns = {"/FeedbackController"})
public class FeedbackController extends HttpServlet {
    
    FeedbackDAO fdao = new FeedbackDAO();

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
            if(action.equals("goToFeedback")){
               url = goToFeedbackPage(request, response);
            } else if(action.equals("submitFeedback")){
                url = SubmitFeedbackCenter(request, response);
            }else if(action.equals("viewFeedbackByMenu")){
                url = viewFeedbackByMenu(request, response);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error in controller: " + e.getMessage());
       
        }finally{
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
    
    private String SubmitFeedbackCenter(HttpServletRequest request, HttpServletResponse response) {
    try {
        String ratingStr = request.getParameter("rating");
        String comment = request.getParameter("comment");
        String menuIdStr = request.getParameter("menu_ID");

        int menu_id_value = 0;
            try {
                menu_id_value = Integer.parseInt(menuIdStr);

            } catch (Exception e) {
        }
            
        if (ratingStr == null || ratingStr.trim().isEmpty()) {
            request.setAttribute("error", "Rating is required");
            return "feedback.jsp";
        }

        int rating;
        try {
            rating = Integer.parseInt(ratingStr.trim());
            if (rating < 1 || rating > 5) {
                request.setAttribute("error", "Rating must be between 1 and 5");
                return "feedback.jsp";
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Rating must be a valid number");
            return "feedback.jsp";
        }

        if (menuIdStr == null || menuIdStr.trim().isEmpty()) {
            request.setAttribute("error", "Menu ID is required");
            return "feedback.jsp";
        }

        int menu_ID;
        try {
            menu_ID = Integer.parseInt(menuIdStr.trim());
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid Menu ID");
            return "feedback.jsp";
        }

        if (comment != null && !comment.trim().isEmpty() && comment.trim().length() < 5) {
            request.setAttribute("error", "Comment must be at least 5 characters long");
            return "feedback.jsp";
        }

        
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        if (user == null) {
            request.setAttribute("error", "You must be logged in to submit feedback.");
            return "login.jsp";
        }
        int user_ID = user.getUser_ID();

        FeedbackDTO feedback = new FeedbackDTO(0, rating, comment, user_ID, menu_ID);
        boolean success = fdao.create(feedback);
        
        if (success) {
            request.setAttribute("success", "Feedback submitted successfully!");
            MenuDAO mdao = new MenuDAO();
            MenuDTO menu = mdao.getMenuByID(menu_id_value);
            request.setAttribute("menu", menu);
            request.setAttribute("prev_rating", ratingStr);
            request.setAttribute("prev_comment", comment);
            return "feedback.jsp";
        } else {
            request.setAttribute("error", "Failed to submit feedback. Please try again.");
            return "feedback.jsp";
        }

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
        return "feedback.jsp";
    }
}


    private String goToFeedbackPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            String menu_ID = request.getParameter("menu_ID");
            int menu_id_value = 0;
            try {
                menu_id_value = Integer.parseInt(menu_ID);

            } catch (Exception e) {
            }
            
            MenuDAO mdao = new MenuDAO();
            MenuDTO menu = mdao.getMenuByID(menu_id_value);
            request.setAttribute("menu", menu);
            
            if (menu_ID == null || menu_ID.isEmpty()) {
                request.setAttribute("error", "Menu ID missing");
                return "feedback.jsp";  
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Unable to load menu info");
        }
        return "feedback.jsp";
    }
    
    
    private String viewFeedbackByMenu(HttpServletRequest request, HttpServletResponse response) {
        try {
            String menuIdStr = request.getParameter("menu_ID");
            if (menuIdStr == null || menuIdStr.trim().isEmpty()) {
                request.setAttribute("error", "Menu ID is missing");
                return "menuFeedbacks.jsp";
            }
            
            int menu_id_value = 0;
            try {
                menu_id_value = Integer.parseInt(menuIdStr);

            } catch (Exception e) {
            }
            int menuID = Integer.parseInt(menuIdStr.trim());

            List<FeedbackDTO> feedbacks = fdao.getFeedbacksByMenuID(menuID);
            MenuDTO menu = new MenuDAO().getMenuByID(menu_id_value);

            request.setAttribute("feedbacks", feedbacks);
            request.setAttribute("menu", menu);

            return "menuFeedbacks.jsp";
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error retrieving feedbacks: " + e.getMessage());
            return "menuFeedbacks.jsp";
        }
    }

}

        
