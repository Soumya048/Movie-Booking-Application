package com.masai.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.masai.model.Ticket;

@Service 
public interface TicketDao extends JpaRepository<Ticket, Integer> {

	
	
}
