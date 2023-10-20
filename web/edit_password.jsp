<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="it">
<head>
    <title>Title</title>
</head>
<body>
<header>
    <%@include file="static/header.jsp"%>
</header>

<%
    List<String> errors = (List<String>) request.getAttribute("errors");
    if(errors != null) {
        for (String error : errors) { %>
<%=error%> <br>
<%
        } %>
    <br>
<%
    }
%>
<form class="login" method="post" action="EditPassword">
    <label for="oldpassword">Vecchia password: </label>
    <input type="password" name="oldpassword" id="oldpassword"> <br>

    <label for="newpassword">Nuova password: </label>
    <input type="password" name="newpassword" id="newpassword"> <br>

    <label for="newpasswordrepeat">Ripeti nuova password: </label>
    <input type="password" name="newpasswordrepeat" id="newpasswordrepeat"> <br>
    <input type="submit" value="Modifica password">
</form>

<footer>
    <%@include file="static/footer.jsp"%>
</footer>
</body>
</html>