package com.assessment.projectmanagementservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.projectmanagementservice.model.User;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Integer>{

}
