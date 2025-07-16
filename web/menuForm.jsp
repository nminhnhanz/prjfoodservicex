<%-- 
    Document   : menuForm
    Created on : Jul 10, 2025, 10:03:27 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.dto.UserDTO" %>
<%@page import="utils.AuthUtils" %>
<%@page import="model.dto.MenuDTO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Menu Form</title>
        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
        <!-- Custom CSS -->
        <link rel="stylesheet" href="../assets/css/menuForm.css">
    </head>
    <body>
        <%@include file="Header.jsp" %>

        <%
                if (AuthUtils.isAdmin(request)) {
                    String checkError = (String)request.getAttribute("checkError");
                    String menuMessage = (String)request.getAttribute("message");
                    MenuDTO menu  = (MenuDTO)request.getAttribute("menu"); 
                    Boolean isEdit = (Boolean)request.getAttribute("isEdit") != null;
                    String keyword = (String)request.getAttribute("keyword");
                
        %>

        <div class="container-fluid">
            <div class="menu-form-container">
                <div class="menu-form-header">
                    <h1><%=isEdit ? "EDIT MENU" : "ADD MENU"%></h1>              
                </div>

                <div class="menu-form">
                    <form action="MenuController" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="action" value="<%=isEdit ? "updateMenu" : "addMenu"%>" />

                        <!-- Menu ID -->
                        <div class="form-group">
                            <label for="menu_id" class="form-label">Menu ID <span class="required">*</span></label>
                            <input type="text" id="menu_id" name="menuId" required 
                                   class="form-control" 
                                   value="<%=menu != null ? menu.getMenu_id():""%>" 
                                   <%= isEdit ? "readonly" : "" %> />
                        </div>

                        <!-- Food Name -->
                        <div class="form-group">
                            <label for="food" class="form-label">Food <span class="required">*</span></label>
                            <input type="text" id="food" name="food" required 
                                   class="form-control" 
                                   value="<%=menu != null ? menu.getFood() : "" %>" />
                        </div>

                  
                 
                        <!-- Price -->
                        <div class="form-group">
                            <label for="price" class="form-label">Price <span class="required">*</span></label>
                            <input type="text" id="price" name="price" required 
                                   class="form-control" 
                                   value="<%=menu != null ? menu.getPrice(): "" %>" />
                        </div>

                        <!-- Description -->
                        <div class="form-group">
                            <label for="description" class="form-label">Food Description</label>
                            <textarea id="description" name="food_description" 
                                      class="form-control form-textarea" 
                                      placeholder="Enter food description..."><%=menu != null ? menu.getFood_description() : "" %></textarea>
                        </div>

                        <!-- Food Status -->
                        <div class="form-group">
                            <label for="status" class="form-label">Status <span class="required">*</span></label>
                            <select id="status" name="food_status" required class="form-select">
                                <option value="">-- Select Status --</option>
                                <option value="Ready to serve" <%=menu != null && "Ready to serve".equals(menu.getFood_status()) ? "selected" : "" %>>Ready to serve</option>
                                <option value="Preparing" <%=menu != null && "Preparing".equals(menu.getFood_status()) ? "selected" : "" %>>Preparing</option>
                                <option value="Out of Stock" <%=menu != null && "Out of Stock".equals(menu.getFood_status()) ? "selected" : "" %>>Out of Stock</option>
                            </select>
                        </div>

                        <!-- Category ID -->
                        <div class="form-group">
                            <label for="category_id" class="form-label">Category ID <span class="required">*</span></label>
                            <input type="text" id="category_id" name="categoryId" required 
                                   class="form-control" 
                                   value="<%=menu != null ? menu.getCategory_id() : "" %>" />
                        </div>
                        <!-- Image -->
                        <div class="form-group">
                            <label for="image" class="form-label">Image</label>
                            <input type="file" id="image" name="FoodImage" class="form-control" />
                            <input type="hidden" name="currentImage" value="<%=menu != null ? menu.getImage() : "" %>" />
                        </div>

                        <div class="form-buttons"> 
                            <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                            <input type="submit" value="<%=isEdit ? "Update Menu" : "Add Menu"%>" class="btn btn-primary" />
                            <input type="reset" value="Reset" class="btn btn-secondary" />
                            <a href="welcome.jsp" class="back-link">← Back to Menus</a>
                        </div>             
                    </form>

                    <% if(checkError != null && !checkError.isEmpty()) { %>
                    <div class="alert-message alert-error"><%=checkError%></div>
                    <% } else if(menuMessage != null&& !menuMessage.isEmpty()) { %>
                    <div class="alert-message alert-success"><%=menuMessage%></div>
                    <% } %>
                </div>
            </div>
        </div>

        <%}else{%>
        <div class="container-fluid">
            <div class="access-denied-container">
                <div class="access-denied-title">ACCESS DENIED</div>
                <div class="access-denied-message">
                    <%=AuthUtils.getAccessDeniedMessage("Menu Form")%> 
                </div>
            </div>
        </div>
        <%}%>

        <%@include file="Footer.jsp" %>

        <!-- Bootstrap JS -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>
</html>