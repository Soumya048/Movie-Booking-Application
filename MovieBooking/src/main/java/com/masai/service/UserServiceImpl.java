package com.masai.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

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
import com.masai.model.Seats;
import com.masai.model.Theatre;
import com.masai.model.Ticket;
import com.masai.model.User;
import com.masai.model.UserSession;
import com.masai.repository.MovieDao;
import com.masai.repository.SeatsDao;
import com.masai.repository.TheatreDao;
import com.masai.repository.TicketDao;
import com.masai.repository.UserDao;
import com.masai.repository.UserSessionDao;

@Service
public class UserServiceImpl implements UserService  {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserSessionDao userSessionDao;
	
	@Autowired
	private TheatreDao theatreDao;
	
	@Autowired
	private MovieDao movieDao;
	
	@Autowired
	private TicketDao ticketDao;
	
	@Autowired
	private SeatsDao seatsDao;
	
	@Autowired 
	private JavaMailSender javaMailSender;
	 
	@Value("${spring.mail.username}") private String sender;
	
	

	@Override
	public User registerUser(User user) throws UserException {
		
		Optional<User> existing = userDao.findByAbstractUserMobile(user.getAbstractUser().getMobile());

		if (existing.isPresent()) 
			throw new UserException("A Customer already exist with this mobile number in the Database");
		
		return userDao.save(user); 
		 
	}


	@Override
	public UserSession loginUser(LoginDTO user) throws LogInException, UserException {
		 
		 Optional<User> userOpt = userDao.findByAbstractUserMobile(user.getMobile());

		if (!userOpt.isPresent()) 
			throw new UserException("User does not exist with the given mobile number");

		User existingUser = userOpt.get();
		
		Optional<UserSession> usOpt = userSessionDao.findByUserId(existingUser.getUserId());

		if (usOpt.isPresent())
			throw new LogInException("User already logged in");

		if (existingUser.getAbstractUser().getPassword().equals(user.getPassword())) {

			UserSession newSession = new UserSession();

			newSession.setUserId(existingUser.getUserId());
			newSession.setUserType("User");
			newSession.setSessionStartTime(LocalDateTime.now());
			newSession.setSessionEndTime(LocalDateTime.now().plusHours(2));

			UUID uuid = UUID.randomUUID();
			String key = uuid.toString().split("-")[0];

			newSession.setUuId(key);
			

			return userSessionDao.save(newSession);
		} 
		else 
			throw new LogInException("Password Incorrect. Try again.");
		
	}

	@Override
	public String logoutUser(String key) throws UserException {
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new UserException("User is not logged in, Please login first!");

		userSessionDao.delete(usOpt.get());

		return "User has succefully logged out.";
	}

	@Override
	public User updateUser(User user, String key) throws UserException, LogInException {
		
		User update = null;
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new UserException("User is not logged in, Please login first!");
		else {
		    	 
			UserSession userSession = usOpt.get();
			Integer id = userSession.getUserId();
			
			User newData = new User();
			newData.setUserId(id);
			newData.setAbstractUser(user.getAbstractUser());
				
			update = userDao.save(newData);
			return update;
		}
	}

	@Override
	public User deleteUser(String key) throws UserException, LogInException {

		User existingCustomer = null;
	
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new UserException("User is not logged in, Please login first!");
		
		UserSession userSession = usOpt.get();
		
		Optional<User> custOtp = userDao.findById(userSession.getUserId());

		existingCustomer = custOtp.get();
		
		userDao.delete(existingCustomer);
		userSessionDao.delete(userSession);
		
		return existingCustomer;
	}


	@Override
	public List<Theatre> findTheatresByNameCityPin(String name, String city, String pin, String key) throws TheatreException, LogInException {

		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		List<Theatre> theatrelist = new ArrayList<Theatre>();
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");
		
		if(name != null) {
			
			Optional<Theatre> theatreOpt = theatreDao.findByName(name);
			
			if(theatreOpt.isPresent()) 
				theatrelist.add(theatreOpt.get());
			
			
			if(!theatrelist.isEmpty()) 
				return theatrelist;
			
		}
		
		if(city != null) {
			theatrelist = theatreDao.findByCity(city);
			
			if(!theatrelist.isEmpty()) 
				return theatrelist;
			
		}
		if(pin != null) {
			theatrelist = theatreDao.findByPincode(pin);
			
			if(!theatrelist.isEmpty()) 
				return theatrelist;
		}

		
		if(theatrelist.isEmpty()) 
			throw new TheatreException("Theatres not found");
		else
			return null;
	}


