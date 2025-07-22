<%-- 
    Document   : menuFeedbacks
    Created on : Jul 13, 2025, 9:06:00 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="model.dto.FeedbackDTO"%>
<%@page import="model.dto.MenuDTO"%>

<html>
    <head>
        <title>Menu Feedbacks</title>
    <body>
        <%
            List<FeedbackDTO> feedbacks = (List<FeedbackDTO>) request.getAttribute("feedbacks");
            MenuDTO menu = (MenuDTO) request.getAttribute("menu");
            
        %>

        <h1>Feedbacks for: <%= (menu != null) ? menu.getFood() : "Unknown Dish" %></h1>

        <% if (feedbacks == null || feedbacks.isEmpty()) { %>
        <p>No feedback yet for this menu.</p>
        <% } else { %>
        <ul>
            <% for (FeedbackDTO fb : feedbacks) { %>
            <li>User: <%=fb.getUser_name()%> - Rating: <%= fb.getRating() %> - Comment: <%= fb.getComment() %></li>
                <% } %>
        </ul>
        <% } %>

        <br>
        <a href="MainController?action=searchMenu">🔙 Back to Menu</a>
    </body>
</head>
</html>

