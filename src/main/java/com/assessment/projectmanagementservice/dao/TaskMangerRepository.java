package com.assessment.projectmanagementservice.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.assessment.projectmanagementservice.model.Task;

@Repository
@Transactional(readOnly = true)
public interface TaskMangerRepository extends JpaRepository<Task, Integer>, JpaSpecificationExecutor<Task> {

	@Query("SELECT count(t) FROM Task t WHERE t.project.projectId = (:id)")
	public long findTaskCountByProjectId(@Param("id") int id);
	
	@Query("SELECT count(t) FROM Task t WHERE t.project.projectId = (:id) and t.status = 1")
	public long findClosedTaskCountByProjectId(@Param("id") int id);

	@Query("SELECT t FROM Task t WHERE t.user.userId = (:id)")
	public List<Task> findTaskByUserId(@Param("id") int id);

}
