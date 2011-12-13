/*
 * console - jQuery UI Plugin
 * Console logger based on jQueryUI Dialog
 *
 * Examples and documentation at: http://gertonscorner.wordpress.com
 * See for more examples the demo at: 
 * http://gertonscorner.googlecode.com/svn/trunk/jquery/demo.html
 *
 * Version: 0.2 (13/12/2011)
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */
(function( $ ){
	$.fn.console = function( options ) {  
		$this = this; 
	  	// Create some defaults, extending them with any options that were provided
	  	var settings = $.extend( {
	  		'title' : 'jQueryUI console',
	  		'height' : '250',
	  		'position' : 'left',
	  		'tail': false,
	  		'buttonbar' : false
	  	}, options);
	  
	  	$this.dialog({ title: ($this.attr('title') != "") ? $this.attr('title'): settings.title
	  			   , height: settings.height
		  		   , position: settings.position 
		  		   });
	  	
	    var showButtonbar = function() {
	    	if (settings.buttonbar) {
		    	$this.dialog("option"
		    			, "buttons"
		    			, { "clear": function() {$this.empty();} }
		    			);
	    	} else {
	    		$this.dialog("option", "buttons", {} );
	    	}
	    };
	    
	    showButtonbar();
	    
	  	return {
		  	cls: function() { 
		  		$this.empty(); 
		  	},
		  	println: function(msg) {
		  		if (settings.tail) {
		  			$this.prepend(msg+'<br/>');
		  		} else {
		  			$this.append(msg+'<br/>');
		  		}
		  	},
		  	print: function(msg) {
		  		if (settings.tail) {
		  			$this.prepend(msg);
		  		} else {
		  			$this.append(msg);
		  		}
		  	},
		  	log: function(msg) {
		  		console.log(msg);
		  	},
		  	open: function() {
		  		$this.dialog("open");
		  	},
		  	close: function() {
		  		$this.dialog("close");
		  	},
		  	option: function(key,value) {
		  		if (value == null) {
		  			return settings[key];
		  		}
		  		settings[key] = value;
		  		
		  		if (jQuery.inArray(key, ['buttonbar','tail']) >= 0) {
		  			showButtonbar();
		  		} else {
		  			$this.dialog("option",key, value);
		  			
		  		}	  		
		  	}
	  	};
	  
	};
})( jQuery );