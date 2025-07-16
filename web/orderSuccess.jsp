<%-- 
    Document   : orderSuccess
    Created on : Jul 12, 2025, 11:37:27 AM
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="model.dto.OrderDTO" %>
<%@ page import="java.util.List" %>
    <%
        OrderDTO order = (OrderDTO) request.getAttribute("order");
        List<OrderDTO> orderItems = (List<OrderDTO>) request.getAttribute("orderItems");
        Double totalPrice = (Double) request.getAttribute("totalPrice");
        String formattedTotal = (String) request.getAttribute("formattedTotal");
        String totalCalculationError = (String) request.getAttribute("totalCalculationError");
    %>
    <!DOCTYPE html>
    <html>
        <head>
            <meta charset="UTF-8">
            <title>Order Success Page</title>
        </head>
        <body>
            <h1>Order Successfully!</h1>
            
            <%
                if (order != null) {
            %>
            <h2>Order Details</h2>
            <table>
                <thead>
                    <tr>
                        <th>Menu_ID</th>
                        <th>Quantity</th>
                        <th>Order_time</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><%= order.getMenu_ID() %></td>
                        <td><%= order.getOrder_quantity() %></td>
                        <td><%= order.getOrder_time() %></td>
                    </tr>
                </tbody>
            </table>
            <!-- Total Price Section -->
            <div class="total-section">
                <h2>Order Total</h2>
                <%
                    if (totalPrice != null) {
                %>
                <div class="total-price">
                    Total Amount: <%= formattedTotal != null ? formattedTotal : String.format("%.2f", totalPrice) %>
                </div>
                <%
                    } else if (totalCalculationError != null) {
                %>
                <div style="color: orange;">
                    <p><%= totalCalculationError %></p>
                    <a href="TotalPriceController?action=sumMoney&type=order&id=<%= order.getOrder_ID() %>">Calculate Total Price</a>
                </div>
                <%
                    } else {
                %>
                <div id="totalPriceContainer">
                    <p>Loading total price...</p>
                    <script>
                        // Automatically load total price when page loads
                        window.onload = function() {
                            var orderId = <%= order.getOrder_ID() %>;
                            window.location.href = 'TotalPriceController?action=sumMoney&type=order&id=' + orderId;
                        };
                    </script>
                </div>
                <%
                    }
                %>
            </div>

            <%
                } else {
            %>
            <p>No order information available</p>
            <%
                }
            %>
            
            <div>
                <a href="MainController?action=goToFeedback&menu_ID=<%=order.getMenu_ID() %>">Give Feedback</a>
                <a href="cart.jsp">See your cart</a>
                <a href="MainController?action=searchMenu">Continue to order</a>
            </div>
            
        </body>
    </html>