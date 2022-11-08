package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seats {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer seatId;
	
	@NotNull
	private Integer theatreId;
	
	@NotNull
	private Integer movieId;
	
	@JsonIgnore
	@ElementCollection
	private List<String> seats = new ArrayList<String>(); 
	
	@JsonIgnore
	private Integer totalTickets = 49;  

}
