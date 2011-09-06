// Parallel loading of the javascript resources needed 
$LAB
.setOptions({AlwaysPreserveOrder:true})
.script("http://cdnjs.cloudflare.com/ajax/libs/json2/20110223/json2.js")
.script("http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js")
.script("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js")
.script("http://ajax.microsoft.com/ajax/jquery.templates/beta1/jquery.tmpl.min.js")
.script("http://cdnjs.cloudflare.com/ajax/libs/underscore.js/1.1.7/underscore-min.js")
.script("http://cdnjs.cloudflare.com/ajax/libs/backbone.js/0.5.3/backbone-min.js")
.script("/js/ext/jquery.jgrowl-min.js")
.script("/js/backbone.storage.js")
.script("/js/todo.ui.utils.js")
.script("/js/backbone-models-todos.js")
.script("/js/backbone-views-todos.js")
.wait(function() {
	$(document).ready(function() {
		// Your dom ready stuff
		var App = new AppView;
	});
});