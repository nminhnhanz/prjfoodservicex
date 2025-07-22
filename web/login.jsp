<%-- 
    Document   : login
    Created on : Jul 9, 2025, 9:40:11 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.dto.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
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
                width: 300px;
                text-align: center;
            }

            input[type="text"],
            input[type="password"] {
                width: 90%;
                padding: 10px;
                margin: 10px 0;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            input[type="submit"] {
                background-color: #4CAF50;
                color: white;
                padding: 10px 20px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            input[type="submit"]:hover {
                background-color: #45a049;
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
                margin-top: 10px;
            }
        </style>
    </head>
    <body>
        <%
            if(AuthUtils.isLoggedIn(request)){
                response.sendRedirect("welcome.jsp");
            } else {
                Object objMS = request.getAttribute("message");
                String msg = (objMS == null) ? "" : (objMS + "");
        %>

        <form action="MainController" method="post">
            <h1>LOGIN</h1>
            <input type="hidden" name="action" value="login" />
            <input type="text" name="user_name" placeholder="Username" /> <br>
            <input type="password" name="password" placeholder="Password" /> <br>
            <input type="submit" value="LOGIN">
            <a href="register.jsp">REGISTER</a>
            <span style="color: red"><%=msg%></span>
        </form>

        <% } %>
    </body>
</html>
