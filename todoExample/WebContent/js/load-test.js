$LAB
.setOptions({AlwaysPreserveOrder:true})
.script("http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js")
.script("/js/ext/base64.js")
.script("/js/ext/utf8.js")
.script("/js/ext/sha256.js")
.script("/js/ext/aes.js")
.script("/js/ext/aes-ctr.js")
.wait(function() {
	$(document).ready(function() {
		// Your dom ready stuff
		var userName = 'gerton.tenham@yahoo.com';
		var userToken;
		var serviceToken;
		var serviceRequest;

		$('#login').click(function() {
			var inputUserName = $('#username').val();
			var inputPwd = $('#password').val();
			$('#error').hide("fast");
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
							showError("Authentication error! Please try again.");
						}
				});
				return false;
			});
			// Override default behaviour form submit
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
			$('#error').hide("fast");
			$('form').show("fast");
		}
		
		function showError(message) {
			$('#error').html(message).show("fast").fadeOut(10000);
		}
		
		$('#authTest').click(function() {
			serviceRequest = Aes.Ctr.encrypt("welcome:"+serviceToken, userToken ,256);
			serviceRequest = userName+':'+serviceRequest;
			$.ajax({
				url: '/auth-proxy/was/users',
				type: 'GET',
				data: 'username='+userName,
				beforeSend: function( xhr ) {
					xhr.setRequestHeader("Was-Proxy-Authorization", serviceRequest);
				},
				success: function(data) {
							console.log('Load was performed.');
	  					},
				error: function(jqXHR, textStatus, errorThrown) {
							if (jqXHR.status == 407) {
								showLogin();
								showError("Authentication required! Please login.");
							}
							
						}
			});
		});
	});
});