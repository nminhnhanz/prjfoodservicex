<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.CONSTANT" %>
<%@page import="utils.PaymentAPI" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Fruitables - Vegetable Website Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <meta content="" name="keywords">
        <meta content="" name="description">

        <!-- Google Web Fonts -->
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet"> 

        <!-- Icon Font Stylesheet -->
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">

        <!-- Libraries Stylesheet -->
        <link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


        <!-- Customized Bootstrap Stylesheet -->
        <link href="css/bootstrap.min.css" rel="stylesheet">

        <!-- Template Stylesheet -->
        <link href="css/style.css" rel="stylesheet">
    </head>

    <body>
        <%@include file="Header.jsp" %>
        <%
            String bankCode = CONSTANT.RECEIVER_BANK_CODE;
            String cardNumber = CONSTANT.RECEIVER_CARD_NUMBER;
            String accountName = CONSTANT.RECEIVER_ACCOUNT_NAME;

            java.math.BigDecimal totalPrice = (java.math.BigDecimal) request.getAttribute("totalPrice");

            Integer orderID = (Integer) request.getAttribute("orderID");
            String paymentMethod = (String) request.getAttribute("paymentMethod");
        %>

        <div class="container mt-5 mb-5">
            <div class="card shadow rounded-4 p-4">
                <h2 class="text-success mb-3">Đặt hàng thành công!</h2>
                <p>Mã đơn hàng của bạn là: <strong>#<%= orderID != null ? orderID : "Không xác định" %></strong></p>
                <p>Tổng số tiền: <strong><%= totalPrice != null ? totalPrice.toPlainString() : "0" %> VND</strong></p>
                <p>Phương thức thanh toán: <strong><%= paymentMethod != null ? paymentMethod : "Không xác định" %></strong></p>

                <% if ("vietqr".equals(paymentMethod)) { %>
                <div class="alert alert-info mt-4">
                    <h5>Thông tin chuyển khoản:</h5>
                    <ul>
                        <li><strong>Ngân hàng:</strong> <%= bankCode %></li>
                        <li><strong>Số tài khoản:</strong> <%= cardNumber %></li>
                        <li><strong>Tên người nhận:</strong> <%= accountName %></li>
                        <li><strong>Nội dung chuyển khoản:</strong> Thanh toán đơn hàng #<%= orderID %></li>
                        <li><img width="500" height="500" src="<%=PaymentAPI.getVQR(bankCode,cardNumber,accountName,totalPrice,String.format(CONSTANT.PAYMENT_NOTE_FORMAT,orderID))%>"></li>
                    </ul>
                </div>
                <% } else { %>
                <div class="alert alert-warning mt-4">
                    <p>Bạn đã chọn thanh toán khi nhận hàng (COD).</p>
                    <p>Vui lòng chuẩn bị số tiền <strong><%= totalPrice != null ? totalPrice.toPlainString() : "0" %> VND</strong> khi nhận hàng.</p>
                </div>
                <% } %>

                <a href="MainController" class="btn btn-primary mt-3">Quay về trang chủ</a>
            </div>
        </div>

        <%@include file="Footer.jsp" %>
    </body>
</html>