package com.masai.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer ticketId;

	@NotNull
	private String category;
	
	@NotNull
	private String rowAndColumn;
	
	@NotNull
	private Integer theatreId;
	
	@NotNull
	private Integer userId;
	
	@NotNull
	private Integer noOfSeats;
	
	@NotNull
	private Double totalPrice;
	
}
