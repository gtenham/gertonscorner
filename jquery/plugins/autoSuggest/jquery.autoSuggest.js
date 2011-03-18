 /*
 * AutoSuggest  - jQuery Plugin
 * 
 * Copyright (c) 2010-2011 Gerton ten Ham
 * Examples and documentation at: http://gertonscorner.wordpress.com
 * See for more examples the demo at: 
 * http://gertonscorner.googlecode.com/svn/trunk/jquery/demo.html
 *
 * Version 0.2  (23/02/2011)
 *
 * This Plug-In will auto-complete or auto-suggest completed search queries
 * for you as you type. It supports keybord navigation (UP + DOWN + RETURN), as well
 * as multiple AutoSuggest fields on the same page.
 *
 * Inspired by the AutoSuggest plugin by: Drew Wilson (www.drewwilson.com)
 * Version 1.4 - code.drewwilson.com/entry/autosuggest-jquery-plugin
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 */

(function($){
	$.fn.autoSuggest = function(data, options) {
		var defaults = { 
			asHtmlID: false,
			emptyText: "No Results Found",
			limitText: "No More Selections Are Allowed",
			selectedItemProp: "value", //name of object property
			selectedValuesProp: "value", //name of object property
			searchObjProps: "value", //comma separated list of object property names
			queryParam: "q",
			retrieveLimit: false, //number for 'limit' param on ajax request
			extraParams: "",
			dynamicParamsMap: false,
			matchCase: false,
			minChars: 1,
			keyDelay: 400,
			resultsHighlight: true,
			neverSubmit: false,
			selectionLimit: false,
			showResultList: true,
			showScrollbar: false,
			start: function(){},
		  	selectionClick: function(elem){},
		  	selectionAdded: function(elem){},
		  	formatList: false, //callback function
		  	beforeRetrieve: function(string){ return string; },
		  	retrieveComplete: function(data){ return data; },
		  	resultClick: function(data){},
		  	resultsComplete: function(){}
	  	};  
	 	var opts = $.extend(defaults, options);	 	
	 	
		var d_type = "object";
		var d_count = 0;
		if(typeof data == "string") {
			d_type = "string";
			var req_string = data;
		} else {
			var org_data = data;
			for (k in data) if (data.hasOwnProperty(k)) d_count++;
		}
		if((d_type == "object" && d_count > 0) || d_type == "string"){
			return this.each(function(x){
				if(!opts.asHtmlID){
					x = x+""+Math.floor((Math.random()*10)*($(this).position().top+$(this).position().left)); //this ensures there will be unique IDs on the page if autoSuggest() is called multiple times
					var x_id = "as-input-"+x;
				} else {
					x = opts.asHtmlID;
					var x_id = x;
				}
				opts.start.call(this);
				var input = $(this);
				
				input.attr("autocomplete","off").addClass("as-input");
				
				var input_focus = false;
				
				// Setup basic elements and render them to the DOM
				input.wrap('<ul class="as-selections" id="as-selections-'+x+'"></ul>').wrap('<li class="as-original" id="as-original-'+x+'"></li>');
				var selections_holder = $("#as-selections-"+x);
				var org_li = $("#as-original-"+x);				
				var results_holder = $('<div class="as-results" id="as-results-'+x+'"></div>').hide();
				var results_ul =  $('<ul class="as-list"></ul>');
				
				var values_input = "";
				
				selections_holder.click(function(){
					input_focus = true;
					input.focus();
				}).mousedown(function(){ input_focus = false; }).after(results_holder);	

				var timeout = null;
				var prev = "";
				
				// Handle input field events
				input.focus(function(){			
					if(input_focus){
						$("li.as-selection-item", selections_holder).removeClass("blur");
						if($(this).val() != ""){
							results_ul.css("width", input.outerWidth()-2);
							
							results_holder.show();
						}
					}
					input_focus = true;
					//return true;
				}).blur(function(){
					if(input_focus){
						$("li.as-selection-item", selections_holder).addClass("blur").removeClass("selected");
						results_holder.hide();
					}				
				}).keydown(function(e) {
					// track last key pressed
					lastKeyPressCode = e.keyCode;
					first_focus = false;
					switch(e.keyCode) {
						case 38: // up
							e.preventDefault();
							moveSelection("up");
							break;
						case 40: // down
							e.preventDefault();
							moveSelection("down");
							break;
						case 13: // return
							var active = $("li.active:first", results_holder);
							if(active.length > 0){
								active.click();
								results_holder.hide();
							}
							if(opts.neverSubmit || active.length > 0){
								e.preventDefault();
							}
							break;
						case 9: // tab
							var active = $("li.active:first", results_holder);
							if(active.length > 0){
								active.click();
								results_holder.hide();
							}
							
							break;
						case 8: // backspace
							if(input.val().length <= 1){
								results_holder.html("").hide();
								prev = "";
							}
							if($(":visible",results_holder).length > 0){
								if (timeout){ clearTimeout(timeout); }
								timeout = setTimeout(function(){ keyChange(); }, opts.keyDelay);
							}
							break;
						case 46: // delete
							if(input.val().length <= getSelectionRange(this) ) { //(this.selectionEnd-this.selectionStart)){
								results_holder.html("").hide();
								prev = "";
								results_holder.hide();
							}
							if($(":visible",results_holder).length > 0){
								if (timeout){ clearTimeout(timeout); }
								timeout = setTimeout(function(){ keyChange(); }, opts.keyDelay);
							}
							break;
						default:
							if(opts.showResultList){
								if(opts.selectionLimit && $("li.as-selection-item", selections_holder).length >= opts.selectionLimit){
									results_ul.html('<li class="as-message">'+opts.limitText+'</li>');
									results_holder.show();
								} else {
									if (timeout){ clearTimeout(timeout); }
									timeout = setTimeout(function(){ keyChange(); }, opts.keyDelay);
								}
							}
							break;
					}
				});
				
				function getSelectionRange(inputbox) {
					var startPos = 0;
					var endPos = 0;
					var length = 0;
					var tmpText = "";
					
					if (inputbox.setSelectionRange) { // W3C/Gecko
						startPos = inputbox.selectionStart;
						endPos = inputbox.selectionEnd;
						tmpText = (startPos != endPos) ? inputbox.value.substring(startPos, endPos): "";
					}
					else if (document.selection) { // IE
						var oText = document.selection.createRange().duplicate();
						tmpText = oText.text;
						for (; oText.moveStart("character", -1) !== 0; startPos++);
						endPos = tmpText.length + startPos;
					}
					length = tmpText.length;
					return length;
				}
				
				function keyChange() {
					// ignore if the following keys are pressed: [shift] [capslock]
					if( (lastKeyPressCode > 8 && lastKeyPressCode < 32) ){ return results_holder.hide(); }
					var string = input.val().replace(/[\\]+|[\/]+/g,"");
					if (string == prev) return;
					prev = string;
					if (string.length >= opts.minChars) {
						selections_holder.addClass("loading");
						if(d_type == "string"){
							var limit = "";
							var extraParams = opts.extraParams;
							
							if(opts.retrieveLimit && !opts.showScrollbar){
								limit = "&limit="+encodeURIComponent(opts.retrieveLimit);
							}
							
							if(opts.dynamicParamsMap){
								$.each(opts.dynamicParamsMap, function(key, fieldId) { 
									var dynVal = $("#"+fieldId).val();
									extraParams += "&"+key+"="+encodeURIComponent(dynVal);
								});
							}
							if(opts.beforeRetrieve){
								string = opts.beforeRetrieve.call(this, string);
							}
							// no-cache before calling json
							$.ajaxSetup({ cache: false });
							$.getJSON(req_string+"?"+opts.queryParam+"="+encodeURIComponent(string)+limit+extraParams, function(data){ 
								d_count = 0;
								// Set cache-on for subsequent ajax requests
								$.ajaxSetup({ cache: true });
								var new_data = opts.retrieveComplete.call(this, data);
								for (k in new_data) if (new_data.hasOwnProperty(k)) d_count++;
								processData(new_data, string); 
							});
						} else {
							if(opts.beforeRetrieve){
								string = opts.beforeRetrieve.call(this, string);
							}
							processData(org_data, string);
						}
					} else {
						selections_holder.removeClass("loading");
						results_holder.hide();
					}
				}
				var num_count = 0;
				function processData(data, query){
					if (!opts.matchCase){ query = query.toLowerCase(); }
					var matchCount = 0;
					results_holder.html(results_ul.html("")).hide();
					for(var i=0;i<d_count;i++){				
						var num = i;
						num_count++;
						var forward = false;
						if(opts.searchObjProps == "value") {
							var str = data[num].value;
						} else {	
							var str = "";
							var names = opts.searchObjProps.split(",");
							for(var y=0;y<names.length;y++){
								var name = $.trim(names[y]);
								str = str+data[num][name]+" ";
							}
						}
						if(str){
							if (!opts.matchCase){ str = str.toLowerCase(); }				
							if(str.search(query) != -1 && values_input.search(data[num][opts.selectedValuesProp]) == -1){
								forward = true;
							}	
						}
						if(forward){
							var formatted = $('<li class="as-result-item" id="as-result-item-'+num+'"></li>').click(function(){
									var raw_data = $(this).data("data");
									var number = raw_data.num;
									
									if($("#as-selection-"+number, selections_holder).length <= 0 ){
										var data = raw_data.attributes;
										
										input.focus();
										prev = "";
										input_focus = false;
										add_selected_item(data, number);
										opts.resultClick.call(this, raw_data);
										results_holder.hide();
									}
								}).mousedown(function(){ input_focus = false; }).mouseover(function(){
									$("li", results_ul).removeClass("active");
									$(this).addClass("active");
								}).data("data",{attributes: data[num], num: num_count});
							var this_data = $.extend({},data[num]);
							if (!opts.matchCase){ 
								var regx = new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + query + ")(?![^<>]*>)(?![^&;]+;)", "gi");
							} else {
								var regx = new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + query + ")(?![^<>]*>)(?![^&;]+;)", "g");
							}
							
							if(opts.resultsHighlight){
								this_data[opts.selectedItemProp] = this_data[opts.selectedItemProp].replace(regx,"<em>$1</em>");
							}
							if(!opts.formatList){
								formatted = formatted.html(this_data[opts.selectedItemProp]);
							} else {
								formatted = opts.formatList.call(this, this_data, formatted);	
							}
							results_ul.append(formatted);
							delete this_data;
							matchCount++;
							if(opts.retrieveLimit && opts.retrieveLimit == matchCount && !opts.showScrollbar){ break; }
						}
					}
					selections_holder.removeClass("loading");
					if(matchCount <= 0){
						results_ul.html('<li class="as-message">'+opts.emptyText+'</li>');
					}
					results_ul.css("width", input.outerWidth());
					if (opts.showScrollbar && opts.retrieveLimit && opts.retrieveLimit <= matchCount) {
						var line_height = results_ul.css("line-height");
						var maxHeight;
						if(opts.retrieveLimit && opts.retrieveLimit > 0){
							var maxHeight = line_height.replace('px','')*opts.retrieveLimit;
						}
						results_ul.css("overflow-y", "scroll");
						results_ul.css("height", maxHeight+"px");
						results_ul.scroll(function() {
							input_focus = false;
						});
						input.focus();
					} else {
						results_ul.css("overflow-y", "");
						results_ul.css("height", "");
						input_focus = true;
						input.focus();
					}
					results_holder.show();
					opts.resultsComplete.call(this);
				}
				
				function add_selected_item(data, num){
					input.val(data[opts.selectedValuesProp]); //gth
					//var item = $('<li class="as-selection-item" id="as-selection-'+num+'"></li>').click(function(){
					//		opts.selectionClick.call(this, $(this));
					//		selections_holder.children().removeClass("selected");
					//		$(this).addClass("selected");
					//	}).mousedown(function(){ input_focus = false; });
					
					opts.selectionAdded.call(this, org_li.prev());	
				}
				
				function moveSelection(direction){
					if($(":visible",results_holder).length > 0){
						var lis = $("li", results_holder);
						if(direction == "down"){
							var start = lis.eq(0);
						} else {
							var start = lis.filter(":last");
						}					
						var active = $("li.active:first", results_holder);
						if(active.length > 0){
							if(direction == "down"){
							start = active.next();
							} else {
								start = active.prev();
							}	
						}
						lis.removeClass("active");
						start.addClass("active");
						if (opts.showScrollbar) {
							var listitem_number = $("li.active:first").index()+1;
							var listitem_height = results_ul.css("line-height").replace('px','');
							results_ul.scrollTop((listitem_number-1)*listitem_height);
						}
					}
				}
									
			});
		}
	};
})(jQuery);  	