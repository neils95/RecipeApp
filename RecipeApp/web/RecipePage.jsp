<%-- 
    Document   : recipePage
    Created on : Nov 28, 2015, 3:10:29 AM
    Author     : Neil
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
            String ingredient = request.getParameter("searchBar");
        %>
        <h1><%=ingredient%></h1>
    </body>
</html>
