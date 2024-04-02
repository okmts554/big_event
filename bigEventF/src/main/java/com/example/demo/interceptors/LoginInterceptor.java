package com.example.demo.interceptors;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.util.JwtUtil;
import com.example.demo.util.ThreadLocalUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		String token = request.getHeader("Authorization");
		
		try {
			Map<String,Object> claims = JwtUtil.parseToken(token);
			ThreadLocalUtil.set(claims);
			return true;
		} catch (Exception e) {
			response.setStatus(401);
			return false;
		}
	}
	
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception{
		ThreadLocalUtil.remove();
	}

}
