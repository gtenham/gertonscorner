package com.wordpress.gertonscorner.security.aspects;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;

import com.wordpress.gertonscorner.security.annotations.Authentication;
import com.wordpress.gertonscorner.security.services.IAuthenticationService;

@Aspect
public class SecurityAspect {

	@Autowired
	private IAuthenticationService authenticationService;
	
	@Pointcut(value="execution(public * *(..))")
	public void anyPublicMethod() {
	}
	
	@Around("anyPublicMethod() && @annotation(authentication)")
	public Object checkAuthentication(ProceedingJoinPoint pjp, Authentication authentication) throws Throwable {
		String wasProxyAuthorization = null;
		String remoteAddress = null;
		
		// Check annotation type
		String proxyName = authentication.proxyName();
		Boolean remoteAddressOnly = Boolean.valueOf(authentication.remoteAddressOnly());
		String serviceName = authentication.serviceName();
		
		if (proxyName.equalsIgnoreCase("was-proxy")) {
			// retrieve the methods parameter types (static):
			HttpServletRequest request = null; 
	
			// Check the join point arguments
			for ( Object object : pjp.getArgs()) {
				if (object instanceof HttpServletRequest) {
					request = (HttpServletRequest) object;
					remoteAddress = request.getRemoteAddr();
					wasProxyAuthorization = request.getHeader("Was-Proxy-Authorization");
					
					break;
				}
			}
			authenticationService.validateAuthentication(wasProxyAuthorization, remoteAddress, serviceName, remoteAddressOnly);
		}
		return pjp.proceed();
		//TODO: Log some audit information on successfull authorization
	}
}
