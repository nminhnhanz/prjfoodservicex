<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.dto.MenuDTO" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="model.dto.CategoryDTO" %>
<%@ page import="jakarta.servlet.http.HttpSession" %>
<%
    List<MenuDTO> menuList = (List<MenuDTO>) request.getAttribute("menuList");
    List<CategoryDTO> categoryList = (List<CategoryDTO>) request.getAttribute("categoryList");
    String keyword = (String) request.getAttribute("keyword");
    if (keyword == null) keyword = "";
%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Fruitables - Vegetable Website Template</title>
        <meta content="width=device-width, initial-scale=1.0" name="viewport">
        <link rel="preconnect" href="https://fonts.googleapis.com">
        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
        <link href="lib/lightbox/css/lightbox.min.css" rel="stylesheet">
        <link href="lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <link href="css/style.css" rel="stylesheet">
    </head>
    <body>
        <%@include file="Header.jsp"%>;
        <!-- Fruits Shop Start -->
        <div class="container-fluid fruite py-5">
            <div class="container py-5">
                <h1 class="mb-4">Fresh fruits shop</h1>
                <div class="row g-4">
                    <div class="col-lg-12">
                        <div class="row g-4">
                            <div class="col-xl-6">
                                <!-- Combined Search + Category Filter Form -->
                                <form action="MenuController" method="get" class="d-flex">
                                    <input type="text" name="keyword" class="form-control p-3 me-2" value="<%=keyword%>">
                                    <select name="categoryId" class="form-select me-2">
                                        <option value="0">All Categories</option>
                                        <% if (categoryList != null) {
                                            for (CategoryDTO cat : categoryList) { %>
                                        <option value="<%= cat.getCategory_ID() %>"><%= cat.getCategory_name() %></option>
                                        <% } } %>
                                    </select>
                                    <input type="hidden" name="action" value="searchMenu">
                                    <button type="submit" class="btn btn-primary px-4">
                                        <i class="fa fa-search"></i>
                                    </button>
                                </form>
                            </div>
                        </div>

                        <div class="row g-4 mt-4">
                            <div class="col-lg-3">
                                <!-- Sidebar (optional) -->
                            </div>

                            <div class="col-lg-9">
                                <div class="row g-4 justify-content-center">
                                    <% if (menuList != null && !menuList.isEmpty()) {
                                        for (MenuDTO product : menuList) { %>
                                    <div class="col-md-6 col-lg-6 col-xl-4">
                                        <div class="rounded position-relative fruite-item">
                                            <div class="fruite-img">
                                                <img src="<%= product.getImage() %>" class="img-fluid w-100 rounded-top" alt="">
                                            </div>
                                            <div class="text-white bg-secondary px-3 py-1 rounded position-absolute" style="top: 10px; left: 10px;">Fruits</div>
                                            <div class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                <h4><%= product.getFood() %></h4>
                                                <p><%= product.getFood_description() %></p>
                                                <div class="d-flex justify-content-between flex-lg-wrap">
                                                    <p class="text-dark fs-5 fw-bold mb-0"><%= product.getPrice() %> VND / unit</p>
                                                    <a href="CartController?action=addToCart&id=<%= product.getMenu_id() %>" 
                                                       class="btn border border-secondary rounded-pill px-3 text-primary">
                                                        <i class="fa fa-shopping-bag me-2 text-primary"></i> Add to cart
                                                    </a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <%  } 
                                    } else { %>
                                    <div class="col-12">
                                        <p class="text-center">Không có sản phẩm nào để hiển thị.</p>
                                    </div>
                                    <% } %>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Fruits Shop End -->
<%@include file="Footer.jsp"%>;
    </body>
</html>
