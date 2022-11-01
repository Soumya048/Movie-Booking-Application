package com.masai.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.masai.exception.LogInException;
import com.masai.exception.OrderException;
import com.masai.model.Order;
import com.masai.model.User;
import com.masai.model.UserSession;
import com.masai.repository.UserDao;
import com.masai.repository.UserSessionDao;

@Service
public class PDFGeneratorService {

	@Autowired
	private UserSessionDao userSessionDao;
	
	@Autowired
	private UserDao userDao;
	
	
	public void pdfExport(HttpServletResponse response, String orderId, String key ) throws IOException, LogInException, OrderException {
		
		Optional<UserSession> usOpt = userSessionDao.findByUuId(key);
		
		if (usOpt.isEmpty())
			throw new LogInException("User is not logged in, Please login first!");
		
		
		Optional<User> userOpt = userDao.findById(usOpt.get().getUserId());
		
		User user = userOpt.get();
		
		List<Order> userOrderList = user.getOrders();
		
		if(userOrderList.isEmpty()) 
			throw new OrderException("Order not found");
		
		Order order = null;
		
		for(Order ord:userOrderList) {
			if(ord.getOrderId().equals(orderId)) {
				order = ord;
				break;
			}
		}
		
		if(order == null) 
			throw new OrderException("Not order found with the orderId: " + orderId) ;
		
		Document document = new Document(PageSize.A4);
		
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(18);
		
		Paragraph paragraph = new Paragraph("Movie Booking Deatils" + "\n" + "\n", fontTitle);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		
		Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
		fontParagraph.setSize(12);
		
		
		String orderDeatils = "OrderId: " + order.getOrderId() + "\n" 
							+ "Movie: " + order.getMovieName() + "\n"
							+ "Theatre: " + order.getTheatreName() + "\n"
							+ "Number of Seats: " + order.getNoOfSeats() + "\n"
							+ "Seat Number: " + order.getSeatNumbers() + "\n"
							+ "Show time: " + order.getMovieTime() + "\n"
							+ "Total Price: " + order.getTotalPrice() + "\n"
							+"\n"
							+"\n"
							+ "Thank You For Booking";
		
		
		Paragraph bodySection = new Paragraph(orderDeatils, fontParagraph);
		bodySection.setAlignment(Paragraph.ALIGN_LEFT);
		
		document.add(paragraph);
		document.add(bodySection);
		document.close();
		
	}
	
	
}
