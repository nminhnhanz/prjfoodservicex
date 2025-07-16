<%-- 
    Document   : Footer
    Created on : Jul 13, 2025
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.dto.UserDTO" %>
<%@page import="utils.AuthUtils" %>

<style>
.footer-custom {
    background-color: #2E1B15 !important;
    color: #fff;
}

.footer-custom h5 {
    color: #D7CCC8;
    margin-bottom: 1rem;
}

.footer-custom p {
    margin-bottom: 0.5rem;
}

.footer-custom .social-links a {
    color: #D7CCC8;
    font-size: 1.2rem;
    transition: color 0.3s ease;
}

.footer-custom .social-links a:hover {
    color: #BCAAA4;
}

.footer-custom hr {
    border-color: #5D4037;
}

.footer-custom .text-muted {
    color: #A1887F !important;
}

.footer-custom i {
    color: #D7CCC8;
    margin-right: 0.5rem;
}
</style>

<footer class="footer-custom text-light mt-5">
    <div class="container py-4">
        <div class="row">
            <div class="col-md-6">
                <h5>Contact Information</h5>
                <div id="admin-contact">
                    <%
                        // Kiểm tra xem user hiện tại có phải admin không
                        if(AuthUtils.isLoggedIn(request) && AuthUtils.isAdmin(request)) {
                            UserDTO currentUser = AuthUtils.getCurrentUser(request);
                            // Nếu user hiện tại là admin, hiển thị thông tin của họ
                    %>
                        <p><i class="fas fa-envelope"></i> Email: <%=currentUser.getEmail()%></p>
                        <p><i class="fas fa-phone"></i> Phone: <%=currentUser.getPhone()%></p>
                    <%
                        } else {
                            // Hiển thị thông tin admin mặc định
                    %>
                        <p><i class="fas fa-envelope"></i> Email: admin@foodmenu.com</p>
                        <p><i class="fas fa-phone"></i> Phone: +84 123 456 789</p>
                        <p><small class="text-muted">Contact our administrator for support</small></p>
                    <%
                        }
                    %>
                </div>
            </div>
            
            <div class="col-md-6">
                <h5>About Food Menu System</h5>
                <p>Welcome to our food ordering system. We provide delicious meals with easy online ordering.</p>
                <div class="social-links">
                    <a href="#" class="text-light me-3"><i class="fab fa-facebook"></i></a>
                    <a href="#" class="text-light me-3"><i class="fab fa-twitter"></i></a>
                    <a href="#" class="text-light me-3"><i class="fab fa-instagram"></i></a>
                </div>
            </div>
        </div>
        
        <hr class="my-4">
        
        <div class="row">
            <div class="col-md-12 text-center">
                <p>&copy; 2025 Food Menu System. All rights reserved.</p>
            </div>
        </div>
    </div>
</footer>