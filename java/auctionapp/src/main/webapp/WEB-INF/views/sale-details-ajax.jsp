
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
    uri="http://www.springframework.org/security/tags"%>
<html>
<head>
<title>Item Details</title>
<style>
</style>
<script
    src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.2/jquery.min.js"></script>
<script>
    var csrf_token = '${_csrf.token}';
    $("body").bind("ajaxSend", function(elm, xhr, s) {
        if (s.type == "POST") {
            xhr.setRequestHeader('X-CSRF-TOKEN', csrf_token);
        }
    });
    function getCSRFTokenValue() {
        return $("#csrftoken").val();
    }
    function enableBidButton(flag) {
        $("#bidbutton").prop("disabled", flag);
    }

    function display(data) {
        var json = "<h4>Ajax Response</h4><pre>"
                + JSON.stringify(data, null, 4) + "</pre>";
        $('#feedback').html(json);
    }

    function postBid() {
        var amt = $("#amount").val();

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "${bid_uri}",
            data : {
                _csrf: csrf_token,
                amount : amt
            },
            dataType : 'json',
            timeout : 5000,
            success : function(data) {
                console.log("SUCCESS: ", data);
                display(data);
            },
            error : function(e) {
                console.log("ERROR: ", e);
                display(e);
            },
            done : function(e) {
                console.log("DONE");
                enableBidButton(true);
            }
        });

    }

    jQuery(document).ready(function($) {

        $("#bidbutton").click(function(event) {

            // Disble the search button
            enableBidButton(false);

            // Prevent the form from submitting via the browser.
            event.preventDefault();

            postBid();

        });

    });
</script>
</head>
<body>
    TEXT/HTML View,
    <br />
    <c:out value="${sale}" />
    <input type="hidden" name="sale" value="${sale.id}" />
    <sec:authorize
        access="hasRole('USER') || hasRole('CSR') || hasRole('ADMIN') || hasRole('SUPERADMIN')">
        <input id="amount" type="text" name="amount" />
        <input type="button" id="bidbutton" value="Bid" name="bid" />
    </sec:authorize>
    <sec:authorize access="hasRole('anonymous')">
        Signup or Login to bid
    </sec:authorize>
    <input type="hidden" name="${_csrf.parameterName}" id="csrftoken"
        value="${_csrf.token}" />

</body>
</html>