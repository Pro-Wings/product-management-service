package com.prowings.productmgmt.model;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomErrorResponse {

	private int status;
	private String message;
	private LocalDateTime timestamp;
	// New field to hold validation errors
	private Map<String, String> fieldErrors;

	public CustomErrorResponse(int status, String message, LocalDateTime timestamp) {
		super();
		this.status = status;
		this.message = message;
		this.timestamp = timestamp;
	}

}
