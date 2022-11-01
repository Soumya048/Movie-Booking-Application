package com.masai.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.masai.model.Movie;
import com.masai.model.Theatre;

@Service
public interface MovieDao extends JpaRepository<Movie, Integer>  {


	Optional<Movie> findByMovieName(String moveName);
	
	@Query("SELECT a from com.masai.model.Movie a WHERE year(a.dateOfRelease) like ?1")
	List<Movie> findByReleaseYear(Integer year);
	
	List<Movie> findByDateOfRelease(LocalDate date);
	
	@Query("SELECT a.movieList FROM com.masai.model.Theatre a ORDER BY a.rating DESC")
	List<List<Movie>> findAllMovieSortByRating();
	
	@Query("SELECT a.theatreList FROM com.masai.model.Movie a ORDER BY a.totalTickets DESC")
	List<List<Theatre>> findAllTheatresSortByAvailable();
	
}

