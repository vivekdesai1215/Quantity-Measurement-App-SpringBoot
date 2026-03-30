package com.app.quantitymeasurement.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		List<ObjectError> errorList = ex.getBindingResult().getAllErrors();
		List<String> errMsg = errorList.stream().map(objErr-> objErr.getDefaultMessage())
			.collect(Collectors.toList());
		
		ErrorResponse error = new ErrorResponse();
		
		error.timeStamp = LocalDateTime.now();
		error.status = HttpStatus.BAD_REQUEST.value();
		error.error = "Quantity Measurement Error";
		error.message = String.join("; ", errMsg);
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);		
	}
	
	@ExceptionHandler(QuantityMeasurementException.class)
	public ResponseEntity<ErrorResponse> handleQuantityException(QuantityMeasurementException  ex,WebRequest request){
		
		ErrorResponse error = new ErrorResponse();
		
		error.timeStamp = LocalDateTime.now();
		error.status = HttpStatus.BAD_REQUEST.value();
		error.error = "Quantity Measurement Error";
		error.message = ex.getMessage();
		error.path = request.getDescription(false).replace("url=", "");
		
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleException(Exception ex,WebRequest request){
		
		ErrorResponse error = new ErrorResponse();
		
		error.timeStamp = LocalDateTime.now();
		error.status = HttpStatus.INTERNAL_SERVER_ERROR.value();
		error.error = "Internal Server Error";
		error.message = ex.getMessage();
		error.path = request.getDescription(false).replace("url=", "");
		
		return new ResponseEntity<>(error,HttpStatus.INTERNAL_SERVER_ERROR);		
	}
	
}
