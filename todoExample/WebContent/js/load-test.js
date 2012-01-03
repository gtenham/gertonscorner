$LAB
.setOptions({AlwaysPreserveOrder:true})
.script("http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js")
.script("http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js")
.script("/js/ext/base64.js")
.script("/js/ext/utf8.js")
.script("/js/ext/sha256.js")
.script("/js/ext/aes.js")
.script("/js/ext/aes-ctr.js")
.wait(function() {
	$(document).ready(function() {
		// TODO: Should be saved into session container!
		var userToken;
		var serviceToken;
		var serviceRequest;
		// Variables holding user input
		var inputUserName;
		var inputPwd;
		
		$('#login').click(function() {
			inputUserName = $('#username').val();
			inputPwd = $('#password').val();
			$('#message').hide("fast");
			$.get("/auth-proxy/was/authentication/token?username="+inputUserName, function(data) {
				hashedPwd = Sha256.hash(inputPwd);
				
				serverStr = Aes.Ctr.decrypt(data, hashedPwd ,256);
				userToken = serverStr;

	    		// Encrypt a new service request ticket
				requestForService = Aes.Ctr.encrypt("users", serverStr ,256);

				$.ajax({
					url: '/auth-proxy/was/authentication/serviceticket',
					type: 'GET',
					data: 'username='+inputUserName+'&servicename='+requestForService,
					success: function(data) {
						serviceToken = data;
						showFullName(serviceToken, userToken, inputUserName);
	  				},
					error: function(jqXHR, textStatus, errorThrown) {
						showMessage("Authentication error! Please try again.");
					}
				});
				return false;
			});
			// Override default behaviour form submit
			return false;
		});

		$('#authTest').click(function() {
			try{
				serviceRequest = Aes.Ctr.encrypt("welcome:"+serviceToken, userToken ,256);
				serviceRequest = inputUserName+':'+serviceRequest;
				
				$.ajax({
					url: '/auth-proxy/was/users',
					type: 'GET',
					data: 'username='+inputUserName,
					beforeSend: function( xhr ) {
						xhr.setRequestHeader("Was-Proxy-Authorization", serviceRequest);
					},
					success: function(data) {
						showMessage("Authenticated call returned: "+data);
		  			},
					error: function(jqXHR, textStatus, errorThrown) {
						if (jqXHR.status == 407) {
							showLogin();
							showMessage("Authentication required! Please login.");
						}
								
					}
				});
			} catch(e) {
				console.log(e.message);
				
			}
			
			return false;
		});
		
		$('#logout').click(function() {
			try{
				serviceRequest = Aes.Ctr.encrypt("logout:"+serviceToken, userToken ,256);
				serviceRequest = inputUserName+':'+serviceRequest;
				
				$.ajax({
					url: '/auth-proxy/was/authentication/serviceticket?username='+inputUserName,
					type: 'DELETE',
					beforeSend: function( xhr ) {
						xhr.setRequestHeader("Was-Proxy-Authorization", serviceRequest);
					},
					success: function(data) {
						showMessage("Logout succesfull!");
		  			},
					error: function(jqXHR, textStatus, errorThrown) {
						if (jqXHR.status == 407) {
							showLogin();
							showMessage("Logout was succesfull.");
						}
								
					}
				});
			} catch(e) {
				console.log(e.message);
				
			}
			
			return false;
		});
		
		function showFullName(sToken, uToken, uName) {
			serviceRequest = Aes.Ctr.encrypt("welcome:"+sToken, uToken ,256);
			serviceRequest = uName+':'+serviceRequest;
			
			$.ajax({
				url: '/auth-proxy/was/users',
				type: 'GET',
				data: 'username='+uName,
				beforeSend: function( xhr ) {
					xhr.setRequestHeader("Was-Proxy-Authorization", serviceRequest);
				},
				success: function(data) {
					$('form').hide("fast", function() {
					    $('#success').html('Welcome '+ data + '<br/>You are succesfully authenticated').show();
					});
	  			},
				error: function(jqXHR, textStatus, errorThrown) {
					console.log(textStatus + ' : ' + errorThrown);
				}
			});
		}

		function showLogin() {
			$('#success').hide("fast");
			$('#message').hide("fast");
			$('form').show("fast");
		}
		
		function showMessage(message) {
			$('#message').html(message).show("fast").fadeOut(10000);
		}
	});
});