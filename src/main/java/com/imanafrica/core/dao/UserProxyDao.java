package com.imanafrica.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imanafrica.core.domain.proxy.UserProxy;

public interface UserProxyDao extends JpaRepository<UserProxy, Integer>{

}
