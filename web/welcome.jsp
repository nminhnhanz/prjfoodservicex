<%-- 
    Document   : welcome
    Created on : Jul 9, 2025, 9:40:18 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.UserDTO" %>
<%@page import="model.MenuDTO" %>
<%@page import="java.util.List" %>
<%@page import="utils.AuthUtils" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Welcome Page</title>
    </head>
    <body>
        <%
            if(AuthUtils.isLoggedIn(request)){
                UserDTO user = AuthUtils.getCurrentUser(request);
                String keyword = (String) request.getAttribute("keyword");
            
        %>
        
        <h1>Welcome <%=user.getUser_fullName()%> to our broken rice page</h1>
        <a href="MainController?action=logout">Logout</a>
        
        <form action="MainController" method="post">
            <input type="hidden" name="action" value="searchMenu"/>
            <label>Search Menu:</label>
            <input type="text" name="keyword" value="<%=keyword!=null?keyword:""%>" placeholder="Enter menu name..."/>
            <input type="submit" value="Search"/>
        </form>
            <% if(AuthUtils.isAdmin(request)){ %>
                <a href="menuForm.jsp">Add Menu</a>
                <% } %>
                
            <%
                List<MenuDTO> list = (List<MenuDTO>)request.getAttribute("list");
                if(list!=null && list.isEmpty()){
            %>

            <h3>No Menus found</h3>
            <p>No Menus have name matching with the keyword.</p>
            
            <%
                }else if(list!=null && !list.isEmpty()){
            %>
        
            <table>
                <thead>
                    <tr>
                        <th>Menu_ID</th>
                        <th>Food</th>
                        <th>Image</th>
                        <th>Price</th>
                        <th>Food_Description</th>
                        <th>Food_Status</th>
                        <th>Category_ID</th>
                        
                            <%if(AuthUtils.isAdmin(request)){%>
                        <th>Action</th>
                            <%}%>
                         
                    </tr>
                </thead>
                <tbody>
                    <% for(MenuDTO m : list) { %>
                    <tr>
                        <td data-label="Category_ID"><%=m.getMenu_id()%></td>
                        <td data-label="Name"><%=m.getFood()%></td>
                        <td data-label="Image"><%=m.getImage()%></td>
                        <td data-label="Price"><%=m.getPrice()%></td>
                        <td data-label="Food_description"><%=m.getFood_description()%></td>
                        <td data-label="Food_status"><%=m.getFood_status()%></td>
                        <td data-label="Category_ID"><%=m.getCategory_id()%></td>
                        
                         <% if(AuthUtils.isAdmin(request)){ %>
                            <td data-label="Action">
                                <form action="MainController" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="editMenu"/>
                                    <input type="hidden" name="menuId" value="<%=m.getMenu_id()%>"/>
                                    <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                                    <input type="submit" value="Edit"0 />
                                </form>
                                <form action="MainController" method="post" style="display: inline;">
                                    <input type="hidden" name="action" value="deleteMenu"/>
                                    <input type="hidden" name="menuId" value="<%=m.getMenu_id()%>"/>
                                    <input type="hidden" name="keyword" value="<%=keyword!=null?keyword:""%>" />
                                    <input type="submit" value="Delete" 
                                           onclick="return confirm('Are you sure you want to delete this menu?')"/>
                                </form>
                                </div>
                            </td>
                            <% } %>
                    </tr>
                    <%}%>
                </tbody>
                <%}%>
            </table>
            
        <%}%>
    </body>
</html>
