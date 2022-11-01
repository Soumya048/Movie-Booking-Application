package com.masai.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Theatre {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer theatreId;
	
	@NotNull
	@Size(min = 3, max = 25, message = "Name should be in between 3 to 25")
	private String name;
	
	@NotNull
	@Size(min = 3, max = 25, message = "Address should be in between 3 to 25")
	private String address;
	
	@NotNull
	@Size(min = 3, max = 25, message = "City should be in between 3 to 25")
	private String city;
	
	@NotNull
	@Pattern(regexp="[0-9]{6}", message = "Pincode number must have 6 digits")
	private String pincode;
	
	@NotNull
	@Max(value = 5, message = "Maxmimum rating is 5")
	private Integer rating;
	
	@NotNull
	private Double price;
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "theatreList")
	private List<Movie> movieList = new ArrayList<Movie>();
	
}
