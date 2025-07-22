<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.dto.CartDTO" %>
<%@ page import="model.dto.MenuDTO" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="utils.AuthUtils" %>
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
        <%
            List<CartDTO> cartList = (List<CartDTO>) session.getAttribute("cart");
            List<MenuDTO> menuList = (List<MenuDTO>) request.getAttribute("menuList"); 
        %>
        <div class="container-fluid page-header py-5">
            <h1 class="text-center text-white display-6">Checkout</h1>
            <ol class="breadcrumb justify-content-center mb-0">
                <li class="breadcrumb-item"><a href="#">Home</a></li>
                <li class="breadcrumb-item"><a href="#">Pages</a></li>
                <li class="breadcrumb-item active text-white">Checkout</li>
            </ol>
        </div>



        <!-- Checkout Page Start -->
        <div class="container-fluid py-5">
            <div class="container py-5">
                <h1 class="mb-4">Billing details</h1>
                <form action="MainController" method="post">
                    <input type="hidden" name="action" value="createOrder" >
                    <div class="row g-5">
                        <div class="col-md-12 col-lg-6 col-xl-7">
                            <div class="row">
                                <div class="col-md-12 col-lg-12">
                                    <div class="form-item w-100">
                                        <label class="form-label my-3">Name<sup>*</sup></label>
                                        <input type="text" name="order_fullName" class="form-control" value="<%=AuthUtils.getCurrentUser(request).getUser_fullName()%>">
                                    </div>
                                </div>

                            </div>

                            <div class="form-item">
                                <label class="form-label my-3">Address<sup>*</sup></label>
                                <input type="text" name="order_address" class="form-control" value="<%=AuthUtils.getCurrentUser(request).getAddress()%>">
                            </div>


                            <div class="form-item">
                                <label class="form-label my-3">Phone<sup>*</sup></label>
                                <input type="tel" name="order_phone" class="form-control" value="<%=AuthUtils.getCurrentUser(request).getPhone()%>">
                            </div>
                            <div class="form-item">
                                <label class="form-label my-3">Email Address<sup>*</sup></label>
                                <input type="email" name="order_email" class="form-control" value="<%=AuthUtils.getCurrentUser(request).getEmail()%>">
                            </div>



                            <div class="form-item">
                                <label class="form-label my-3">Note</label>

                                <textarea name="order_note" class="form-control" spellcheck="false" cols="30" rows="11" placeholder="Oreder Notes (Optional)"></textarea>
                            </div>
                        </div>
                        <div class="col-md-12 col-lg-6 col-xl-5">
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th scope="col">Products</th>
                                            <th scope="col">Name</th>
                                            <th scope="col">Price</th>
                                            <th scope="col">Quantity</th>
                                            <th scope="col">Total</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <%
                                MenuDTO mdto = null;    
                                if (cartList == null || cartList.isEmpty()) {
                                        %>

                                        <%
                                        } else {
                                            int index = 0;
                                            for (CartDTO item : cartList) {
                                                for (MenuDTO m : menuList){
                                                    if (m.getMenu_id() == item.getMenu_ID()){
                                                        mdto = m;
                                                        break;
                                                    };
                                                }
                                        
                                        %>
                                        <tr>
                                            <th scope="row">
                                                <div class="d-flex align-items-center mt-2">
                                                    <img src="<%=mdto.getImage()%>" class="img-fluid rounded-circle" style="width: 90px; height: 90px;" alt="">
                                                </div>
                                            </th>
                                            <td class="py-5"><%=mdto.getFood()%></td>
                                            <td class="py-5"><%=mdto.getPrice()%> VND</td>
                                            <td class="py-5"><%=item.getQuantity()%></td>
                                            <td class="py-5"><%=mdto.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))%>VND</td>
                                        </tr>
                                        <%} }%>


                                        <tr>
                                            <th scope="row">
                                            </th>
                                            <td class="py-5">
                                                <p class="mb-0 text-dark text-uppercase py-3">TOTAL</p>
                                            </td>
                                            <td class="py-5"></td>
                                            <td class="py-5"></td>
                                            <td class="py-5">
                                                <div class="py-3 border-bottom border-top">
                                                    <p class="mb-0 text-dark"><%=(BigDecimal)request.getSession().getAttribute("cartSum")%></p>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>

                            <div class="row g-4 text-center align-items-center justify-content-center border-bottom py-3">
                                <div class="col-12">
                                    <div class="form-check text-start my-3">
                                        <input type="radio" class="form-check-input bg-primary border-0" id="Payments-1" name="paymentMethod" value="vietqr">
                                        <label class="form-check-label" for="Payments-1">Chuyen khoan</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row g-4 text-center align-items-center justify-content-center border-bottom py-3">
                                <div class="col-12">
                                    <div class="form-check text-start my-3">
                                        <input type="radio" class="form-check-input bg-primary border-0" id="Delivery-1" name="paymentMethod" value="cod">
                                        <label class="form-check-label" for="Delivery-1">Cash On Delivery</label>
                                    </div>
                                </div>
                            </div>
                            <div class="row g-4 text-center align-items-center justify-content-center pt-4">
                                <button type="submit" class="btn border-secondary py-3 px-4 text-uppercase w-100 text-primary">Place Order</button>
                            </div>

                        </div>
                    </div>
                </form>
            </div>
        </div>
        <!-- Checkout Page End -->


        <%@include file="Footer.jsp" %>
    </body>

</html>