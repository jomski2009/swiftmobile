package com.imanafrica.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imanafrica.core.domain.Recipient;

public interface RecipientDao extends JpaRepository<Recipient, Integer> {
	public Recipient findByCellnumber(long cellnumber);
	
}
