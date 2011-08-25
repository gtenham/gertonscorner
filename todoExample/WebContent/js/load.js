// Parallel loading of the javascript resources needed 
$LAB
.setOptions({AlwaysPreserveOrder:true})
.script("http://cdnjs.cloudflare.com/ajax/libs/json2/20110223/json2.js")
.script("http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js")
.script("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js")
.script("http://ajax.microsoft.com/ajax/jquery.templates/beta1/jquery.tmpl.min.js")
.script("http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.1.7/underscore-min.js")
.script("http://cdnjs.cloudflare.com/ajax/libs/backbone.js/0.5.3/backbone-min.js")
.script("/js/backbone-mvc-todos.js")
.wait(function() {
	$(document).ready(function() {
		// Your dom ready stuff
		var App = new AppView;
		//$.get('/templates/todo-templates.tpl', function(templates) {
		//	$('body').append(templates);
		//	var App = new AppView;
		//});
	});
});