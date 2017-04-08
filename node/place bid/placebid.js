var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');
var querystring = require('querystring');
var cors = require('cors');
var request = require('request');
var events = require('events');
var eventEmitter = new events.EventEmitter();
var config = require('./config.json');
var corsOptions = {
  origin: config.javaseverurl,
  credentials:true,
};
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));
app.use(cookieParser());
app.use(cors(corsOptions));

app.get('/', function (req, res) {
   res.send('App running');
})

app.post('/sales/:id/bids', function(req, res){
	eventEmitter.emit('placeBid',req,res);
});

io.on('connection', function (socket) {
	var saleid = socket.request._query.saleid;
	socket.join(saleid);
	
	socket.on('disconnect', function() {
		socket.leave(saleid);
	});
});

server.listen(3000, function(){
  console.log('listening on *:3000');
});

var placeBid = function placeBid(req,res){
	request({
    url: config.javaseverurl +'/sales/'+req.params.id+'/bids',
    method: 'POST', 
	form: {
       'amount': req.body.amount,
	   '_csrf' : req.body._csrf
    },
    headers: {
        'Accept': 'application/json',
		'Content-Type' : 'application/x-www-form-urlencoded',
		'Cookie':'JSESSIONID='+req.cookies.JSESSIONID,
		}
	}, function(error, response, body){
		if(error) {
			console.log(error);
			res.status(response.statusCode).send(response.statusMessage);
		} else {
			if(response.statusCode == 403){
				res.status(response.statusCode).send(response.statusMessage);
			}
			else if(body.length == 0){
				res.send('bidding amount is less then winning bid amount');
			}else{
				res.send('placed successfully');
				io.to(req.params.id).emit('bid', JSON.parse(body));
			}
		}
	});
}
eventEmitter.on('placeBid', placeBid);