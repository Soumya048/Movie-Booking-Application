package com.masai.controller;

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
import com.masai.dto.MovieTheatreDTO;
import com.masai.exception.AdminException;
import com.masai.exception.InvalidUrlException;
import com.masai.exception.LogInException;
import com.masai.exception.MovieException;
import com.masai.exception.SeatExistException;
import com.masai.exception.TheatreException;
import com.masai.model.Admin;
import com.masai.model.AdminSession;
import com.masai.model.Movie;
import com.masai.model.Theatre;
import com.masai.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	

	@PostMapping("/register")
	public ResponseEntity<Admin> registerAdminHandler(@Valid @RequestBody Admin admin) throws AdminException {
		Admin registered = adminService.adminRegister(admin);
		return new  ResponseEntity<Admin>(registered, HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<AdminSession> loginAdminHandler(@RequestBody LoginDTO loginDto) throws LogInException {
		AdminSession currentSession = adminService.adminLogin(loginDto);
		return new ResponseEntity<AdminSession>(currentSession, HttpStatus.CREATED);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logoutAdminHandler(@RequestParam String key) throws LogInException {
		String logOutCurrentSession = adminService.logoutAdmin(key);
		return new ResponseEntity<String>(logOutCurrentSession, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Admin> updateAdminHandler(@Valid @RequestBody Admin admin, @PathVariable String username, @RequestParam String key ) throws LogInException, AdminException {
		Admin updatedAdmin = adminService.updateAdmin(admin, key);
		return new ResponseEntity<Admin>(updatedAdmin, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Admin> deleteAdminByIdHandler(@PathVariable Integer adminId, @RequestParam String key) throws LogInException, AdminException {
		Admin deletedAdmin = adminService.deleteAdminById(key);
		return new ResponseEntity<Admin>(deletedAdmin, HttpStatus.OK);
	}
	
	@PostMapping("/theatre")
	public ResponseEntity<Theatre> insertTheatreHandler(@Valid @RequestBody Theatre theatre, @RequestParam String key) throws TheatreException, LogInException  {
		Theatre insertedTheatre = adminService.insertTheatre(theatre, key);
		return new  ResponseEntity<Theatre>(insertedTheatre, HttpStatus.CREATED);
	}
	
	@PostMapping("/movie")
	public ResponseEntity<Movie> insertMovieHandler(@Valid @RequestBody Movie movie, @RequestParam String key) throws LogInException, MovieException, InvalidUrlException   {
		Movie insertedMovie = adminService.insertMovies(movie, key);
		return new ResponseEntity<Movie>(insertedMovie, HttpStatus.CREATED);
	}
	
	@PatchMapping("/movie/{movieId}/{theatreId}")
	public ResponseEntity<MovieTheatreDTO> addMovieToTheatreHandler(@PathVariable Integer movieId, @PathVariable Integer theatreId, @RequestParam String key) throws LogInException, MovieException, TheatreException, SeatExistException   {
		MovieTheatreDTO movieTheatreData = adminService.addMoviesToTheatre(movieId, theatreId, key);
		return new ResponseEntity<MovieTheatreDTO>(movieTheatreData, HttpStatus.OK);
	}
	
	

}
