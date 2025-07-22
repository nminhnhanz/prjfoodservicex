<%@page import="java.util.List"%>
<%@page import="model.dto.OrderDTO"%>
<%@page import="model.dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    UserDTO user = (UserDTO) session.getAttribute("user");

    String statusFilter = request.getParameter("status");
    String searchOrderId = request.getParameter("searchOrderID");

    List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Order History</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                margin: 20px;
            }

            .container {
                max-width: 1100px;
                margin: auto;
            }

            h2 {
                text-align: center;
            }

            form {
                margin-bottom: 20px;
                text-align: center;
            }

            table {
                width: 100%;
                border-collapse: collapse;
                font-size: 14px;
                margin-bottom: 30px;
            }

            table, th, td {
                border: 1px solid #ccc;
            }

            th, td {
                padding: 8px;
                text-align: center;
            }

            th {
                background-color: #f2f2f2;
            }

            a.button, button {
                padding: 6px 10px;
                background-color: #007BFF;
                color: white;
                text-decoration: none;
                border-radius: 4px;
                border: none;
                cursor: pointer;
            }

            a.button:hover, button:hover {
                background-color: #0056b3;
            }

            .cancel-btn {
                background-color: #dc3545;
            }

            .cancel-btn:hover {
                background-color: #a71d2a;
            }
        </style>
    </head>
    <body>
        <%@include file="Header.jsp" %>
        <div class="container">
            <h2>Order History</h2>
            <a href="OrderController" class="button">Back</a>

            <!-- Filter by status -->
            <form method="get" action="OrderController">
                <input type="hidden" name="action" value="filter"/>
                <label for="status">Filter by status:</label>
                <select name="status" id="status" onchange="this.form.submit()">
                    <option value="" <%= (statusFilter == null || statusFilter.equals("")) ? "selected" : "" %>>All</option>
                    <option value="pending" <%= "pending".equals(statusFilter) ? "selected" : "" %>>Pending</option>
                    <option value="completed" <%= "completed".equals(statusFilter) ? "selected" : "" %>>Completed</option>
                    <option value="failed" <%= "failed".equals(statusFilter) ? "selected" : "" %>>Failed</option>
                </select>
            </form>

            <!-- Search by Order ID (manager only) -->
            <% if (user != null && "manager".equalsIgnoreCase(user.getRole())) { %>
            <form method="get" action="OrderController">
                <input type="hidden" name="action" value="search"/>
                <label for="searchOrderID">Search by Order ID:</label>
                <input type="text" id="searchOrderID" name="searchOrderID" placeholder="Enter Order ID"
                       value="<%= (searchOrderId != null) ? searchOrderId : "" %>" />
                <button type="submit">Search</button>
            </form>
            <% } %>

            <table>
                <tr>
                    <th>Order ID</th>
                    <th>Date</th>
                    <th>Payment</th>
                    <th>Status</th>
                    <th>Note</th>
                    <th>Address</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Phone</th>
                        <% if (user != null && "manager".equalsIgnoreCase(user.getRole())) { %>
                    <th>User ID</th>
                    <th>Manager Actions</th>
                        <% } %>
                    <th>Action</th>
                </tr>

                <%
                    if (orders != null && !orders.isEmpty()) {
                        for (OrderDTO order : orders) {
                %>
                <tr>
                    <td><%= order.getOrder_ID() %></td>
                    <td><%= order.getOrder_time() %></td>
                    <td><%= order.getPayment_method() %></td>
                    <td><%= order.getStatus() %></td>
                    <td><%= order.getNote() %></td>
                    <td><%= order.getAddress() %></td>
                    <td><%= order.getFullName() %></td>
                    <td><%= order.getEmail() %></td>
                    <td><%= order.getPhone() %></td>
                    <% if (user != null && "manager".equalsIgnoreCase(user.getRole())) { %>
                    <td><%= order.getUser_ID() %></td>
                    <td>
                        <% if ("pending".equalsIgnoreCase(order.getStatus())) { %>
                        <form method="post" action="OrderController" style="display: flex; flex-direction: column; gap: 6px; align-items: center;">
                            <input type="hidden" name="action" value="updateStatus"/>
                            <input type="hidden" name="id" value="<%= order.getOrder_ID() %>"/>
                            <input type="hidden" name="newStatus" value="completed"/>
                            <button type="submit" class="button">Complete</button>
                        </form>

                        <form method="post" action="OrderController" style="display: flex; flex-direction: column; gap: 6px; align-items: center; margin-top: 4px;">
                            <input type="hidden" name="action" value="updateStatus"/>
                            <input type="hidden" name="id" value="<%= order.getOrder_ID() %>"/>
                            <input type="hidden" name="newStatus" value="failed"/>
                            <button type="submit" class="button cancel-btn" style="background-color: #dc3545;">Cancel</button>
                        </form>
                        <% } else { %>
                        <span>Done</span>
                        <% } %>
                    </td>

                    <% } %>
                    <td>
                        <a href="OrderController?action=viewOrderDetail&orderId=<%= order.getOrder_ID() %>" class="button">Detail</a>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr><td colspan="12">No orders found for selected status or Order ID.</td></tr>
                <% } %>
            </table>
        </div>
        <%@include file="Footer.jsp"%>
    </body>
</html>
s