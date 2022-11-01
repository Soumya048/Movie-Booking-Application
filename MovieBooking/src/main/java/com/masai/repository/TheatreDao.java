package com.masai.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.masai.model.Movie;
import com.masai.model.Theatre;

@Service
public interface TheatreDao extends JpaRepository<Theatre, Integer> {

	Optional<Theatre> findByName(String name);
	List<Theatre> findByCity(String city);
	List<Theatre> findByPincode(String pincode);
	
	List<Theatre> findAllByOrderByPriceDesc();
	
}
