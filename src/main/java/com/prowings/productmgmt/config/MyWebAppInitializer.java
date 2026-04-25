package com.prowings.productmgmt.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import com.prowings.productmgmt.interceptor.LoggingFilter;

import jakarta.servlet.Filter;

//this class is replacement of web.xml

public class MyWebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		
		Class[] servletClasses = {WebConfig.class};
		return servletClasses;
	}

	@Override
	protected String[] getServletMappings() {
		String[] mappings = {"/"};
		return mappings;
	}

	@Override
	protected Filter[] getServletFilters() {

	    return new Filter[] {
	            new LoggingFilter()
	    };
	}

}
