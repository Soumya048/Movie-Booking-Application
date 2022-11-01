package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.masai.model.UserSession;

@Service
public interface UserSessionDao extends JpaRepository<UserSession, Integer> {

	public Optional<UserSession> findByUuId(String uuId);
	public Optional<UserSession> findByUserId(Integer userId);
	
}
