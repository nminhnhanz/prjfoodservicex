<%-- 
    Document   : Header
    Created on : Jul 13, 2025
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="model.dto.UserDTO" %>
<%@page import="model.dto.CategoryDTO" %>
<%@page import="java.util.List" %>
<%@page import="utils.AuthUtils" %> 
<!-- Google Web Fonts -->
<meta charset="utf-8">
<title>Ngo Quyen Food</title>
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
<!-- Navbar start -->
<div class="container-fluid fixed-top">
    <div class="container topbar bg-primary d-none d-lg-block">
        <div class="d-flex justify-content-between">
            <div class="top-info ps-2">
                <small class="me-3"><i class="fas fa-map-marker-alt me-2 text-secondary"></i> <a href="#" class="text-white">123 Le Van Viet, Thu Duc</a></small>
                <small class="me-3"><i class="fas fa-envelope me-2 text-secondary"></i><a href="#" class="text-white">ngoquenfood@helpmepls.com</a></small>
            </div>

        </div>
    </div>
    <div class="container px-0">
        <nav class="navbar navbar-light bg-white navbar-expand-xl">
            <a href="index.html" class="navbar-brand"><h1 class="text-primary display-6">Ngo Quyen Food</h1></a>
            <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse" data-bs-target="#navbarCollapse">
                <span class="fa fa-bars text-primary"></span>
            </button>
            <div class="collapse navbar-collapse bg-white" id="navbarCollapse">
                <div class="navbar-nav mx-auto">
                    <a href="MainController" class="nav-item nav-link active">Home</a>
                    <a href="MenuController?action=searchMenu" class="nav-item nav-link">Shop</a>

                    <a href="contact.html" class="nav-item nav-link">Contact</a>
                </div>
                <div class="d-flex m-3 me-0">
                    <a href="MainController?action=getCart" class="position-relative me-4 my-auto">
                        <i class="fa fa-shopping-bag fa-2x"></i>
                    </a>
                    <%if (!AuthUtils.isLoggedIn(request)){%>
                    <a href="login.jsp" class="my-auto">
                        Login
                    </a>
                    /
                    <a href="register.jsp" class="my-auto">
                        Register
                    </a>
                    <%} else {%>
                        Welcome, <%=AuthUtils.getCurrentUser(request).getUser_fullName()%>.
                        
                        <a href="MainController?action=logout">Logout</a>.
                        <a href="MainController?action=viewOrders">View Order</a>
                    <%}%>
                </div>
            </div>
        </nav>
    </div>
</div>
<!-- Navbar End -->
