<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" session="false"%>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Help page</title>
</head>
<body>
    Hello ${user.firstName}!
    <br> All available URI at this time are:
    <table>
        <tr>
            <td>URI</td>
            <td>Handled by</td>
        </tr>
        <c:forEach var="entry" items="${mappings}">
            <tr>
                <td><a href="${entry.key}">${entry.key}</a></td>
                <td>${entry.value}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>