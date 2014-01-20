package com.imanafrica.core.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.imanafrica.core.domain.Group;
import com.imanafrica.core.domain.User;

public interface GroupDao extends JpaRepository<Group, Integer> {
public List<Group> findByOwner(User user);

public List<Group> findByOwnerUserid(int userid);
}
