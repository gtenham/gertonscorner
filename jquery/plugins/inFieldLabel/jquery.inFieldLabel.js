 /*
 * inFieldLabel - jQuery Plugin
 * The ultimate in-field-label for input text fields
 *
 * Copyright (c) 2010 Gerton ten Ham
 * Examples and documentation at: http://gertonscorner.wordpress.com
 *
 * Version: 0.2 (30/08/2010)
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */
(function($){
	$.fn.inFieldLabel = function(options) {
		var defaults = { 
			labelText: "Enter Name Here",
			useAttribute: false
	  	};  
	 	var opts = $.extend(defaults, options);
		
		var input = $(this);
		var inlineLabel = opts.labelText;
		if (opts.useAttribute && (typeof opts.useAttribute == "string")) {
			inlineLabel = input.attr(opts.useAttribute);
			// Override default browser behaviour for useAttribute "title"
			if (opts.useAttribute == "title") {
				input.hover(function(e){											 							   
					input.attr("title","");									  				
				},
				function(){	
					input.attr("title",inlineLabel);
				});	
			}
		}
		var labelContainer = $('<span class="inline-label">'+inlineLabel+'</span>');
		
		// Modify the DOM by adding placeholder label
		input.before(labelContainer);
		
		labelContainer.click(function() {
			input.focus();
		});
		
		showInlineLabel();
		
		input
			.focus(function(){
                showInlineLabel();
            })
            .blur(function(){
				showInlineLabel();
            })
			.keydown(function(e) {
				// All keystrokes except [shift]
				if (e.keyCode != 16) {
				   labelContainer.hide();
				}
				// Check on del key, if no data left, force show label
				if (e.keyCode == 46 && input.val().length <= (this.selectionEnd-this.selectionStart)) {
					showInlineLabel(true);
				}
				// Check on backspace key, if no data left, force show label
				if (e.keyCode == 8 && input.val().length <= 1) {
					showInlineLabel(true);
				}
			})
			.change(function(){
                showInlineLabel();
            })
            ;
		function showInlineLabel(force) {
			if ( force || input.val() === '') {
				labelContainer.show();
			} else {
				labelContainer.hide();
			}
		}
		
	};
	
})(jQuery); 