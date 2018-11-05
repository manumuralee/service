package com.assessment.projectmanagementservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.projectmanagementservice.model.Project;

@Repository
@Transactional(readOnly = true)
public interface ProjectRepository extends JpaRepository<Project, Integer>{

	@Query("SELECT p FROM Project p WHERE p.user.userId = (:id)")
	public List<Project> findProjectByUserId(@Param("id") int id);

}
