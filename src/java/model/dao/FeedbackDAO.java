/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao;

/**
 *
 * @author Admin
 */
import model.dto.FeedbackDTO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DbUtils;



public class FeedbackDAO {
    private static final String GET_ALL_FEEDBACKS = "SELECT feedback_ID, rating, comment, user_ID, menu_ID FROM [Feedback]";
    private static final String GET_FEEDBACK_BY_ID = "SELECT feedback_ID, rating, comment, user_ID, menu_ID FROM [Feedback] WHERE feedback_ID = ?";
    private static final String CREATE_FEEDBACK = "INSERT INTO [Feedback] (rating, comment, user_ID, menu_ID) VALUES (?, ?, ?, ?)";
    private static final String UPDATE_FEEDBACK = "UPDATE [Feedback] SET rating = ?, comment = ?, user_ID = ?, menu_ID = ? WHERE feedback_ID = ?";
    private static final String DELETE_FEEDBACK = "DELETE FROM [Feedback] WHERE feedback_ID = ?";
    private static final String GET_FEEDBACK_BY_MENU_ID = "SELECT feedback_ID, rating, comment, user_ID, menu_ID FROM [Feedback] WHERE menu_ID = ?";


    public List<FeedbackDTO> getAllFeedbacks() {
        List<FeedbackDTO> feedbacks = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_ALL_FEEDBACKS);
            rs = ps.executeQuery();

            while (rs.next()) {
                FeedbackDTO feedback = new FeedbackDTO();
                feedback.setFeedback_ID(rs.getInt("feedback_ID"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setComment(rs.getString("comment"));
                feedback.setUser_ID(rs.getInt("user_ID"));
                feedback.setMenu_ID(rs.getInt("menu_ID"));
                feedbacks.add(feedback);
            }
        } catch (Exception e) {
            System.err.println("Error in getAllFeedbacks(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return feedbacks;
    }

    public FeedbackDTO getFeedbackByID(String id) {
        FeedbackDTO feedback = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(GET_FEEDBACK_BY_ID);
            ps.setString(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                feedback = new FeedbackDTO();
                feedback.setFeedback_ID(rs.getInt("feedback_ID"));
                feedback.setRating(rs.getInt("rating"));
                feedback.setComment(rs.getString("comment"));
                feedback.setUser_ID(rs.getInt("user_ID"));
                feedback.setMenu_ID(rs.getInt("menu_ID"));
            }
        } catch (Exception e) {
            System.err.println("Error in getFeedbackByID(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, rs);
        }

        return feedback;
    }

    public boolean create(FeedbackDTO feedback) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(CREATE_FEEDBACK);
            ps.setInt(1, feedback.getRating());
            ps.setString(2, feedback.getComment());
            ps.setInt(3, feedback.getUser_ID());
            ps.setInt(4, feedback.getMenu_ID());

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
        } catch (Exception e) {
            System.err.println("Error in create(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean update(FeedbackDTO feedback) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(UPDATE_FEEDBACK);
            ps.setInt(1, feedback.getRating());
            ps.setString(2, feedback.getComment());
            ps.setInt(3, feedback.getUser_ID());
            ps.setInt(4, feedback.getMenu_ID());
            ps.setInt(5, feedback.getFeedback_ID());

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
        } catch (Exception e) {
            System.err.println("Error in update(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean delete(String id) {
        boolean success = false;
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = DbUtils.getConnection();
            ps = conn.prepareStatement(DELETE_FEEDBACK);
            ps.setString(1, id);

            int rowsAffected = ps.executeUpdate();
            success = (rowsAffected > 0);
        } catch (Exception e) {
            System.err.println("Error in delete(): " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResources(conn, ps, null);
        }

        return success;
    }

    public boolean isFeedbackExists(String id) {
        return getFeedbackByID(id) != null;
    }

    
    public List<FeedbackDTO> getFeedbacksByUserID(int userId) {
        List<FeedbackDTO> feedbacks = new ArrayList<>();
        String sql = "SELECT f.Feedback_ID, f.Rating, f.Comment, f.User_ID, f.Menu_ID, u.User_name "
                + "FROM [Feedback] f JOIN [User] u ON f.User_ID = u.User_ID WHERE f.User_ID = ?";

        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FeedbackDTO fb = new FeedbackDTO();
                fb.setFeedback_ID(rs.getInt("feedback_ID"));
                fb.setRating(rs.getInt("rating"));
                fb.setComment(rs.getString("comment"));
                fb.setUser_ID(rs.getInt("user_ID"));
                fb.setMenu_ID(rs.getInt("menu_ID"));
                fb.setUser_name(rs.getString("User_name")); 
                
                feedbacks.add(fb);
            }
        } catch (Exception e) {
            System.err.println("Error in getFeedbacksByUserID: " + e.getMessage());
            e.printStackTrace();
        }

        return feedbacks;
    }
    
    public List<FeedbackDTO> getFeedbacksByMenuID(int menuID) {
        List<FeedbackDTO> list = new ArrayList<>();
        String sql = "SELECT f.Feedback_ID, f.Rating, f.Comment, f.User_ID, f.Menu_ID, u.User_name "
                + "FROM [Feedback] f JOIN [User] u ON f.User_ID = u.User_ID WHERE f.Menu_ID = ?";
        try ( Connection conn = DbUtils.getConnection();  PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, menuID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                FeedbackDTO fb = new FeedbackDTO();
                fb.setFeedback_ID(rs.getInt("Feedback_ID"));
                fb.setRating(rs.getInt("Rating"));
                fb.setComment(rs.getString("Comment"));
                fb.setUser_ID(rs.getInt("User_ID"));
                fb.setMenu_ID(rs.getInt("Menu_ID"));
                fb.setUser_name(rs.getString("User_name")); 
                list.add(fb);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
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

}

    

