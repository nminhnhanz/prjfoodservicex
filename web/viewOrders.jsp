<%@page import="java.util.List"%>
<%@page import="model.dto.OrderDTO"%>
<%@page import="model.dto.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    UserDTO user = (UserDTO) session.getAttribute("user");
        out.println("DEBUG: user = " + user);
if (user != null) {
        out.println(" | role = " + user.getRole());
    }
    String statusFilter = request.getParameter("status");

    List<OrderDTO> orders = (List<OrderDTO>) request.getAttribute("orders");
    
%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Order History</title>
    </head>
    <body>

        <h2>Order History</h2>
        <a href="MainController">Back</a>
        <!-- Filter by status -->
        <form method="get" action="MainController">
            <input type="hidden" name="action" value="viewOrders"/>
            <label for="status">Filter by status:</label>
            <select name="status" id="status" onchange="this.form.submit()">
                <option value="" <%= (statusFilter == null || statusFilter.equals("")) ? "selected" : "" %>>All</option>
                <option value="pending" <%= "pending".equals(statusFilter) ? "selected" : "" %>>Pending</option>
                <option value="completed" <%= "completed".equals(statusFilter) ? "selected" : "" %>>Completed</option>
                <option value="failed" <%= "failed".equals(statusFilter) ? "selected" : "" %>>Failed</option>
            </select>
        </form>

        <br>

        <table border="1" cellpadding="8" cellspacing="0">
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
                <th>Action</th> <!-- View Detail -->
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
                    <%-- Change Edit to Complete --%>
                    <a href="MainController?action=completeOrder&id=<%= order.getOrder_ID() %>">Complete</a>
                    <% if ("pending".equalsIgnoreCase(order.getStatus())) { %>
                    | <a href="MainController?action=cancelOrder&id=<%= order.getOrder_ID() %>">Cancel</a>
                    <% } %>
                </td>
                <% } %>
                <td>
                    <a href="MainController?action=viewOrderDetail&orderId=<%= order.getOrder_ID() %>">View Detail</a>
                </td>
            </tr>
            <%
                    }
                } else {
            %>
            <tr><td colspan="12">No orders found for selected status.</td></tr>
            <%
                }
            %>
        </table>

    </body>
</html>
