package com.masai.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.masai.model.Theatre;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

	private List<Theatre> theatreList = new ArrayList<Theatre>();
	private LocalDate date;
	private Integer availability;
	
}
