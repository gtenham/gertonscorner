(function($){
	$.fn.limitMaxlength = function(options){

		var settings = jQuery.extend({
			attribute: "maxlength",
			onLimitCallback: function(){},
			onEditCallback: function(){}
		}, options);
	
		// Initialise variables
		var element = jQuery(this);
		var maxlength = parseInt(element.attr(settings.attribute));
		var length = element.val().length;
		
		// Event handler to limit the textarea
		var onEdit = function(){
			// Use timeout for settling focus, paste events
			setTimeout(function() {
				    length = element.val().length;
					if ((maxlength - length) == 0) {
						element.val(element.val().substr(0, maxlength));
						settings.onLimitCallback.call(this,maxlength);
					}
					settings.onEditCallback.call(this,(maxlength - length));
				},
			100);
			
		};
		
		element.keyup(function(e) {
						// All keyCodes except [SHIFT], [CTRL] etc
						if( (e.keyCode >= 46 || e.keyCode == 8 || e.keyCode == 32) ) {
							onEdit();
						}
					})
				.focus(onEdit)
				.live('paste', onEdit);
	};
})(jQuery);