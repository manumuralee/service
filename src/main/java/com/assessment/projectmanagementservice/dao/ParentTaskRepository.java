package com.assessment.projectmanagementservice.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.assessment.projectmanagementservice.model.ParentTask;

public interface ParentTaskRepository extends JpaRepository<ParentTask, Integer> {
	
	@Query("SELECT pt FROM ParentTask pt WHERE pt.task.taskId = ?1 ")
	public ParentTask findParentTaskByTaskId(int id);

}
