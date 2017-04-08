<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<sec:authentication var="user" property="principal" />
<header>
    <nav class="navbar navbar-default animated fadeInDown"
        role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                data-target="#navbar-collapse-1">
                <span class="sr-only">Toggle navigation</span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span>
            </button>

        </div>
        <a class="navbar-brand" href="#">Spring Silent Auction</a>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="navbar-collapse-1">
            <ul class="nav navbar-nav navbar-left">
                <li><img src="/static/img/zaidi.gif"
                    onclick="location.href='/sales'"></li>
                <!-- <li><a href="#">Menu</a></li> -->
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${'anonymousUser'!=user}">
                    <li><a href="/sales?create">New Sale</a></li>


                    <li><a href="/sales?user=${user.id}">My Items</a></li>
                </c:if>
                <li><a href="/sales">All Sales</a></li>
                <c:if test="${'anonymousUser'!=user}">
                    <li><a href="/logout">Sign out</a></li>
                </c:if>
                <!-- li class="dropdown"><a href="#" class="dropdown-toggle"
                    data-toggle="dropdown">Menu <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                        <li class="divider"></li>
                        <li><a href="/logout">Sign out</a></li>
                    </ul></li -->
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </nav>

</header>