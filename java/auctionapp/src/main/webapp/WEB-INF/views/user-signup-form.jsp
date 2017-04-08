<%@ page session="false"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Signup</title>
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
	<div id="form-box" class="animated bounceInUp">

		<c:if test="${not empty error}">
			<div class="error">${error}</div>
		</c:if>
		<c:if test="${not empty msg}">
			<div class="msg">${msg}</div>
		</c:if>
		<form:form class="themeForm" method="post" modelAttribute="userForm"
			action="${userActionUrl}">
			<c:choose>
				<c:when test="${userForm['new']}">
					<header class="form-header">Signup for an account</header>
					<c:set var="userActionUrl" value="/signup" />
				</c:when>
				<c:otherwise>
					<header class="form-header">Update your profile</header>
					<c:set var="userActionUrl" value="/users" />
				</c:otherwise>
			</c:choose>

			<form:hidden path="id" />

			<spring:bind path="username">

				<label>Email :</label>
				<form:input path="username" id="username"
					placeholder="Your email address"/>
				<form:errors path="username" />
			</spring:bind>

			<spring:bind path="firstName">

				<label>First Name :</label>
				<form:input path="firstName" type="text" id="firstName"
					placeholder="First Name" />
				<form:errors path="firstName" />
			</spring:bind>

			<spring:bind path="lastName">

				<label>Last Name :</label>
				<form:errors path="lastName" />
				<form:input path="lastName" type="text" id="lastName"
					placeholder="Last Name" />
				
			</spring:bind>
			<c:choose>
				<c:when test="${userForm['new']}">
					<spring:bind path="password">

						<label>Password :</label>
						<form:password path="password" id="password"
							placeholder="password" />
						<form:errors path="password" />

					</spring:bind>

					<spring:bind path="confirmPassword">
						<label>Confirm Password :</label>
						<form:password path="confirmPassword" id="password"
							placeholder="Repeat the password" />
						<form:errors path="confirmPassword" />
					</spring:bind>

				</c:when>
				<c:otherwise>
					<input type="hidden" value="PASSWORD_PLACEHODLER" name="password" />
					<input type="hidden" value="PASSWORD_PLACEHODLER"
						name=confirmPassword />
				</c:otherwise>
			</c:choose>

			<spring:bind path="address">
				<label>Address :</label>
				<form:textarea path="address" rows="5" id="address"
					placeholder="address" />
				<form:errors path="address" />
			</spring:bind>

			<spring:bind path="phoneNumber">
				<label>Phone Number</label>
				<form:input path="phoneNumber" type="text" id="phoneNumber"
					placeholder="PhoneNumber" />
				<form:errors path="phoneNumber" />
			</spring:bind>


			<c:choose>
				<c:when test="${userForm['new']}">
					<button type="submit">Signup</button>
				</c:when>
				<c:otherwise>
					<button type="submit">Update</button>
				</c:otherwise>
			</c:choose>
			<input type="hidden" name="${_csrf.parameterName}"
				value="${_csrf.token}" />
		</form:form>
	</div>



</body>
<script src="/static/js/jquery-1.10.1.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
</html>