package com.masai.service;


import java.util.List;
import java.util.Map;

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

public interface AdminService {
	
	public Admin adminRegister(Admin admin) throws AdminException;
	public AdminSession adminLogin(LoginDTO loginDto) throws LogInException;
	public Admin updateAdmin(Admin admin, String key) throws LogInException, AdminException ;
	public Admin deleteAdminById(String key) throws LogInException, AdminException;
	public String logoutAdmin(String key) throws LogInException;
	
	public Theatre insertTheatre(Theatre theatre, String key) throws TheatreException, LogInException;
	public Movie insertMovies(Movie movie, String key) throws LogInException, MovieException, InvalidUrlException;
	public MovieTheatreDTO addMoviesToTheatre(Integer movieId, Integer theatreId, String key) throws LogInException, TheatreException, MovieException, SeatExistException; 
	public Map<String, Object> getAllTheatresByPageAndSize(Integer page, Integer size, String key) throws TheatreException, LogInException;
}
