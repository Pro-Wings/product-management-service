package com.prowings.productmgmt.interceptor;

import java.io.IOException;
import java.util.UUID;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoggingFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		// 🔥 Generate Trace ID
		String traceId = UUID.randomUUID().toString();
		MDC.put("traceId", traceId);

		CachedBodyHttpServletRequest wrappedRequest = new CachedBodyHttpServletRequest(req);

		CachedBodyHttpServletResponse wrappedResponse = new CachedBodyHttpServletResponse(res);
		String requestBody = wrappedRequest.getBody();
		log.info("REQUEST [{} {}] body={}", req.getMethod(), req.getRequestURI(), truncate(requestBody));

		try {
			chain.doFilter(wrappedRequest, wrappedResponse);

		} finally {

			String responseBody = wrappedResponse.getBody();
			log.info("RESPONSE status={} body={}", res.getStatus(), truncate(responseBody));

			MDC.clear();
		}
	}

	private String truncate(String body) {
		if (body == null)
			return "";
		return body.length() > 1000 ? body.substring(0, 1000) + "..." : body;
	}
}