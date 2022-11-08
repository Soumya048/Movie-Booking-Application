package com.masai.controller;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.masai.service.PDFGeneratorService;
import com.masai.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PDFGeneratorService pdfGeneratorService;

	@PostMapping("/register")
	public ResponseEntity<User> registerUser(@RequestBody User user) throws UserException {
		User newUser = userService.registerUser(user);
		return  new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<UserSession> loginUserHandler(@RequestBody LoginDTO loginDto) throws LogInException, UserException {
		UserSession currentSession = userService.loginUser(loginDto);
		return new ResponseEntity<UserSession>(currentSession, HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> logoutUserHandler(@RequestParam String key) throws UserException {
		String message = userService.logoutUser(key);
		return new ResponseEntity<String>(message, HttpStatus.ACCEPTED);
	}

	@PutMapping("/update")
	public ResponseEntity<User> updateUserHandler(@Valid @RequestBody User user, @RequestParam String key) throws LogInException, UserException {
		User updateUser = userService.updateUser(user, key);
		return new ResponseEntity<User>(updateUser, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<User> deleteUserByIdHandler(@RequestParam String key) throws UserException, LogInException{
		User deletedUser = userService.deleteUser(key);
		return new ResponseEntity<User>(deletedUser, HttpStatus.OK);
	}
	
	@GetMapping("/theatre")
	public ResponseEntity<List<Theatre>> findTheatreHandler(@RequestParam(required = false) String name, @RequestParam(required = false) String city, @RequestParam(required = false)  String pin, @RequestParam String key) throws TheatreException, LogInException  {
		List<Theatre> foundTheatre = userService.findTheatresByNameCityPin(name, city, pin, key);
		return new  ResponseEntity<List<Theatre>>(foundTheatre, HttpStatus.OK);
	}
	
	
	@GetMapping("/movie")
	public ResponseEntity<List<MovieDTO>> findMoviesByNameYearHandler(@RequestParam(required = false) String name, @RequestParam(required = false) String year, @RequestParam String key) throws LogInException, MovieException  {
		List<MovieDTO> foundMovie = userService.findMoviesByNameYear(name, year, key);
		return new ResponseEntity<List<MovieDTO>>(foundMovie, HttpStatus.OK);
	}
	
	@GetMapping("/movie/tickets")
	public ResponseEntity<List<Movie>> findMoviesByTicketsHandler(@RequestParam String key) throws LogInException, MovieException  {
		List<Movie> foundMovies = userService.findMoviesByTickets(key);
		return new ResponseEntity<List<Movie>>(foundMovies, HttpStatus.OK);
	}
	
	@GetMapping("/movie/{date}")
	public ResponseEntity<List<Movie>> findMoviesByDateHandler(@PathVariable String date, @RequestParam String key) throws LogInException, MovieException  {
		List<Movie> foundMovies = userService.findMoviesByDate(date, key);
		return new ResponseEntity<List<Movie>>(foundMovies, HttpStatus.OK);
	}
	
	@GetMapping("/theatre/{movieName}")
	public ResponseEntity<List<Theatre>> findTheatresByMovieNameHandler(@PathVariable String movieName, @RequestParam String key) throws LogInException, MovieException, TheatreException  {
		List<Theatre> foundTheatres = userService.findTheatresByMovie(movieName, key);
		return new ResponseEntity<List<Theatre>>(foundTheatres, HttpStatus.OK);
	}
	
	@GetMapping("/movie/actor/{actor}")
	public ResponseEntity<List<Movie>> findMoviesByActorHandler(@PathVariable String actor, @RequestParam String key) throws LogInException, MovieException {
		List<Movie> foundMovies = userService.findMoviesByActor(actor, key);
		return new ResponseEntity<List<Movie>>(foundMovies, HttpStatus.OK);
	}
	
	@GetMapping("/movie/sort/rating")
	public ResponseEntity<List<List<Movie>>> findAllMoviesSortByRatingHandler(@RequestParam String key) throws LogInException, MovieException {
		List<List<Movie>> foundMovies = userService.SortByRating(key);
		return new ResponseEntity<List<List<Movie>>>(foundMovies, HttpStatus.OK);
	}
	
	@GetMapping("/theatre/sort/price")
	public ResponseEntity<List<Theatre>> findAllTheatreSortByPriceHandler(@RequestParam String key) throws LogInException, TheatreException {
		List<Theatre> foundTheatres = userService.SortTheatreByPirce(key);
		return new ResponseEntity<List<Theatre>>(foundTheatres, HttpStatus.OK);
	}
	
	@GetMapping("/theatre/sort/availability")
	public ResponseEntity<List<List<Theatre>>> findAllTheatreSortByAvailabilityHandler(@RequestParam String key) throws LogInException, TheatreException {
		List<List<Theatre>> foundTheatres = userService.sortTheatreByAvailablity(key);
		return new ResponseEntity<List<List<Theatre>>>(foundTheatres, HttpStatus.OK);
	}
	
	@PatchMapping("/bookMovie/{noOfseats}/{movieId}/{theatreId}")
	public ResponseEntity<Order> bookMovieHandler(@PathVariable Integer noOfseats, @PathVariable Integer movieId, @PathVariable Integer theatreId, @RequestParam String key) throws OrderException, TicketException, LogInException, TheatreException, MovieException, EmailException, SeatExistException {
		Order booked = userService.bookMovies( noOfseats, movieId, theatreId, key);
		return new ResponseEntity<Order>(booked, HttpStatus.OK);
	}
	
	@GetMapping("/generate/pdf/{orderId}")
	public void generateOrderDetilsPDF(HttpServletResponse response, @PathVariable String orderId, @RequestParam String key) throws IOException, LogInException, OrderException {
		
		response.setContentType("application/pdf");
		String currentDateTime = LocalDateTime.now().toString();
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=" + orderId + "_" + currentDateTime + ".pdf";
		response.setHeader(headerKey, headerValue);
		
		pdfGeneratorService.pdfExport(response, orderId, key);
	}
	

}
