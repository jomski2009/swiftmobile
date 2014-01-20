package com.imanafrica.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imanafrica.core.domain.Sms;

public interface SmsDao extends JpaRepository<Sms, Long>{

}
