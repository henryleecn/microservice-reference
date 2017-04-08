<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:page pageTitle="Login">

    <!-- Container -->
    <div id="form-box" class="animated bounceInUp login-fullwidth">

        <form class="themeForm" name='themeForm'
            action="<c:url value='/login' />" method='POST'>
            <header class="form-header">
                Login or <a href="/signup    ">Signup</a>!
            </header>
            <c:if test="${not empty msg || not empty error}">
                <div class="msg">${msg}${error}</div>
            </c:if>
            <table>
                <tr>
                    <label>Email *: </label>

                    <input type='text' name='username' value=''>

                    <label> Password *: </label>
                    <input type='password' name='password' />

                    <input name="submit" type="submit" value="submit" />
                </tr>
            </table>
            <input type="hidden" name="${_csrf.parameterName}"
                value="${_csrf.token}" />

        </form>
    </div>
</template:page>