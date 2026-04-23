package com.prowings.productmgmt.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.prowings.productmgmt.model.CustomErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

//	@Override
//    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
//            HttpRequestMethodNotSupportedException ex, 
//            HttpHeaders headers, 
//            HttpStatusCode status, 
//            WebRequest request) {
//        
//        // 1. Logic to get the unsupported method (e.g., POST)
//        String unsupportedMethod = ex.getMethod();
//        
//        // 2. Logic to get supported methods (e.g., [GET, DELETE])
//        StringBuilder supportedMethods = new StringBuilder();
//        ex.getSupportedHttpMethods().forEach(m -> supportedMethods.append(m).append(" "));
//
//        String message = String.format("The HTTP method '%s' is not supported for this endpoint. Supported methods are: %s", 
//                                        unsupportedMethod, supportedMethods.toString().trim());
//
//        // 3. Construct your custom ApiError DTO
//        CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), message, LocalDateTime.now());
//
//		return new ResponseEntity<>(errorResponse, HttpStatus.METHOD_NOT_ALLOWED);
//    }
	
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public ResponseEntity<CustomErrorResponse> handleMethodArgumentNotValidException(
//			MethodArgumentNotValidException e) {
//
//		System.out.println("Validation Error stacktrace :---------- ");
//		e.printStackTrace();
//		System.out.println("--------------------------------------- ");
//
////		String error = e.getBindingResult().getFieldError().getDefaultMessage();
//		Map<String, String> errors = new HashMap();
//
//		// Loop through all the errors Spring found and put them in our Map
//		e.getBindingResult().getAllErrors().forEach((error) -> {
//			String fieldName = ((FieldError) error).getField();
//			String errorMessage = error.getDefaultMessage();
//			errors.put(fieldName, errorMessage);
//		});
//
//		CustomErrorResponse errorResponse = new CustomErrorResponse(400, "Field validation errors!!",
//				LocalDateTime.now(), errors);
//
//		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
//	}

//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<CustomErrorResponse> handleRuntimeException(Exception e) {
//		CustomErrorResponse errorResponse = new CustomErrorResponse(404, e.getMessage(), LocalDateTime.now());
//
//		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
//	}

	@ExceptionHandler(ProductValidationException.class)
	public ResponseEntity<CustomErrorResponse> handleValidationException(ProductValidationException e) {
		CustomErrorResponse errorResponse = new CustomErrorResponse(400, e.getMessage(), LocalDateTime.now());

		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
