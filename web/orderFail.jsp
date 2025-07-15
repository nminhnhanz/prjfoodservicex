<%-- 
    Document   : orderFail
    Created on : Jul 12, 2025, 10:20:16 AM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Order Fail Page</title>
    </head>
    <body>

        <h2>Order Fail</h2>

            <% 
                String error = (String) request.getAttribute("error");
                if (error != null) {
                    out.println(error);
                } else {
                    out.println("Unknow error detected");
                }
            %>
       
        
        <a href="welcome.jsp">Return to page</a>
      
    </body>
</html>
