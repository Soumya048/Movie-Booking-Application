package com.masai.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.TypeDef;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer movieId;
	
	@NotNull
	private String movieName;
	
	@ElementCollection
	private List<String> cast = new ArrayList<String>();

	@NotNull
	private String genre;
	
	@NotNull
	private String duration;
	
	@NotNull
	private String quality;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate dateOfRelease;
	
	@JsonIgnore
	@ElementCollection
	private List<String> seats = new ArrayList<String>(); 
	
	@JsonIgnore
	private Integer totalTickets = 49;  
	
	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Theatre> theatreList = new ArrayList<Theatre>();
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL)
	private List<Ticket> ticketList = new ArrayList<Ticket>();
	
	
}
