package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.masai.model.User;

@Service
public interface UserDao extends JpaRepository<User, Integer> {
	
	Optional<User> findByAbstractUserMobile(String mobile);

}
