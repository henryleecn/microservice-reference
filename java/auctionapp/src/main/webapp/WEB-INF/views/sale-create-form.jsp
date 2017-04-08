<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Create Sale</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<!-- css files included -->
<link rel="stylesheet" href="/static/css/bootstrap.css">
<link rel="stylesheet" type="text/css"
    href="/static/css/animate.min.css">
<link rel="stylesheet" href="/static/css/theme.css">
</head>
<body>
    <!-- Header  -->
    <%@include file="/WEB-INF/views/header.jsp"%>
    <!-- Container -->
    <div class="container" style="width:50%">
        <c:choose>
            <c:when test="${sale['new']}">
                <h2>Create Sale</h2>
            </c:when>
            <c:otherwise>
                <h2>Update Sale</h2>
            </c:otherwise>
        </c:choose>
        <br />

        <form:form class="form-horizontal" method="post" modelAttribute="sale"
            action="${action_uri}">

            <form:hidden path="id" />
            <form:hidden path="product" />

            <spring:bind path="startPrice">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Starting Price</label>
                    <div class="col-sm-10">
                        <form:input path="startPrice" class="form-control" id="startPrice"
                            placeholder="StartPrice" />
                        <form:errors path="startPrice" class="control-label" />
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="reservePrice">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Reserve Price</label>
                    <div class="col-sm-10">
                        <form:input path="reservePrice" class="form-control"
                            id="reservePrice" placeholder="ReservePrice" />
                        <form:errors path="reservePrice" class="control-label" />
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="startTime">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Start Time (mm/dd/yy
                        hh:mm:ss)</label>
                    <div class="col-sm-10">
                        <form:input path="startTime" type="text" class="form-control"
                            id="startTime" placeholder="StartTime" />
                        <form:errors path="startTime" class="control-label" />
                    </div>
                </div>
            </spring:bind>

            <spring:bind path="endTime">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">End Time (mm/dd/yy
                        hh:mm:ss)</label>
                    <div class="col-sm-10">
                        <form:input path="endTime" type="text" class="form-control"
                            id="endTime" placeholder="EndTime" />
                        <form:errors path="endTime" class="control-label" />
                    </div>
                </div>
            </spring:bind>


            <spring:bind path="description">
                <div class="form-group ${status.error ? 'has-error' : ''}">
                    <label class="col-sm-2 control-label">Description</label>
                    <div class="col-sm-10">
                        <form:textarea path="description" rows="5" cols="30"
                            class="form-control" id="description" placeholder="Description" />
                        <form:errors path="description" class="control-label" />
                    </div>
                </div>
            </spring:bind>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <c:choose>
                        <c:when test="${sale['new']}">
                            <button type="submit" class="btn-lg btn-primary pull-right">Create</button>
                        </c:when>
                        <c:otherwise>
                            <button type="submit" class="btn-lg btn-primary pull-right">Update
                            </button>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            <input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" />
        </form:form>
    </div>
</body>
<script src="/static/js/jquery-1.10.1.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
</html>
