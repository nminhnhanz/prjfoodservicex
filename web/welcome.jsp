<%-- 
    Document   : welcome
    Created on : Jul 9, 2025, 9:40:18 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.dto.UserDTO" %>
<%@page import="model.dto.MenuDTO" %>
<%@page import="model.dto.CategoryDTO" %>
<%@page import="java.util.List" %>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        
    </head>
    <body>
        <!-- Include Header -->
        <%@include file="Header.jsp" %>
        
        <%
            if(AuthUtils.isLoggedIn(request)){
                UserDTO user = AuthUtils.getCurrentUser(request);
                String keyword = (String) request.getAttribute("keyword");
                String selectedCategoryId = (String) request.getAttribute("selectedCategoryId");
                
                // Nếu chưa có list trong request thì load tất cả menu
                List<MenuDTO> list = (List<MenuDTO>)request.getAttribute("list");
                List<CategoryDTO> categories = (List<CategoryDTO>)request.getAttribute("categories");
                if(list == null){
                    // Redirect để load tất cả menu
                    response.sendRedirect("MenuController?action=loadAllMenu");
                    return;
                }
        %>
        
        <!-- Main Content -->
        <div class="container mt-4">
            <%
                if(list != null && list.isEmpty()){
            %>
                <div class="alert alert-info">
                    <h3>No Menus found</h3>
                    <% if(keyword != null && !keyword.trim().isEmpty()){ %>
                        <p>No Menus have name matching with the keyword: "<strong><%=keyword%></strong>"</p>
                    <% } %>
                    <% if(selectedCategoryId != null && !selectedCategoryId.equals("0")){ %>
                        <%
                            // Tìm tên category để hiển thị
                            String categoryName = "";
                            if(categories != null){
                                for(CategoryDTO category : categories){
                                    if(String.valueOf(category.getCategory_ID()).equals(selectedCategoryId)){
                                        categoryName = category.getCategory_name();
                                        break;
                                    }
                                }
                            }
                        %>
                        <p>No Menus found in category: "<strong><%=categoryName%></strong>"</p>
                    <% } %>
                    <a href="MenuController?action=loadAllMenu" class="btn btn-primary">View All Menu</a>
                </div>
            <%
                } else if(list != null && !list.isEmpty()){
            %>
                <% 
                    String displayTitle = "All Menu";
                    if(keyword != null && !keyword.trim().isEmpty() && selectedCategoryId != null && !selectedCategoryId.equals("0")){
                        // Tìm tên category
                        String categoryName = "";
                        if(categories != null){
                            for(CategoryDTO category : categories){
                                if(String.valueOf(category.getCategory_ID()).equals(selectedCategoryId)){
                                    categoryName = category.getCategory_name();
                                    break;
                                }
                            }
                        }
                        displayTitle = "Search Results for \"" + keyword + "\" in category \"" + categoryName + "\"";
                    } else if(keyword != null && !keyword.trim().isEmpty()){
                        displayTitle = "Search Results for \"" + keyword + "\"";
                    } else if(selectedCategoryId != null && !selectedCategoryId.equals("0")){
                        // Tìm tên category
                        String categoryName = "";
                        if(categories != null){
                            for(CategoryDTO category : categories){
                                if(String.valueOf(category.getCategory_ID()).equals(selectedCategoryId)){
                                    categoryName = category.getCategory_name();
                                    break;
                                }
                            }
                        }
                    }
                %>
                
                <div class="row">
                    <div class="col-12">
                        <div class="card">
                            <div class="card-header">
                                <h4><%=displayTitle%></h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-striped table-hover">
                                        <thead class="table-dark">
                                            <tr>
                                                <th>Food</th>
                                                <th>Image</th>
                                                <th>Price</th>
                                                <th>Description</th>
                                                <th>Status</th>
                                                <th>Actions</th>
                                                <% if(AuthUtils.isAdmin(request)){ %>
                                                <th>Admin Actions</th>
                                                <% } %>
                                                <th>Feedback</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <% for(MenuDTO m : list) { %>
                                            <tr>
                                                <td><%=m.getFood()%></td>
                                                <td><img src="<%=m.getImage()%>" width="100" height="100""></td>
                                                <td><%=m.getPrice()%></td>
                                                <td><%=m.getFood_description()%></td>
                                                <td>
                                                    <span class="badge bg-<%=m.getFood_status().equals("Active") ? "success" : "danger"%>">
                                                        <%=m.getFood_status()%>
                                                    </span>
                                                </td>
                                                <td>   
                                                    <form action="MainController" method="post" style="display: inline;">
                                                        <input type="hidden" name="action" value="goToOrder" />
                                                        <input type="hidden" name="menu_ID" value="<%= m.getMenu_id()%>" />
                                                        <button type="submit" class="btn btn-primary btn-sm">
                                                            <i class="fas fa-shopping-cart"></i> Order
                                                        </button>
                                                    </form>
                                                </td>
                                                
                                                <% if(AuthUtils.isAdmin(request)){ %>
                                                <td>
                                                    <form action="MenuController" method="post" style="display: inline;">
                                                        <input type="hidden" name="action" value="editMenu"/>
                                                        <input type="hidden" name="menuId" value="<%=m.getMenu_id()%>"/>
                                                        <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                                                        <input type="hidden" name="categoryId" value="<%=selectedCategoryId!=null?selectedCategoryId:""%>" />
                                                        <button type="submit" class="btn btn-warning btn-sm">
                                                            <i class="fas fa-edit"></i> Edit
                                                        </button>
                                                    </form>
                                                    <form action="MenuController" method="post" style="display: inline; margin-left: 5px;">
                                                        <input type="hidden" name="action" value="deleteMenu"/>
                                                        <input type="hidden" name="menuId" value="<%=m.getMenu_id()%>"/>
                                                        <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                                                        <input type="hidden" name="categoryId" value="<%=selectedCategoryId!=null?selectedCategoryId:""%>" />
                                                        <button type="submit" class="btn btn-danger btn-sm"
                                                               onclick="return confirm('Are you sure you want to delete this menu: <%=m.getFood()%>?')">
                                                            <i class="fas fa-trash"></i> Delete
                                                        </button>
                                                    </form>                                 
                                                </td>
                                                <% } %>
                                                <td>
                                                    <form action="MainController" method="post" style="display:inline;">
                                                        <input type="hidden" name="action" value="viewFeedbackByMenu" />
                                                        <input type="hidden" name="menu_ID" value="<%= m.getMenu_id() %>" />
                                                        <input type="submit" value="View Feedback" />
                                                    </form>
                                                </td>
                                            </tr>
                                            <% } %>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>
            
        <% } else { %>
            <div class="container mt-5">
                <div class="row justify-content-center">
                    <div class="col-md-6">
                        <div class="card">
                            <div class="card-body text-center">
                                <h2>Please login to view menu</h2>
                                <a href="login.jsp" class="btn btn-primary">Login</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        <% } %>
        
        <!-- Include Footer -->
        <%@include file="Footer.jsp" %>
    </body>
</html>