package com.prowings.productmgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid value to one of the parameter in request!!")
public class ProductValidationException extends RuntimeException{

	public ProductValidationException() {
		super();
	}

	public ProductValidationException(String message) {
		super(message);
	}

	
	

}
