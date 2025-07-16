<%-- 
    Document   : feedback
    Created on : Jul 12, 2025, 11:14:54 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.dto.MenuDTO" %>


<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Feedback page</title>
    </head>
    <body>
        <%
            MenuDTO menu = (MenuDTO) request.getAttribute("menu");
            String error = (String) request.getAttribute("error");
            String success = (String) request.getAttribute("success");
            String prevRating = (String) request.getAttribute("prev_rating");
            String prevComment = (String) request.getAttribute("prev_comment");

        %>

        <h1>Give Feedback for <%=(menu != null) ? menu.getFood() : "this item" %></h1>

        <% if (error != null) { %>
        <p style="color:red;"><%= error %></p>
        <% } %>

        <% if (success != null) { %>
        <p style="color:green;"><%= success %></p>
        <% } %>
        
        
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="submitFeedback" />
            <input type="hidden" name="menu_ID" value="<%=(menu != null) ? menu.getMenu_id() : "" %>" />

            <label>Rating (1-5):</label>
            <input type="number" name="rating" min="1" max="5"
                   value="<%= prevRating != null ? prevRating : "" %>" required /><br><br>

            <label>Comment:</label><br>
            <textarea name="comment" rows="4" cols="40" required><%= prevComment != null ? prevComment : "" %></textarea><br><br>

            <input type="submit" value="Submit Feedback" />
        </form>
        
        <br>
        <a href="MainController?action=searchMenu">🔙 Back to Menu</a>

    </body>
</html>

