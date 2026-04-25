package com.prowings.productmgmt.interceptor;

import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingInteceptor implements HandlerInterceptor {

	private static final String START_TIME = "startTime";

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		long startTime = System.currentTimeMillis();
		request.setAttribute(START_TIME, startTime);

		log.info("----- START method={} uri={} query={}", request.getMethod(), request.getRequestURI(),
				request.getQueryString());

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		long startTime = (long) request.getAttribute(START_TIME);
		long duration = System.currentTimeMillis() - startTime;

		log.info("----- END status={} duration={}ms", response.getStatus(), duration);

		if (ex != null) {
			log.error("ERROR occurred", ex);
		}
	}

}
