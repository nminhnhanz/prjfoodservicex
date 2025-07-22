<%-- 
    Document   : cart
    Created on : Jul 12, 2025, 8:32:55 PM
    Author     : Admin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.dto.CartDTO" %>
<%@ page import="model.dto.MenuDTO" %>
<%@ page import="java.math.BigDecimal" %>

<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="utf-8">
        <title>Foodwebapp</title>
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


        <!-- Modal Search Start -->
        <div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-fullscreen">
                <div class="modal-content rounded-0">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Search by keyword</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body d-flex align-items-center">
                        <div class="input-group w-75 mx-auto d-flex">
                            <input type="search" class="form-control p-3" placeholder="keywords" aria-describedby="search-icon-1">
                            <span id="search-icon-1" class="input-group-text p-3"><i class="fa fa-search"></i></span>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal Search End -->


        <!-- Single Page Header start -->
        <div class="container-fluid page-header py-5">
            <h1 class="text-center text-white display-6">Cart</h1>
            <ol class="breadcrumb justify-content-center mb-0">
                <li class="breadcrumb-item"><a href="#">Home</a></li>
                <li class="breadcrumb-item"><a href="#">Pages</a></li>
                <li class="breadcrumb-item active text-white">Cart</li>
            </ol>
        </div>
        <!-- Single Page Header End -->


        <!-- Cart Page Start -->
        <div class="container-fluid py-5">
            <div class="container py-5">
                <div class="table-responsive">

                    <form action="MainController?action=updateCart" method="post">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th scope="col">Products</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Total</th>
                                    <th scope="col">Handle</th>
                                </tr>
                            </thead>
                            <tbody>
                                <%
                                List<CartDTO> cart = (List<CartDTO>) request.getSession().getAttribute("cart");
                                List<MenuDTO> menuList = (List<MenuDTO>) request.getAttribute("menuList");
                                MenuDTO mdto = null;    
                                if (cart == null || cart.isEmpty()) {
                                %>
                                <%
                                } else {
                                    int index = 0;
                                    for (CartDTO item : cart) {
                                        for (MenuDTO m : menuList){
                                            if (m.getMenu_id() == item.getMenu_ID()){
                                                mdto = m;
                                                break;
                                            };
                                        }
                                %>
                                <tr>
                                    <th scope="row">
                                        <div class="d-flex align-items-center">
                                            <img src="<%=mdto.getImage()%>" class="img-fluid me-5 rounded-circle" style="width: 80px; height: 80px;" alt="">
                                        </div>
                                    </th>
                                    <td>
                                        <p class="mb-0 mt-4"><%=mdto.getFood()%></p>
                                    </td>
                                    <td>
                                        <p class="mb-0 mt-4"><%=mdto.getPrice()%> VND</p>
                                    </td>
                                    <td>
                                        <div class="input-group quantity mt-4" style="width: 100px;">
                                            <input name="quantity<%=item.getMenu_ID()%>" type="text" class="form-control form-control-sm text-center border-0" value="<%=item.getQuantity()%>">
                                        </div>
                                    </td>
                                    <td>
                                        <p class="mb-0 mt-4"><%=mdto.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))%>VND</p>
                                    </td>
                                    <td>
                                        <!-- Fixed: Use a link instead of nested form -->
                                        <a href="CartController?action=removeFromCart&menuId=<%=item.getMenu_ID()%>" 
                                           class="btn btn-danger btn-sm" 
                                           onclick="return confirm('Are you sure you want to remove this item?')">Delete</a>
                                    </td>
                                </tr>
                                <%
                                    index++;
                                    }
                                %>
                            </tbody>
                        </table>
                        <div class="mt-5">
                            <button class="btn border-secondary rounded-pill px-4 py-3 text-primary" type="submit">Update Cart</button>
                        </div>
                    </form>
                    <%}%>
                    <div class="row g-4 justify-content-end">
                        <div class="col-8"></div>
                        <div class="col-sm-8 col-md-7 col-lg-6 col-xl-4">
                            <div class="bg-light rounded">
                                <div class="p-4">
                                    <h1 class="display-6 mb-4">Cart <span class="fw-normal">Total</span></h1>
                                </div>
                                <div class="py-4 mb-4 border-top border-bottom d-flex justify-content-between">
                                    <h5 class="mb-0 ps-4 me-4">Total</h5>
                                    <p class="mb-0 pe-4"><%=(BigDecimal)request.getSession().getAttribute("cartSum")%></p>
                                </div>
                                <form action="MainController" method="post">
                                    <input type="hidden" name="action" value="checkOut">
                                    <button class="btn border-secondary rounded-pill px-4 py-3 text-primary text-uppercase mb-4 ms-4" type="submit">
                                        Proceed Checkout
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Cart Page End -->


            <%@include file="Footer.jsp" %>

    </body>

</html>
