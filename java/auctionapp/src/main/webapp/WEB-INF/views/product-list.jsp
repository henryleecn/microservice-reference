<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:page pageTitle="Items Listing">
    <!-- Container -->
    <div class="container animated bounceInUp">
        <c:choose>
            <c:when test="${empty products}">
                You have not created a item yet.
            </c:when>
            <c:otherwise>
                <c:forEach items="${products}" var="product">
                    <article class="col-sm-4 col-xs-12 prodctlist">
                        <a href="/products/${product.id}"> <img
                            src="${product.thumbnail}"></img>
                            <div class="detail">
                                <h3>${product.name}</h3>
                                <h4>${product.msrp}</h4>
                                <h4>
                                    <a href="/products/${product.id}/sales?create">Create
                                        sale</a>
                                </h4>
                            </div>
                        </a>
                    </article>
                </c:forEach>
            </c:otherwise>
        </c:choose>

    </div>
</template:page>