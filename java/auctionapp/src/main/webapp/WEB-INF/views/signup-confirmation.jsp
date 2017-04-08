<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<title>Product Details</title>
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
    <div class="container">
        <div class="col-sm-7 detail fadeInUp animated">
            <h2>
                You are now signed up.<br> Proceed to <a href="/sales?create">Create a new
                    Sale</a> or <a href="/sales">View the current Sales</a>. 
            </h2>
        </div>
    </div>
</body>
<script src="/static/js/jquery-1.10.1.min.js"></script>
<script src="/static/js/bootstrap.min.js"></script>
</html>