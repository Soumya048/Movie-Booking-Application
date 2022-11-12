package com.masai.service;

import java.util.List;

import com.masai.dto.LoginDTO;
import com.masai.dto.MovieDTO;
import com.masai.exception.EmailException;
import com.masai.exception.LogInException;
import com.masai.exception.MovieException;
import com.masai.exception.OrderException;
import com.masai.exception.SeatExistException;
import com.masai.exception.TheatreException;
import com.masai.exception.TicketException;
import com.masai.exception.UserException;
import com.masai.model.Movie;
import com.masai.model.Order;
import com.masai.model.Theatre;
import com.masai.model.User;
import com.masai.model.UserSession;

public interface UserService {

	public User registerUser(User user) throws UserException;                                                     
	public UserSession loginUser(LoginDTO user) throws LogInException, UserException ;
	public String logoutUser(String key) throws UserException;
	public User updateUser(User user, String key) throws UserException, LogInException;                                          
	public User deleteUser(String key) throws UserException, LogInException;  
	
	public List<Theatre> findTheatresByNameCityPin(String name, String city, String pin, String key) throws TheatreException, LogInException;
	public List<MovieDTO> findMoviesByNameYear(String name, String year, String key) throws MovieException, LogInException;
	public List<Movie> findMoviesByTickets(String key) throws LogInException, MovieException;
	public List<Movie> findMoviesByDate(String date, String key) throws LogInException, MovieException; 
	public List<Theatre> findTheatresByMovie(String movieName, String key) throws LogInException, MovieException, TheatreException;
	public List<Movie> findMoviesByActor(String actor, String key)  throws LogInException, MovieException ;
	public List<List<Movie>> SortByRating(String key) throws LogInException, MovieException;
	public List<Theatre> SortTheatreByPirce(String key) throws LogInException, TheatreException;
	public List<List<Theatre>> sortTheatreByAvailablity(String key) throws LogInException, TheatreException;
	
	// no key require
	public Movie getMovieById(Integer movieId) throws MovieException;
	public List<Theatre> getTheatreByMovieId(Integer movieId) throws TheatreException, MovieException;
	
	public Order bookMovies(Integer noOfseats, Integer movieId, Integer theatreId, String key) throws EmailException, OrderException, TicketException, LogInException, TheatreException, MovieException, SeatExistException; 
	
}
