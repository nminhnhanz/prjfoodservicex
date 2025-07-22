<%-- 
    Document   : register
    Created on : Jul 9, 2025, 11:42:24 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register Page</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f2f5;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            form {
                background-color: #fff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                width: 400px;
                text-align: center;
            }

            input[type="text"],
            input[type="password"],
            select {
                width: 90%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            input[type="submit"] {
                background-color: #007bff;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
                margin-top: 10px;
            }

            input[type="submit"]:hover {
                background-color: #0056b3;
            }

            a {
                display: inline-block;
                margin-top: 10px;
                color: #007bff;
                text-decoration: none;
            }

            a:hover {
                text-decoration: underline;
            }

            h1 {
                margin-bottom: 20px;
                color: #333;
            }

            span {
                display: block;
                margin-bottom: 10px;
                font-weight: bold;
            }

            span[style*="green"] {
                color: green;
            }

            span[style*="red"] {
                color: red;
            }
        </style>
    </head>
    <body>
        <%
            // Kiểm tra nếu đã login và là Admin/Manager thì chuyển hướng
            if(AuthUtils.isLoggedIn(request) && 
               (AuthUtils.isAdmin(request) || AuthUtils.isManager(request))){
                response.sendRedirect("welcome.jsp");
                return;
            }

            String success = (String) request.getAttribute("register_success");
            String error = (String) request.getAttribute("register_error");
        %>

        <form action="MainController" method="post">
            <h1>CUSTOMER REGISTER</h1>

            <% if(success != null) { %>
                <span style="color: green"><%=success%></span>
            <% } else if(error != null) { %>
                <span style="color: red"><%=error%></span>
            <% } %>

            <input type="hidden" name="action" value="register" />
            <input type="hidden" name="role" value="Customer" />

            <input type="text" name="user_name" placeholder="Username" required> <br>
            <input type="text" name="email" placeholder="Email" required> <br>
            <input type="password" name="password" placeholder="Password" required> <br>
            <input type="text" name="fullName" placeholder="Full Name" required> <br>
            <input type="text" name="phone" placeholder="Phone"> <br>
            <input type="text" name="address" placeholder="Address"> <br>

            <select name="gender">
                <option value="Male">Male</option>
                <option value="Female">Female</option>
            </select> <br>

            <input type="submit" value="REGISTER">
            <a href="login.jsp">Back to Login</a>
        </form>
    </body>
</html>
