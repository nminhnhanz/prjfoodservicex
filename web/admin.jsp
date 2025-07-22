<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.dto.UserDTO" %>
<%@page import="model.dto.MenuDTO" %>
<%@page import="model.dto.CategoryDTO" %>
<%@page import="java.util.List" %>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>Admin Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

    </head>
    <body>

        <!-- Bootstrap CSS PHẢI ĐẶT TRƯỚC -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <!-- Custom CSS cho navbar -->
        <style>
            .navbar-custom {
                background-color: #2E1B15 !important; /* Màu nâu đậm */
                padding: 1rem 0 !important; /* Tăng padding để làm to navbar */
                box-shadow: 0 2px 4px rgba(0,0,0,0.1); /* Thêm shadow cho đẹp */
            }

            .navbar-custom .navbar-brand {
                font-size: 1.5rem !important; /* Làm to chữ brand */
                font-weight: bold;
                color: #fff !important;
            }

            .navbar-custom .navbar-nav .nav-link {
                color: #fff !important;
                font-size: 1.1rem; /* Làm to chữ nav-link */
            }

            .navbar-custom .navbar-nav .nav-link:hover {
                color: #BCAAA4 !important; /* Màu nâu nhạt khi hover */
            }

            .navbar-custom .form-control,
            .navbar-custom .form-select {
                border: 1px solid #8D6E63; /* Border màu nâu nhạt hơn */
            }

            .navbar-custom .btn-primary {
                background-color: #8D6E63;
                border-color: #8D6E63;
            }

            .navbar-custom .btn-primary:hover {
                background-color: #6D4C41;
                border-color: #6D4C41;
            }

            .navbar-custom .btn-secondary {
                background-color: #8D6E63;
                border-color: #8D6E63;
            }

            .navbar-custom .btn-secondary:hover {
                background-color: #6D4C41;
                border-color: #6D4C41;
            }

            .navbar-custom .btn-success {
                background-color: #689F38;
                border-color: #689F38;
            }

            .navbar-custom .btn-outline-light {
                color: #fff;
                border-color: #fff;
                padding: 0.375rem 0.75rem;
            }

            .navbar-custom .btn-outline-light:hover {
                background-color: #fff;
                color: #2E1B15;
            }

            .navbar-custom .nav-link {
                padding: 0.375rem 0.75rem;
                border-radius: 0.375rem;
                transition: all 0.3s ease;
            }

            .navbar-custom .nav-link:hover {
                background-color: rgba(255, 255, 255, 0.1);
            }
        </style>

        <nav class="navbar navbar-expand-lg navbar-dark navbar-custom">
            <div class="container">
                <!-- Brand/Logo -->
                <a class="navbar-brand" href="MenuController?action=loadAllMenu">Admin panel</a>

                <!-- Navbar toggler for mobile -->
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                    <span class="navbar-toggler-icon"></span>
                </button>

                <!-- Navbar content -->
                <div class="collapse navbar-collapse" id="navbarNav">
                    <!-- Search and Filter Section - CENTER -->
                    <% if(AuthUtils.isLoggedIn(request)){ %>
                    <div class="mx-auto">
                        <div class="d-flex align-items-center flex-wrap justify-content-center">




                            </form>

                            <!-- 3. SHOW ALL MENU -->
                            <div class="me-3 mb-2 mb-lg-0">
                                <a href="MenuController?action=loadAllMenu" class="btn btn-secondary">
                                    <i class="fas fa-list"></i> Show All
                                </a>
                            </div>

                            <!-- Add Menu Button (Admin only) -->
                            <% if(AuthUtils.isAdmin(request)){ %>
                            <div class="me-3 mb-2 mb-lg-0">
                                <a href="menuForm.jsp" class="btn btn-success">
                                    <i class="fas fa-plus"></i> Add
                                </a>
                            </div>
                            
                            <div class="me-3 mb-2 mb-lg-0">
                                <a href="MainController?action=viewOrders" class="btn btn-success">
                                    <p>view order</p>
                                </a>
                            </div>
                            <% } %>
                        </div>
                    </div>
                    <% } %>

                    <!-- Right side items -->
                    <div class="navbar-nav ms-auto">
                        <% if(AuthUtils.isLoggedIn(request)){ %>
                        <!-- 4. SEE YOUR CART -->

                        <a class="nav-link d-flex align-items-center" href="MainController?action=logout">
                            <i class="fas fa-sign-out-alt me-1"></i> Logout
                        </a>
                        <% } else { %>
                        <a class="nav-link d-flex align-items-center" href="login.jsp">
                            <i class="fas fa-sign-in-alt me-1"></i> Login
                        </a>
                        <% } %>
                    </div>
                </div>
            </div>
        </nav>

        <!-- ================ WELCOME SECTION ================ -->
        <% if(AuthUtils.isLoggedIn(request)){ %>
        <%
            UserDTO user = AuthUtils.getCurrentUser(request);
        %>
        <div class="welcome-section <%= AuthUtils.isAdmin(request) ? "admin-welcome" : AuthUtils.isManager(request) ? "manager-welcome" : "customer-welcome" %>">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-8">
                        <div class="welcome-content">
                            <%
                                if(AuthUtils.isAdmin(request)) {
                            %>
                            <%
                                } else if(AuthUtils.isManager(request)) {
                            %>
                            <h2 class="mb-2">
                                Chào mừng Manager!
                            </h2>
                            <p class="mb-0 fs-5">
                                Xin chào <strong><%=user.getUser_fullName()%></strong>
                            </p>
                            <%
                                } else if(AuthUtils.isCustomer(request)) {
                            %>
                            <h2 class="mb-2">
                                Chào mừng đến với Food Menu!
                            </h2>
                            <p class="mb-0 fs-5">
                                Kính chào quý khách <strong><%=user.getUser_fullName()%></strong>
                            </p>
                            <%
                                }
                            %>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% } else { %>
        <!-- Welcome section for guests -->
        <div class="welcome-section guest-welcome">
            <div class="container">
                <div class="row align-items-center">
                    <div class="col-md-8">
                        <div class="welcome-content">
                            <h2 class="mb-2"> 
                                Chào mừng đến với Food Menu!
                            </h2>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <% } %>

        <!-- Messages Section -->
        <div class="container mt-3">
            <%
                String message = (String) request.getAttribute("message");
                String error = (String) request.getAttribute("error");
                if(message != null){
            %>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <strong><%=message%></strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <%
                }
                if(error != null){
            %>
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <strong><%=error%></strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <%
                }
            %>
        </div>

        <!-- Font Awesome for icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        <!-- WELCOME SECTION -->
        <div class="welcome-section <%= AuthUtils.isAdmin(request) ? "admin-welcome" : AuthUtils.isManager(request) ? "manager-welcome" : "customer-welcome" %>">
            <div class="container py-4">
                <% if(AuthUtils.isLoggedIn(request)) {
                UserDTO user = AuthUtils.getCurrentUser(request); %>
                <h2>Chào mừng <%= AuthUtils.isAdmin(request) ? "Admin" : AuthUtils.isManager(request) ? "Manager" : "khách hàng" %>!</h2>
                <p>Xin chào <strong><%= user.getUser_fullName() %></strong></p>
                <% } else { %>
                <h2>Chào mừng đến với Food Menu!</h2>
                <% } %>
            </div>
        </div>

        <!-- ALERTS -->
        <div class="container mt-3">
            <% if(request.getAttribute("message") != null) { %>
            <div class="alert alert-success alert-dismissible fade show">
                <strong><%= request.getAttribute("message") %></strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% } %>
            <% if(request.getAttribute("error") != null) { %>
            <div class="alert alert-danger alert-dismissible fade show">
                <strong><%= request.getAttribute("error") %></strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
            <% } %>
        </div>

        <!-- MAIN CONTENT -->
        <% if(AuthUtils.isLoggedIn(request)) {
            UserDTO user = AuthUtils.getCurrentUser(request);
            List<MenuDTO> list = (List<MenuDTO>) request.getAttribute("list");
            List<CategoryDTO> categories = (List<CategoryDTO>) request.getAttribute("categories");
            String keyword = (String) request.getAttribute("keyword");
            String selectedCategoryId = (String) request.getAttribute("selectedCategoryId");

            if(list == null) {
                response.sendRedirect("MenuController?action=loadAllMenu");
                return;
            }
        %>
        <div class="container mt-4">
            <% if(list.isEmpty()) { %>
            <div class="alert alert-info">
                <h3>No Menus found</h3>
                <% if(keyword != null && !keyword.trim().isEmpty()) { %>
                <p>No Menus matching keyword: <strong><%= keyword %></strong></p>
                <% } %>
                <% if(selectedCategoryId != null && !selectedCategoryId.equals("0")) {
                    String categoryName = "";
                    for(CategoryDTO c : categories) {
                        if(String.valueOf(c.getCategory_ID()).equals(selectedCategoryId)) {
                            categoryName = c.getCategory_name();
                            break;
                        }
                    }
                %>
                <p>No Menus found in category: <strong><%= categoryName %></strong></p>
                <% } %>
                <a href="MenuController?action=loadAllMenu" class="btn btn-primary">View All Menu</a>
            </div>
            <% } else { %>
            <div class="card">
                <div class="card-header">
                    <h4>Menu List</h4>
                </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead class="table-dark">
                                <tr>
                                    <th>Food</th>
                                    <th>Image</th>
                                    <th>Price</th>
                                    <th>Description</th>
                                    <th>Status</th>
                                    <% if(AuthUtils.isAdmin(request)) { %><th>Admin</th><% } %>
                                </tr>
                            </thead>
                            <tbody>
                                <% for(MenuDTO m : list) { %>
                                <tr>
                                    <td><%= m.getFood() %></td>
                                    <td><img src="<%= m.getImage() %>" width="100" height="100"></td>
                                    <td><%= m.getPrice() %></td>
                                    <td><%= m.getFood_description() %></td>
                                    <td><span class="badge bg-<%= m.getFood_status().equals("Active") ? "success" : "danger" %>"><%= m.getFood_status() %></span></td>

                                    <% if(AuthUtils.isAdmin(request)) { %>
                                    <td>
                                        <form method="post" action="MenuController" style="display:inline;">
                                            <input type="hidden" name="action" value="editMenu">
                                            <input type="hidden" name="menuId" value="<%= m.getMenu_id() %>">
                                            <button class="btn btn-sm btn-warning"><i class="fas fa-edit"></i></button>
                                        </form>
                                        <form method="post" action="MenuController" style="display:inline;">
                                            <input type="hidden" name="action" value="deleteMenu">
                                            <input type="hidden" name="menuId" value="<%= m.getMenu_id() %>">
                                            <button class="btn btn-sm btn-danger" onclick="return confirm('Delete <%= m.getFood() %>?')">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                        </form>
                                    </td>
                                    <% } %>
                                </tr>
                                <% } %>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <% } %>
        </div>
        <% } else { %>
        <div class="container mt-5">
            <div class="alert alert-warning text-center">
                <h4>Please log in to view the menu.</h4>
                <a href="login.jsp" class="btn btn-primary">Login</a>
            </div>
        </div>
        <% } %>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>
