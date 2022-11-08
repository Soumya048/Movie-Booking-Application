package com.masai.dto;

import com.masai.model.Movie;
import com.masai.model.Theatre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieTheatreDTO {

	private Movie movieDetails;
	
	private Theatre theatreDetails;
	
	private Integer totalTickets;  

}
