<%-- 
    Document   : order
    Created on : Jul 12, 2025, 9:35:16 AM
    Author     : Admin
--%>
<%@ page import="model.MenuDTO" %>
    
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Page</title>
    </head>
        <body>
            <%
                MenuDTO menu = (MenuDTO) request.getAttribute("menu");
                if (menu == null) {
                    response.sendRedirect("MainController?action=searchMenu");
                    return;
                }
            %>
            
            <h1>Order Information</h1>
            <a href="welcome.jsp">Return to page</a>
            <% if (menu != null) { %>
            <table border="1" cellpadding="8" cellspacing="0">
                <tr>
                    <th>Food</th>
                    <td><%= menu.getFood() %></td>
                </tr>
                <tr>
                    <th>Price</th>
                    <td><%= menu.getPrice() %></td>
                </tr>
                <tr>
                    <th>Description</th>
                    <td><%= menu.getFood_description() %></td>
                </tr>
            </table> <br>
            
        <h2>Order food: <%= menu.getFood() %></h2>
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="createOrder" />
            <input type="hidden" name="menu_ID" value="<%= menu.getMenu_id() %>" />

            <label>Quantity:</label>
            <input type="number" name="order_quantity" value="1" min="1" required /><br>

            <label>Date of order:</label>
            <%
            java.time.LocalDate today = java.time.LocalDate.now();
            %>
            <input type="date" name="order_price_time" 
            value="<%= today %>" 
            min="<%= today %>" required /><br>

            <input type="submit" value="Order" />
        </form>
    <%}%>
        
    </body>
</html