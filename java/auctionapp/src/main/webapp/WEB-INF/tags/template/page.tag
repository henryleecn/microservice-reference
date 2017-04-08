<%@ attribute name="pageTitle" required="false" %>
<!DOCTYPE html>
<html>
<head>
<title>${pageTitle}</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<%@include file="/WEB-INF/views/cssInclude.jspf"%>
<link rel="shortcut icon" type="image/x-icon" media="all"
    href="/static/img/favicon.ico" />
</head>
<body >
        <%@include file="/WEB-INF/views/header.jsp"%>
        <jsp:doBody />  
</body>
<%@include file="/WEB-INF/views/jsInclude.jspf"%>
</html>