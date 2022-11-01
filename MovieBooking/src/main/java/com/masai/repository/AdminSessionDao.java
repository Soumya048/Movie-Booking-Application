package com.masai.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.masai.model.AdminSession;

@Service
public interface AdminSessionDao extends JpaRepository<AdminSession, Integer> {

	public Optional<AdminSession> findByAdminId(Integer adminId);
	public Optional<AdminSession> findByUuId(String uuid);
	
}
