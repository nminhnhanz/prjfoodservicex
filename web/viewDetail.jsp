<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.dto.OrderDTO"%>
<%@page import="model.dto.MenuDTO"%>

<%@page import="model.dto.OrderItemDTO"%>
<%@page import="utils.CONSTANT" %>
<%@page import="utils.PaymentAPI" %>
<%@page import="java.util.List" %>
<%@page import="java.math.BigDecimal" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Order Details</title>
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="Header.jsp" %>

        <%
            OrderDTO order = (OrderDTO) request.getAttribute("order");
            List<OrderItemDTO> items = (List<OrderItemDTO>) request.getAttribute("items");
        List<MenuDTO> menuList = (List<MenuDTO>) request.getAttribute("menuList");
            BigDecimal totalPrice = (BigDecimal) request.getAttribute("totalPrice");

            String bankCode = CONSTANT.RECEIVER_BANK_CODE;
            String cardNumber = CONSTANT.RECEIVER_CARD_NUMBER;
            String accountName = CONSTANT.RECEIVER_ACCOUNT_NAME;
        %>

        <div class="container mt-5 mb-5">
            <div class="card shadow rounded-4 p-4">
                <h2 class="mb-3">Chi tiết đơn hàng</h2>
                <p><strong>Mã đơn hàng:</strong> #<%= order.getOrder_ID() %></p>
                <p><strong>Phương thức thanh toán:</strong> <%= order.getPayment_method() %></p>
                <p><strong>Trạng thái:</strong> <%= order.getStatus() %></p>

                <hr>
                <h4>Sản phẩm đã mua:</h4>
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th>Tên sản phẩm</th>
                            <th>Đơn giá</th>
                            <th>Số lượng</th>
                            <th>Thành tiền</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% 
                            if (items != null) {
                                for (OrderItemDTO item : items) { 
                        %>
                        <tr>
                            <td>
                                <%
                                    String productName = "Không xác định";
                                    MenuDTO mdto = null;
                                    if (menuList != null) {
                                        for (MenuDTO menu : menuList) {
                                            if (menu.getMenu_id() == item.getMenuID()) {
                                                mdto = menu;
                                                break;
                                            }
                                        }
                                    }
                                %>
                                <%= mdto.getFood() %>
                            </td>
                            <td><%= mdto.getPrice() %> VND</td>
                            <td><%= item.getQuantity() %></td>
                            <td><%=  mdto.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())) %> VND</td>
                        </tr>
                        <% 
                                } 
                            } else { 
                        %>
                        <tr><td colspan="4">Không có sản phẩm nào.</td></tr>
                        <% } %>
                    </tbody>

                </table>

                <p class="text-end fw-bold">Tổng tiền: <%= totalPrice.toPlainString() %> VND</p>

                <% if ("vietqr".equalsIgnoreCase(order.getPayment_method())) { %>
                <div class="alert alert-info mt-4">
                    <h5>Thông tin chuyển khoản:</h5>
                    <ul>
                        <li><strong>Ngân hàng:</strong> <%= bankCode %></li>
                        <li><strong>Số tài khoản:</strong> <%= cardNumber %></li>
                        <li><strong>Tên người nhận:</strong> <%= accountName %></li>
                        <li><strong>Nội dung chuyển khoản:</strong> Thanh toán đơn hàng #<%= order.getOrder_ID() %></li>
                        <li><img src="<%= PaymentAPI.getVQR(bankCode, cardNumber, accountName, totalPrice, String.format(CONSTANT.PAYMENT_NOTE_FORMAT, order.getOrder_ID())) %>" alt="Mã VietQR"></li>
                    </ul>
                </div>
                <% } else { %>
                <div class="alert alert-warning mt-4">
                    <p>Bạn đã chọn thanh toán khi nhận hàng (COD).</p>
                    <p>Vui lòng chuẩn bị <strong><%= totalPrice.toPlainString() %> VND</strong> khi nhận hàng.</p>
                </div>
                <% } %>

                <a href="MainController" class="btn btn-primary mt-3">Quay lại</a>
            </div>
        </div>

        <%@include file="Footer.jsp" %>
    </body>
</html>
