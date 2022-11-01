package com.masai.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
	
	private String orderId;
	
	private Integer movieId;
	
	private Integer theatreId;
	
	private String movieName;
	
	private String theatreName;
	
	private Integer noOfSeats;
	
	private String seatNumbers;
	
	private LocalDateTime bookedTime = LocalDateTime.now();
	
	private String movieTime;
	
	private Double totalPrice;
	
}
