<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:page pageTitle="Create Sale">
    <!-- Container -->
    <div id="form-box" class="animated bounceInUp">
        <form:form class="themeForm" name='themeForm' method="post"
            modelAttribute="sale" action="${action_uri}">
            <form:hidden path="id" />
            <header class="form-header">
                <c:choose>
                    <c:when test="${sale['new']}">
                        <h1>Create Sale</h1>
                    </c:when>
                    <c:otherwise>
                        <h1>Update Sale</h1>
                    </c:otherwise>
                </c:choose>
            </header>

            <spring:bind path="name">
                <label>Item Name :</label>
                <form:input path="name" id="name" placeholder="Item name" />
                <form:errors path="name" />
            </spring:bind>

            <spring:bind path="imageURL">
                <label>Item Image</label>
                <form:input path="imageURL" id="imageURL"
                    placeholder="Paste a URL to image" />
                <form:errors path="imageURL" />
            </spring:bind>

            <spring:bind path="thumbnail">
                <label>Item Thumbnail Image</label>
                <form:input path="thumbnail" id="thumbnail"
                    placeholder="Paste a URL to thumbnail image" />
                <form:errors path="thumbnail" />
            </spring:bind>

            <spring:bind path="currencyCode">
                <label>Price Currency</label>
                <form:select path="currencyCode" id="currencyCode">
                    <c:forEach var="currency" items="${currencies}">
                        <form:option value="${currency.key}">${currency.key==currency.value?"":currency.value} ${currency.key}</form:option>
                    </c:forEach>
                </form:select>
                <form:errors path="currencyCode" />
            </spring:bind>

            <spring:bind path="msrp">
                <label>Suggested Retail Price</label>
                <form:input path="msrp" id="msrp"
                    placeholder="Ongoing/Suggested retail price." />
                <form:errors path="msrp" />
            </spring:bind>

            <spring:bind path="startPrice">
                <label>Starting Price</label>
                <form:input path="startPrice" id="startPrice"
                    placeholder="StartPrice" />
                <form:errors path="startPrice" />
            </spring:bind>

            <spring:bind path="reservePrice">
                <label>Minimum Sale Price</label>
                <form:input path="reservePrice" id="reservePrice"
                    placeholder="ReservePrice" />
                <form:errors path="reservePrice" />
            </spring:bind>

            <spring:bind path="startTime">
                <label>Start Time</label>
                <form:input path="startTime" type="text" id="startTime"
                    placeholder="StartTime" />
                <form:errors path="startTime" />
            </spring:bind>

            <spring:bind path="endTime">
                <label>End Time</label>
                <form:input path="endTime" type="text" id="endTime"
                    placeholder="EndTime" />
                <form:errors path="endTime" />
            </spring:bind>

			<spring:bind path="city">
                <label>City</label>
                <form:select path="city" id="city">
                	<form:option value="Bengaluru">Bengaluru</form:option>
                	<form:option value="Gurgaon">Gurgaon</form:option>
                	<form:option value="Noida">Noida</form:option>
                </form:select>
                <form:errors path="city" />
            </spring:bind>
            
            <spring:bind path="description">
                <label>Description</label>
                <form:textarea path="description" rows="5" cols="30"
                    id="description" placeholder="Description" />
                <form:errors class="error" path="description" />
            </spring:bind>

            <c:choose>
                <c:when test="${sale['new']}">
                    <input type="submit" name="create" value="Create">
                </c:when>
                <c:otherwise>
                    <input type="submit" value="Update" name="update">
                </c:otherwise>
            </c:choose>

            <input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" />
        </form:form>
    </div>
</template:page>
