/*
 * limitMaxlength - jQuery Plugin
 * Use maxlength attribute on textareas
 *
 * Examples and documentation at: http://gertonscorner.wordpress.com
 * See for more examples the demo at: 
 * http://gertonscorner.googlecode.com/svn/trunk/jquery/demo.html
 *
 * Version: 0.1 (19/12/2011)
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */
(function($){
	$.fn.limitMaxlength = function(options) {
		
		var defaults = { 
				attribute: "maxlength",
				onInitCallback: function(elem, remaining){},
				onLimitCallback: function(elem, remaining){},
				onEditCallback: function(elem, remaining){}
		  	};  
		var settings = $.extend(defaults, options);
	
		return this.each(function() {
			// Initialise variables
			var textarea = $(this);
			var maxlength = parseInt(textarea.attr(settings.attribute));
			var length = textarea.val().length;
			settings.onInitCallback.call(this, textarea, (maxlength - length));
			
			// Event handler to limit the textarea
			function onEdit() {
				
				// Use timeout for settling focus, paste events
				setTimeout(function() {
						length = textarea.val().length;
						if ((maxlength - length) == 0) {
							textarea.val(textarea.val().substr(0, maxlength));
							settings.onLimitCallback.call(this,textarea,maxlength);
						}
						settings.onEditCallback.call(this,textarea,(maxlength - length));
					},
					100);
				
			};
			
			
			textarea
				.keyup(function(e) {
						// All keyCodes except [SHIFT], [CTRL] etc
						if( (e.keyCode >= 46 || e.keyCode == 8 || e.keyCode == 32) ) {
							onEdit();
						}
					})
				.focus(function() {
						onEdit();
					})
				.live('paste', function() {
							onEdit();
						});
			
			
		});
	
	};
})(jQuery);