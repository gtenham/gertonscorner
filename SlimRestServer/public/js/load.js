// Parallel loading of the javascript resources needed 
$LAB
.setOptions({AlwaysPreserveOrder:true})
.script("http://cdnjs.cloudflare.com/ajax/libs/json2/20110223/json2.js")
.script("http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js")
.script("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js")
.script("http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.1.7/underscore-min.js")
.script("http://cdnjs.cloudflare.com/ajax/libs/backbone.js/0.5.3/backbone-min.js")
.script("http://cdnjs.cloudflare.com/ajax/libs/socket.io/0.8.4/socket.io.min.js")
.wait(function() {
	$(document).ready(function() {
		// Your dom ready stuff
		var socket = io.connect('http://localhost:8888');
		socket.on('todos', function (data) {
		    console.log('Message pushed from server: '+data.msg);
		});

	});
});