	@Override
	public List<MovieDTO> findMoviesByNameYear(String name, String year, String key) throws MovieException, LogInException {
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");
		
		List<MovieDTO> movieDtoList = new ArrayList<MovieDTO>();
		
		if(name != null) {
			
			Optional<Movie> movieOpt = movieDao.findByMovieName(name);
			
			if(movieOpt.isPresent()) {
				Movie movie = movieOpt.get();
				
				MovieDTO mvDto = new MovieDTO();
				
				mvDto.setTheatreList(movie.getTheatreList());
				mvDto.setDate(movie.getDateOfRelease());
				movieDtoList.add(mvDto);
				
				if(!movieDtoList.isEmpty())
					return movieDtoList;
			}
		}
		if(year != null) {
			
			Integer yer = Integer.parseInt(year);
			List<Movie> movieList = movieDao.findByReleaseYear(yer);
			
			if(!movieList.isEmpty()) {
				
				for(Movie movie:movieList) {
					
					MovieDTO mvDto = new MovieDTO();
					
					mvDto.setTheatreList(movie.getTheatreList());
					mvDto.setDate(movie.getDateOfRelease());
					movieDtoList.add(mvDto);
		
				}
				
				if(!movieDtoList.isEmpty())
					return movieDtoList;
			}
			
		}
		
		if(movieDtoList.isEmpty())
			throw new MovieException("No movie found with given name or year"  );
		else
			return null;
	}


	@Override
	public List<Movie> findMoviesByTickets(String key) throws LogInException, MovieException {
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");

		List<Movie> movieWithTickets = seatsDao.findMoviesBySeatsAvailability();

		if(movieWithTickets.isEmpty()) 
			throw new MovieException("Movies not found");
		
		return movieWithTickets;
	}


	@Override
	public List<Movie> findMoviesByDate(String date, String key) throws LogInException, MovieException {
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");
		
		LocalDate localDate = LocalDate.parse(date);
		
		List<Movie> moviesList = movieDao.findByDateOfRelease(localDate);
		
		if(moviesList.isEmpty()) 
			throw new MovieException("Movies not found");
		
		return moviesList;
	}


	@Override
	public List<Theatre> findTheatresByMovie(String movieName, String key) throws LogInException, MovieException, TheatreException {

		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");
		
		Optional<Movie> movieOpt = movieDao.findByMovieName(movieName);
		
		if(movieOpt.isEmpty())
			throw new MovieException("Movie not found");
		
		if(movieOpt.get().getTheatreList().isEmpty())
			throw new TheatreException("Movies not playing in any Theatres");
		
		
		return movieOpt.get().getTheatreList();
	}


	@Override
	public List<Movie> findMoviesByActor(String actor, String key) throws LogInException, MovieException {
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");
		
		List<Movie> movieList = movieDao.findAll();
		
		List<Movie> movieWithMatchedActor = new ArrayList<Movie>();
		
		for(Movie mv:movieList) {
			if(mv.getCast().contains(actor)) {
				movieWithMatchedActor.add(mv);
			}
		}
		
		if(movieWithMatchedActor.isEmpty()) 
			throw new MovieException("Movies not found");
		
		return movieWithMatchedActor;
	}


	@Override
	public List<List<Movie>> SortByRating(String key) throws LogInException, MovieException {
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");
		
		List<List<Movie>> movieList =  movieDao.findAllMovieSortByRating();
		
		if(movieList.isEmpty())
			throw new MovieException("Movies not found");
		
		return movieList;
	}


	@Override
	public List<Theatre> SortTheatreByPirce(String key) throws LogInException, TheatreException {

		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");
		
		List<Theatre> theatreList = theatreDao.findAllByOrderByPriceDesc();
		
		if(theatreList.isEmpty()) 
			throw new TheatreException("Theatres not found");
		
		return theatreList;
	}


	@Override
	public List<List<Theatre>> sortTheatreByAvailablity(String key) throws LogInException, TheatreException {
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");
		
//		movieDao.findAllTheatresSortByAvailable()
		List<List<Theatre>> theatresList = null;
		
		if(theatresList.isEmpty()) 
			throw new TheatreException("Theatres not found");
		
		return theatresList;
	}


