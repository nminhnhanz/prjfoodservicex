<%-- 
    Document   : cart
    Created on : Jul 12, 2025, 8:32:55 PM
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.OrderDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Cart page</title>
        
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="cart.css">
    </head>
    <body>
        <!-- Include Header -->
        <%@ include file="Header.jsp" %>
        
        <div class="cart-container">
            <div class="container">
                <div class="row">
                    <div class="col-lg-8">
                        <h2 class="cart-title">Your cart</h2>
                        
                        <%
                            List<OrderDTO> cart = (List<OrderDTO>) session.getAttribute("cart");
                            if (cart == null || cart.isEmpty()) {
                        %>
                        <div class="empty-cart">
                            <p>Have no something in cart.</p>
                            <a href="MenuController?action=loadAllMenu" class="btn btn-primary">Continue to order</a>
                        </div>
                        <%
                            } else {
                                int index = 0;
                                for (OrderDTO item : cart) {
                        %>
                        <div class="cart-item">
                            <p><strong>Menu_ID:</strong> <%= item.getMenu_ID() %></p>
                            <p><strong>Quantity:</strong> <%= item.getOrder_quantity() %></p>
                            <p><strong>Cart_price_time:</strong> <%= item.getOrder_price_time() %></p>
                            
                            <form action="MainController" method="post">
                                <input type="hidden" name="action" value="removeFromCart" />
                                <input type="hidden" name="index" value="<%= index %>" />
                                <input type="submit" value="Delete" class="delete-btn" />
                            </form>
                            <hr>
                        </div>
                        <%
                                    index++;
                                }
                            }
                        %>
                    </div>
                    
                    <!-- Cart Summary -->
                    <div class="col-lg-4">
                        <% if (cart != null && !cart.isEmpty()) { %>
                        <div class="cart-summary">
                            <%
                                Object cartIdObj = request.getAttribute("cartId");
                                if (cartIdObj == null) {
                                    cartIdObj = session.getAttribute("cartId");
                                }
                            %>
                            
                            <form action="TotalPriceController" method="get">
                                <input type="hidden" name="action" value="sumMoney" />
                                <input type="hidden" name="type" value="cart" />
                                <input type="hidden" name="id" value="<%= request.getAttribute("cartId") != null ? request.getAttribute("cartId") : session.getAttribute("cartId") %>" />
                                <input type="submit" value="Tính tổng tiền" class="calculate-btn" />
                            </form>
                                
                            <%
                                String formattedTotal = (String) request.getAttribute("formattedTotal");
                                if (formattedTotal == null) {
                            %>
                                <div class="price-notice">Nhấn để cập nhật giá tiền</div>
                            <%
                                } else {
                            %>
                                <div class="total-price">Tổng tiền: <%= formattedTotal %> VNĐ</div>
                            <%
                            }
                            %>
                            
                            <a href="MainController?action=searchMenu" class="continue-link">Continue to order</a>
                        </div>
                        <% } %>
                    </div>
                </div>
            </div>
        </div>
        
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>