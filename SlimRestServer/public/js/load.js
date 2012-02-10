// Parallel loading of the javascript resources needed 
$LAB
.setOptions({AlwaysPreserveOrder:true})
.script("http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js")
.script("http://cdnjs.cloudflare.com/ajax/libs/socket.io/0.8.4/socket.io.min.js")
.wait(function() {
	$(document).ready(function() {
		// Your dom ready stuff
		var socket = io.connect('http://localhost:8888');
		socket.on('connect', function () {
		    socket.send('hi');

		    socket.on('message', function (msg) {
		    	console.log(msg);
		    	$('#main dl').append('<dt>'+msg.action+'</dt><dd>'+msg.message+'</dd>')
		    });
		  });

	});
});