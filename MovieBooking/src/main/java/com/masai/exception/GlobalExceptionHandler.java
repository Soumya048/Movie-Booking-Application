package com.masai.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(InvalidUrlException.class)
	public ResponseEntity<ErrorDetails> invalidUrlExceptionHandler(InvalidUrlException ie, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(ie.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(SeatExistException.class)
	public ResponseEntity<ErrorDetails> seatExistExceptionHandler(SeatExistException se, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(se.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<ErrorDetails> emailExceptionHandler(EmailException ee, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(ee.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TicketException.class)
	public ResponseEntity<ErrorDetails> ticketExceptionHandler(TicketException te, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(te.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(OrderException.class)
	public ResponseEntity<ErrorDetails> orderExceptionHandler(OrderException oe, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(oe.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(MovieException.class)
	public ResponseEntity<ErrorDetails> movieExceptionHandler(MovieException me, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(me.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(TheatreException.class)
	public ResponseEntity<ErrorDetails> theatreExceptionHandler(TheatreException te, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(te.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(AdminException.class)
	public ResponseEntity<ErrorDetails> adminExceptionHandler(AdminException ae, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(ae.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LogInException.class)
	public ResponseEntity<ErrorDetails> logInExceptionHandler(LogInException le, WebRequest req) {
		ErrorDetails err = new ErrorDetails();
		
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(le.getMessage());
		err.setDetails(req.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
	
	
	// No handler found exception
	@ExceptionHandler(NoHandlerFoundException.class)
	public ResponseEntity<ErrorDetails> notFoundExceptionHandler(NoHandlerFoundException nfe, WebRequest req ){	
		ErrorDetails err = new ErrorDetails();
			
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(nfe.getMessage());
		err.setDetails(req.getDescription(false));
			
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
		
	//wrong uri exception	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorDetails> MANVExceptionHandler(MethodArgumentNotValidException me) {
		ErrorDetails err = new ErrorDetails();
			
		err.setTimestamp(LocalDateTime.now());
		err.setMessage("Validation Error");
		err.setDetails(me.getBindingResult().getFieldError().getDefaultMessage());
			
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.BAD_REQUEST);
	}
		
	//for other exception
	@ExceptionHandler(Exception.class) 
	public ResponseEntity<ErrorDetails> otherExceptionHandler(Exception e, WebRequest req) {	
		ErrorDetails err = new ErrorDetails();
			
		err.setTimestamp(LocalDateTime.now());
		err.setMessage(e.getMessage());
		err.setDetails(req.getDescription(false));
			
		return new ResponseEntity<ErrorDetails>(err, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	
}
