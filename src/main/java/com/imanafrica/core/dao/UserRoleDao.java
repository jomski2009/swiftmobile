package com.imanafrica.core.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.imanafrica.core.domain.UserRole;

@Repository
public interface UserRoleDao extends JpaRepository<UserRole, Integer>  {

}
