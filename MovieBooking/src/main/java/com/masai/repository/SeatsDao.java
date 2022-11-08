package com.masai.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.masai.model.Movie;
import com.masai.model.Seats;

public interface SeatsDao extends JpaRepository<Seats, Integer> {

	
	@Query("FROM com.masai.model.Movie b WHERE b.movieId IN(SELECT DISTINCT a.movieId FROM com.masai.model.Seats a WHERE a.totalTickets > 0 ORDER BY a.totalTickets DESC)")
	public List<Movie> findMoviesBySeatsAvailability();
	
	@Query("FROM com.masai.model.Seats a WHERE a.movieId = ?1 AND a.theatreId = ?2")
	public Optional<Seats> findSeatsByMovieIdAndTheatreId(Integer movieId, Integer theatreId);
	
}