	@Override
	public Order bookMovies(Integer noOfseats, Integer movieId, Integer theatreId, String key) throws EmailException, OrderException, LogInException, TheatreException, TicketException, MovieException, SeatExistException {
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");
		
		Optional<Movie> movieOpt = movieDao.findById(movieId);
		
		if(movieOpt.isEmpty())
			throw new MovieException("Movie not found");
		
		Optional<Theatre> theatreOpt = theatreDao.findById(theatreId);
		
		if(theatreOpt.isEmpty())
			throw new TheatreException("Thetre not found");
		
		Optional<Seats> seatsOpt = seatsDao.findSeatsByMovieIdAndTheatreId(movieId, theatreId);
		
		if(seatsOpt.isEmpty()) 
			throw new SeatExistException("Saets does not exist");
		
		Seats seats = seatsOpt.get();
		
		
		
		Optional<User> userOpt = userDao.findById(usOpt.get().getUserId());
		User user = userOpt.get();
		
		Ticket ticket = new Ticket();
		Order order = new Order();
		
		Movie movie = movieOpt.get();
		Theatre theatre = theatreOpt.get();
		
		String orderedSeatNums = "";
				
		if(seats.getTotalTickets() > noOfseats) {
			
			seats.setTotalTickets(seats.getTotalTickets() - noOfseats);
			

			String seatNo = "";
			
			String category = "";
			
			for(int i = 0; i < noOfseats; i++) {
				
				int filledSeatSize = seats.getSeats().size();
				
				if(filledSeatSize >= 0 && filledSeatSize <= 6) {
					
					if(!category.contains("A")) {
						category += "A ";
					}
					
					
					seatNo +=  "A"+(filledSeatSize + 1); 
					seats.getSeats().add(seatNo);
				}
				else if(filledSeatSize >= 7 && filledSeatSize <= 13) {
					
					if(!category.contains("B")) {
						category += "B ";
					}
					
					seatNo +=  "B"+(filledSeatSize + 1); 
					seats.getSeats().add(seatNo);
				}
				else if(filledSeatSize >= 14 && filledSeatSize <= 20) {
					
					if(!category.contains("C")) {
						category += "C ";
					}
					
					seatNo +=  "C"+(filledSeatSize + 1); 
					seats.getSeats().add(seatNo);
				}
				else if(filledSeatSize >= 21 && filledSeatSize <= 27) {
					
					if(!category.contains("D")) {
						category += "D ";
					}
					
				
					seatNo +=  "D"+(filledSeatSize + 1); 
					seats.getSeats().add(seatNo);
				}
				else if(filledSeatSize >= 28 && filledSeatSize <= 34) {
					
					if(!category.contains("E")) {
						category += "E ";
					}
				
					seatNo +=  "E"+(filledSeatSize + 1); 
					seats.getSeats().add(seatNo);
				}
				else if(filledSeatSize >= 35 && filledSeatSize <= 41) {
					
					if(!category.contains("F")) {
						category += "F ";
					}
					
					seatNo +=  "F"+(filledSeatSize + 1); 
					seats.getSeats().add(seatNo);
				}
				else if(filledSeatSize >= 42 && filledSeatSize <= 49) {
					
					if(!category.contains("G")) {
						category += "G ";
					}
					
					seatNo +=  "G"+(filledSeatSize + 1); 
					seats.getSeats().add(seatNo);
				}
				orderedSeatNums += seatNo + " ";
				seatNo = "";
		
			}
			
			System.out.println(seats.getSeats());
			System.out.println("seats: " + orderedSeatNums);
			
			
			Double totalPrice = noOfseats * theatre.getPrice();
			
			UUID randomUUID = UUID.randomUUID();
			String orderId = randomUUID.toString().replaceAll("-", "").toUpperCase();
			
			order.setOrderId(orderId);
			order.setMovieId(movieId);
			order.setTheatreId(theatreId);
			order.setMovieName(movie.getMovieName());
			order.setTheatreName(theatre.getName());
			order.setNoOfSeats(noOfseats);
			order.setSeatNumbers(orderedSeatNums);
			order.setMovieTime(movie.getDateOfRelease().plusDays(1).toString());
			order.setTotalPrice(totalPrice);

			ticket.setUserId(user.getUserId());
			ticket.setTheatreId(theatreId);
			ticket.setCategory(category);
			ticket.setRowAndColumn(orderedSeatNums);
			ticket.setTotalPrice(totalPrice);
			ticket.setNoOfSeats(noOfseats);

			
		}
		else 
			throw new OrderException("Not enough seats");
		
		user.getOrders().add(order);
		userDao.save(user);
		
		ticketDao.save(ticket);
		
		movie.getTicketList().add(ticket);
		movieDao.save(movie);
		
		seatsDao.save(seats);
		
		String messageBody = 
				"Hi," + user.getAbstractUser().getUsername()
				+ "\n"
				+ "Your ticket has been booked successfully with order id:" + order.getOrderId() + "\n"
				+ "Movie Name: " + order.getMovieName() + "\n"
				+ "Show Time: " + order.getMovieTime() + "\n"
				+ "Theatre Details: " + order.getTheatreName() + ", " + theatre.getCity() + ", " + theatre.getPincode() + "\n"
				+ "Category: " + ticket.getCategory() + "\n"
				+ "Seat Number: " + order.getSeatNumbers() + "\n"
				+ "Total Price: " + order.getTotalPrice() + "\n"
				+ "\n"
				+ "\n" 
				+ "Thank You";
		
		
		 try {
			 
			 SimpleMailMessage mailMessage = new SimpleMailMessage();
	 
			 // Setting up necessary details
			 mailMessage.setFrom(sender);
			 mailMessage.setTo(user.getAbstractUser().getEmail());
			 mailMessage.setText(messageBody);
			 mailMessage.setSubject("Movie Ticket Booked");
	 
			 System.out.println("mail");
			 javaMailSender.send(mailMessage);
		 }
		 catch (Exception e) {
			 throw new EmailException("Unable to send mail");
		 }
		
		 return order;

	}

	
}