// Timer Code
//var end = new Date('05/20/2016 10:1 AM');

var _second = 1000;
var _minute = _second * 60;
var _hour = _minute * 60;
var _day = _hour * 24;
var timer;

function showRemaining() {
	var now = new Date();
	var distance = end - now;
	if (distance < 0) {

		clearInterval(timer);
		document.getElementById('countdown').innerHTML = 'EXPIRED!';

		return;
	}
	var days = Math.floor(distance / _day);
	var hours = Math.floor((distance % _day) / _hour);
	var minutes = Math.floor((distance % _hour) / _minute);
	var seconds = Math.floor((distance % _minute) / _second);
	var timetoshow = days + 'days ' + hours + 'hrs ' + minutes + 'mins '
			+ seconds + 'secs';
	$('#countdown').html(timetoshow);
}

var currency_symbols = {
	'USD' : '$', // US Dollar
	'EUR' : '€', // Euro
	'CRC' : '₡', // Costa Rican Colón
	'GBP' : '£', // British Pound Sterling
	'ILS' : '₪', // Israeli New Sheqel
	'INR' : '₹', // Indian Rupee
	'JPY' : '¥', // Japanese Yen
	'KRW' : '₩', // South Korean Won
	'NGN' : '₦', // Nigerian Naira
	'PHP' : '₱', // Philippine Peso
	'PLN' : 'zł', // Polish Zloty
	'PYG' : '₲', // Paraguayan Guarani
	'THB' : '฿', // Thai Baht
	'UAH' : '₴', // Ukrainian Hryvnia
	'VND' : '₫', // Vietnamese Dong
};

var Bidding = {
	nodeServerURL:$('#nodeserverurl').val(),
	attachEvents:function(){
		$('#place_bid').on('click',function(){
			var form = $('#place_bid').closest('form');
			$.ajax({
                url:Bidding.nodeServerURL+form.attr('action'),
                type: "POST",
                xhrFields: { withCredentials:true },
                data: {
                		amount :  form.find('#amount').val(),
                		_csrf:  form.find('#csrftoken').val(),
                	}
		    })
		    .done(function(data) {
		    	form.find('#amount').val('');
		    	return false;
		    })
		    .fail(function(error){
		    	location.reload();
		    });
		});
	},
	updateSalesWinningBid : function() {
		var winningbiddiv = $('#winning_bid_detail');
		if (winningbiddiv.length > 0) {
			var socket = io(Bidding.nodeServerURL,{query:"saleid="+$('#saleid').val()});
        	socket.on('bid', function(data){
        	    winningbiddiv.find('#lastUserName').html(
						data.createdByName);
				winningbiddiv.find('#lastUserAmount').html(
						data.amount.toFixed(1));
				if (!winningbiddiv.is(":visible")) {
					$('#nowinningbid').addClass('hide');
					winningbiddiv.removeClass('hide');
				}
        	});
		}
	},
	displayTimer : function() {
		$('#startTime').appendDtpicker();
		$('#endTime').appendDtpicker({
			autodateOnStart : false,
			minDate : $('#startTime').val(),
		});
		$('#startTime').change(function() {
			$('#endTime').val('');
			$('#endTime').handleDtpicker('destroy');
			$('#endTime').appendDtpicker({
				autodateOnStart : false,
				minDate : $('#startTime').val(),
				current : $('#startTime').val(),
			});
		});
	},
};

var coordslat = getCookie("coordslat");
	if(coordslat==""){
		console.log("Coordinates cookie not found");
		navigator.geolocation.getCurrentPosition(function(location) {
	    	  console.log(location.coords.latitude);
	    	  console.log(location.coords.longitude);
	    	  console.log(location.coords.accuracy);
	    	  console.log(location.coords);
	    	  //setCookie("coords","{\"lat\":"+location.coords.latitude+",\"lon\":"+location.coords.longitude+"}",1);
	    	  setCookie("coordslat",location.coords.latitude,1);
	    	  setCookie("coordslon",location.coords.longitude,1);
	    	  location.reload(true);
	    	});
	}else{
		//console.log(JSON.parse(coords).lat);
	}


function setCookie(cname, cvalue, exhours) {
    var d = new Date();
    d.setTime(d.getTime() + (exhours*60*60*1000));
    var expires = "expires="+ d.toUTCString();
    document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/;domain=.zaidi.in";
}
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

$(window).load(function() {
	if ($('#countdown').length > 0) {
		timer = setInterval(showRemaining, 1000);
	}
	Bidding.updateSalesWinningBid();
	//Bidding.displayTimer();
})
$(document).ready(function() {
	Bidding.attachEvents();
	Bidding.displayTimer();
});
