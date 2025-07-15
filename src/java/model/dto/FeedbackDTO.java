/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dto;

/**
 *
 * @author Admin
 */
public class FeedbackDTO {
    private int feedback_ID;
    private int rating;
    private String comment;
    private int user_ID;
    private int menu_ID;
    private MenuDTO menu;
    private String user_name;


    public FeedbackDTO() {
    }

    public FeedbackDTO(int feedback_ID, int rating, String comment, int user_ID, int menu_ID) {
        this.feedback_ID = feedback_ID;
        this.rating = rating;
        this.comment = comment;
        this.user_ID = user_ID;
        this.menu_ID = menu_ID;
    }

    public int getFeedback_ID() {
        return feedback_ID;
    }

    public void setFeedback_ID(int feedback_ID) {
        this.feedback_ID = feedback_ID;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getUser_ID() {
        return user_ID;
    }

    public void setUser_ID(int user_ID) {
        this.user_ID = user_ID;
    }

    public int getMenu_ID() {
        return menu_ID;
    }

    public void setMenu_ID(int menu_ID) {
        this.menu_ID = menu_ID;
    }
    
    public MenuDTO getMenu() {
        return menu;
    }

    public void setMenu(MenuDTO menu) {
        this.menu = menu;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    
    
}
