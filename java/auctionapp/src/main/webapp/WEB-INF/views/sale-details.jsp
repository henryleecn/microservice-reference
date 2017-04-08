<%@page import="java.util.Currency"%>
<%@ page session="false"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<template:page pageTitle="Home">
	<!-- Container -->
	<div class="container animated pulse singleproduct">
		<div class="col-sm-5 fadeInLeft animated">
			<img src="${sale.imageURL}">
		</div>
		<div class="col-sm-7 detail fadeInUp animated">
			<h1>${sale.name}</h1>
			<p>${sale.description}</p>
			<p>At ${sale.city}</p>
			<%-- <p>START DATE: ${sale.startTime}</p> --%>
			<p>
				Auction ends at
				<fmt:formatDate value="${sale.endTime}"
					pattern="MMMM d,yyyy hh:mm a" />
			</p>
			<script type="text/javascript">
				var end = new Date(
						'<fmt:formatDate value="${sale.endTime}" pattern="MMMM d,yyyy HH:mm:ss" />');
			</script>
			<p>
				<span>Time Remaining :</span><span id="countdown"></span>
			</p>
			<input type="hidden" id="saleid" value="${sale.id}" /> <input
				type="hidden" id="nodeserverurl" value="${nodeserverurl}" />
			<sec:authentication var="user" property="principal" />
			<c:choose>
				<c:when test="${sale.open}">
					<c:choose>
						<c:when test="${sale.winningBid ne null}">
							<div id="winning_bid_detail">
								<p>
									<span>Last Bid Person : </span><span id='lastUserName'>
									<c:choose><c:when test="${empty sale.winningBid.createdByName}">${sale.winningBid.createdBy}</c:when><c:otherwise>${sale.winningBid.createdByName}</c:otherwise></c:choose>
									</span>
								</p>
								<p>
									<span>Last Bid Amount : </span>${sale.currencyCode} <span
										id='lastUserAmount'> ${sale.winningBid.amount}</span>
								</p>
								<%--                                     <fmt:formatNumber  type="currency" currencyCode="${sale.currency.currencyCode}" value="${sale.winningBid.amount} "/> --%>
							</div>
						</c:when>
						<c:otherwise>
							<div id="winning_bid_detail" class="hide">
								<p>
									<span>Last Bid Person : </span><span id='lastUserName'></span>
								</p>
								<p>
									<span>Last Bid Amount : </span><span id='lastUserAmount'></span>
								</p>
							</div>
							<c:if test="${user.id != sale.createdBy}">
								<p id="nowinningbid">Be the first to bid!</p>
							</c:if>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${user.id !=sale.createdBy}">
							<form:form method="post" modelAttribute="bid" action="${bid_uri}">
								<spring:bind path="amount">
									<div
										class="clearfix form-group ${status.error ? 'has-error' : ''}">
										<label class="col-sm-2 clearleft control-label">Bid
											Amount</label>
										<div class="col-sm-10">
											<form:input path="amount" class="" id="amount"
												placeholder="your bid price" />
											<form:errors path="amount" class="control-label" />
											<input type="button" id="place_bid" value="submit"></input>
										</div>
									</div>
								</spring:bind>

								<input type="hidden" name="${_csrf.parameterName}"
									id="csrftoken" value="${_csrf.token}" />
							</form:form>
						</c:when>
						<c:otherwise>You can not bid on own Sale.
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
                    This sale is not active!
                </c:otherwise>
			</c:choose>
		</div>
	</div>
</template:page>