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
			selectedItemProp: "value", //name of object property
			selectedValuesProp: "value", //name of object property
			searchObjProps: "value", //false, "value" or a comma separated list of object property names to search in
			queryParam: "q",
			retrieveLimit: false, //number for 'limit' param on ajax request
			extraParams: "",
			dynamicParamsMap: false, 
			matchCase: false,
			matchAny: true, // Match any occurrence of queryParam
			trimQueryParam: "ltrim", //false, ltrim, rtrim or trim
			minChars: 1,
			keyDelay: 400,
			resultsHighlight: true,
			neverSubmit: false, // Prevent submit form on [ENTER]
			showResultList: true, // Show result list
			showScrollbar: false, // Show scrollbar, "retrieveLimit" will be used for determining height
			start: function(){},
			formatRawData: false, //callback function : function(string){ return string; }
		  	formatList: false, //callback function
		  	beforeRetrieve: function(string){ return string; },
		  	retrieveComplete: function(data){ return data; },
		  	resultClick: false, //function(data){},
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
				var keep_list = false;
				
				input.attr("autocomplete","off").addClass("as-input");
				
				// Setup basic elements and render them to the DOM
				input.wrap('<div class="as-original" id="as-original-'+x+'"></div>');
				var input_holder = $("#as-original-"+x);
				var results_holder = $('<div class="as-results" id="as-results-'+x+'"></div>');
				var results_list =  $('<ul class="as-list"></ul>');
				
				input_holder.click(function(){
					input.focus();
				}).after(results_holder);	

				$('html').bind('click',function(){ if(!keep_list) {toggleResultsList('hide');}});
				
				var timeout = null;
				var prev = "";
				
				// Handle input field events
				input.focus(function(){		
					if($(this).val() != "" && !keep_list){
						results_list.css("width", input.outerWidth()-2);
						keep_list = true;
						toggleResultsList('show');
					}
				}).blur(function(e) { keep_list = false;
				})
				.keydown(function(e) {
					// track last key pressed
					lastKeyPressCode = e.keyCode;
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
								toggleResultsList('hide');
							}
							if(opts.neverSubmit || active.length > 0){
								e.preventDefault();
							}
							break;
						case 9: // tab
							var active = $("li.active:first", results_holder);
							if(active.length > 0){
								active.click();
							}
							toggleResultsList('hide');
							break;
						case 8: // backspace
							if(input.val().length <= 1){
								results_holder.html("");
								prev = "";
								toggleResultsList('hide');
							}
							if (timeout){ clearTimeout(timeout); }
							timeout = setTimeout(function(){ keyChange(); }, opts.keyDelay);
							break;
						case 46: // delete
							if(input.val().length <= getSelectionRange(this) ) {
								results_holder.html("");
								prev = "";
								toggleResultsList('hide');
							}
							if (timeout){ clearTimeout(timeout); }
							timeout = setTimeout(function(){ keyChange(); }, opts.keyDelay);
							break;
						default:
							if(opts.showResultList){
								if (timeout){ clearTimeout(timeout); }
								timeout = setTimeout(function(){ keyChange(); }, opts.keyDelay);
							} 
							break;
					}
				});
				
				function toggleResultsList(action) {
					if (action == 'hide') {
						results_holder.hide();
						results_list.hide();
					} else {
						results_holder.show();
						results_list.show();
					}
				}
				
				
				function trimValue(val) {
					var returnValue = val;
					switch(opts.trimQueryParam) {
						case "ltrim":
							returnValue = (val||"").replace( /^(\s|\u00A0)+/g,"");
							break;
						case "rtrim":
							returnValue = (val||"").replace( /[\s\u00A0]+$/g,"");
							break;
						case "trim":
							returnValue = $.trim(val);
							break;
						default:
							returnValue = val;
							break;
					}
					return returnValue;
				}
				
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
					// Trimming value (using opts.trimQueryParam)
					var trimInputValue = trimValue(input.val());
					
					var string = trimInputValue.replace(/[\\]+|[\/]+/g,"");
					
					if (string == prev) return;
					prev = string;
					if (string.length >= opts.minChars) {
						input_holder.addClass("loading");
						if(d_type == "string"){
							var limit = "";
							var extraParams = opts.extraParams;
							
							if(opts.retrieveLimit && !opts.showScrollbar){
								limit = "&limit="+encodeURIComponent(opts.retrieveLimit);
							}
							
							if(opts.dynamicParamsMap){
								$.each(opts.dynamicParamsMap, function(key, fieldId) { 
									var trimDynVal = trimValue($("#"+fieldId).val());
									var dynVal = trimDynVal.replace(/[\\]+|[\/]+/g,"");
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
						input_holder.removeClass("loading");
						toggleResultsList('hide');
					}
				}
				var num_count = 0;
				function processData(data, query){
					if (!opts.matchCase){ query = query.toLowerCase(); }
					var matchCount = 0;
					results_holder.html(results_list.html(""));
					if(data.length > 0){
						for(var i=0;i<d_count;i++){				
							var num = i;
							num_count++;
							var str = "";
							var localmatch = false;
							if (!opts.searchObjProps) {
								localmatch = true;
							} else {
								if(opts.searchObjProps == "value") {
									str = data[num].value;
								} else {	
									str = "";
									var names = opts.searchObjProps.split(",");
									for(var y=0;y<names.length;y++){
										var name = $.trim(names[y]);
										str = str+data[num][name]+" ";
									}
								}
								if(str){
									if (!opts.matchCase){ str = str.toLowerCase(); }				
									if(str.search(query) != -1){
										localmatch = true;
									}	
								}
							}
							
							if(localmatch){
								var formatted = $('<li class="as-result-item" id="as-result-item-'+num+'"></li>').click(function(){
										var raw_data = $(this).data("data");
										var number = raw_data.num;
										var data = raw_data.attributes;
											
										prev = "";
										add_selected_item(data, number);
										input.focus();
										toggleResultsList('hide');
										if (opts.resultClick) {
                                           opts.resultClick.call(this, raw_data);
                                        }
									}).mouseover(function(){
										$("li", results_list).removeClass("active");
										$(this).addClass("active");
									}).data("data",{attributes: data[num], num: num_count});
								var this_data = $.extend({},data[num]);
								
								var regxMatchCase = (!opts.matchCase) ? "gi" : "g";
								if (!opts.matchAny){
									var regx = new RegExp("^(" + query + ")(?![^<>]*>)(?![^&;]+;)", regxMatchCase);
								} else {
									var regx = new RegExp("(?![^&;]+;)(?!<[^<>]*)(" + query + ")(?![^<>]*>)(?![^&;]+;)", regxMatchCase);
								}
								// pre-format raw string data
                                if (opts.formatRawData) {
                            	   this_data[opts.selectedItemProp] = opts.formatRawData.call(this, this_data[opts.selectedItemProp]);
                                }
								// Highlight query within result
								if(opts.resultsHighlight){
									this_data[opts.selectedItemProp] = this_data[opts.selectedItemProp].replace(regx,"<em>$1</em>");
								}
								// Format the resultlist with custom callback function resulting in new html
								if(!opts.formatList){
									formatted = formatted.html(this_data[opts.selectedItemProp]);
								} else {
									formatted = opts.formatList.call(this, this_data, formatted);	
								}
								results_list.append(formatted);
								delete this_data;
								matchCount++;
								if(opts.retrieveLimit && opts.retrieveLimit == matchCount && !opts.showScrollbar){ break; }
							}
						}
					}
					input_holder.removeClass("loading");
					toggleResultsList('show');
					if(matchCount == 0){
						results_list.css("overflow-y", "");
						results_list.css("height", "");
						if (opts.emptyText) {
							results_list.html('<li class="as-message">'+opts.emptyText+'</li>');
							input.focus();
						} else {
							toggleResultsList('hide');
						}
					} else {
						results_list.css("width", input.outerWidth());
						
						if (opts.showScrollbar && opts.retrieveLimit && opts.retrieveLimit <= matchCount) {
							var line_height = "15px"; //results_list.css("line-height");
							var maxHeight;
							if(opts.retrieveLimit && opts.retrieveLimit > 0){
								var maxHeight = line_height.replace('px','')*opts.retrieveLimit;
							}
							results_list.css("overflow-y", "scroll");
							results_list.css("height", maxHeight+"px");
							// Catch scroll event
							results_list.scroll(function() {
								// Still needed?
							});
						} else {
							results_list.css("overflow-y", "");
							results_list.css("height", "");
						}
						input.focus();
					}
					opts.resultsComplete.call(this);
				}
				
				function add_selected_item(data, num){
					input.val(data[opts.selectedValuesProp]);
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
							var listitem_height = results_list.css("line-height").replace('px','');
							results_list.scrollTop((listitem_number-1)*listitem_height);
						}
					}
				}
									
			});
		}
	};
})(jQuery);  	