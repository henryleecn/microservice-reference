<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/template"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<template:page pageTitle="Sales Listing">
    <!-- Container -->
    <div class="container animated bounceInUp">
    <div id="typeahead" class="themeForm">
    <input class="typeahead" type="text" data-provide="typeahead" placeholder="Search for Sales">
    </div>
	<script src="/static/js/jquery-1.10.1.min.js"></script>
	<script type="text/javascript">
	var typeahead_url = 'http://elasticsearch-server:9200/sales/couchbaseDocument/_search';
	var search_url = 'http://elasticsearch-server:9200/sales/couchbaseDocument/_search';
	$(document).ready(function() {
	    $('.typeahead').typeahead({
	        source: function (query, process) {
	        	var lat = getCookie("coordslat");
	        	var lon = getCookie("coordslon");
	        	var input;
	        	if(lat==""){
	        		input = "{ \"query\": {  \"match\": {   \"doc.name\":\""+query+"\" }  } }"
	        	}else{
	        		input =  "{ \"query\": { \"bool\": { \"must\": { \"match\": { \"doc.name\": \""+query+"\" } }, \"filter\": { \"geo_distance\": { \"distance\": \"20km\", \"doc.saleLocation\": { \"lat\":" +lat+", \"lon\": "+lon+" } } } } }  }";
	        	}
	        	//console.log(input);
	        	return $.post(
	        			//'http://your-server-host:8085/search',
	        			typeahead_url,
	        			input, 
	        			function (data) {
							var result = new Array();
							//console.log(JSON.stringify(data));
							for (var index in data.hits.hits) {
							    result.push(data.hits.hits[index]._source.doc.name);
							}
							//console.log(JSON.stringify(result))
							return process(result);
	            		});
	        },
	        updater: function(selection){
	            console.log("You selected: " + selection)
	            $.get(search_url+"?q=doc.name:"+selection, function(data, status){
 			       window.location="/sales/"+data.hits.hits[0]._id;
 			    });
	        }
	    });
	})
	</script>
    
        <c:if test="${empty sales}">
             <article class="col-sm-4 col-xs-12 prodctlist">
             <div class="detail">
            <p>There are no Sales created yet. 
            <a href="/sales?create">Click here to create a Sale</a></p>
            </div>
            </article>
        </c:if>
        <c:forEach items="${sales}" var="sale">
            <article class="col-sm-4 col-xs-12 prodctlist">
            <a href="/sales/${sale.id}">
                
                <img src="${sale.thumbnail}">
                <div class="detail">
                <%-- <c:if test="${sale.winningBid ne null}">
                    
                    <h4>${sale.currencyCode} ${sale.winningBid.amount}</h4>
                </c:if> --%>
                    <h3>${sale.name}</h3>
                    <p>Ending: <fmt:formatDate value="${sale.endTime}" pattern="MMMM d,yyyy hh:mm a" /></p>
                    <p>Latest Bid Amount: <strong style="color:darkgreen">${sale.maxBidAmount} ${sale.currencyCode}</strong></p>
                </div>
            </a>
        </article>
        </c:forEach>
    </div>
</template:page>