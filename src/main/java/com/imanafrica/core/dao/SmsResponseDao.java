package com.imanafrica.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imanafrica.core.domain.SmsResponse;

public interface SmsResponseDao extends JpaRepository<SmsResponse, Long> {

}
