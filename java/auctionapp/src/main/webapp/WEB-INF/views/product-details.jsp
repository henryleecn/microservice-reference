<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:page pageTitle="Item Details">
    <!-- Container -->
    <div class="container animated pulse singleproduct">
        <div class="col-sm-5 fadeInLeft animated">
            <img src="${product.imageURL}">
        </div>
        <div class="col-sm-7 detail fadeInUp animated">
            <h1>${product.name}</h1>
            <p>${product.msrp}</p>
            <p>
                <a href="${uri}/sales?create">Create Sale</a>
            </p>
            <p>
                <a href="${uri}/sales">View Sales</a>
            </p>
        </div>
    </div>
</template:page>