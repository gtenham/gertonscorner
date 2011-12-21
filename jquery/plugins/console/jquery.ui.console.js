/*
 * console - jQuery UI Plugin
 * Console logger based on jQueryUI Dialog
 *
 * Examples and documentation at: http://gertonscorner.wordpress.com
 * See for more examples the demo at: 
 * http://gertonscorner.googlecode.com/svn/trunk/jquery/demo.html
 *
 * Version: 0.3 (17/12/2011)
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *   
 * Depends:
 * 		jquery.ui.core.js
 * 		jquery.ui.widget.js
 * 	jquery.ui.button.js
 * 		jquery.ui.draggable.js
 * 		jquery.ui.mouse.js
 * 		jquery.ui.position.js
 * 		jquery.ui.resizable.js
 */
(function($){
	// Extend standard ui Dialog with new option(s)
	$.ui.dialog.prototype.options.tail = false;
	
	$.widget('ui.console', $.extend({}, $.ui.dialog.prototype, {
		
		//Initialize our console widget by initializing the base dialog.
		_init: function() {
			$.ui.dialog.prototype._init.call(this);
		},
		// extended public methods
		cls: function() { 
			this.element.empty(); 
	  	},
	  	println: function(msg) {
	  		if (this.options.tail) {
	  			this.element.prepend(msg+'<br/>');
	  		} else if (this.element.html() != "" && !this.element.html().match(/([\s\S]+)<br\s*\/?>$/)) {
	  			this.element.append('<br/>'+msg+'<br/>');
	  		} else {
	  			this.element.append(msg+'<br/>');
	  		}
	  	},
	  	print: function(msg) {
	  		if (this.options.tail) {
	  			this.element.prepend(msg);
	  		} else {
	  			this.element.append(msg);
	  		}
	  	}
	}));
}(jQuery));