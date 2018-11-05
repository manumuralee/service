/**
 * 
 */
package com.assessment.projectmanagementservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Admin
 *
 */
@Entity
@Table(name = "parent_task_table")
public class ParentTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParentTask() {
		super();
	}

	public ParentTask(String parentName) {
		super();
		this.parentName = parentName;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "parent_task_id")
	private int parentTaskId;

	@Column(name = "parent_task_name", unique = true, nullable = false)
	private String parentName;

	@Column(name = "status", nullable = false, columnDefinition = "int default 0")
	private int status;

	
	@OneToOne
	@JoinColumn(name = "task_id")
	private Task task;

	/**
	 * @return the parentTaskId
	 */
	public int getParentTaskId() {
		return parentTaskId;
	}

	/**
	 * @param parentTaskId
	 *            the parentTaskId to set
	 */
	public void setParentTaskId(int parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	/**
	 * @return the parentName
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * @param parentName
	 *            the parentName to set
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * @return the task
	 */
	public Task getTask() {
		return task;
	}

	/**
	 * @param task
	 *            the task to set
	 */
	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ParentTask [parentTaskId=");
		builder.append(parentTaskId);
		builder.append(", parentName=");
		builder.append(parentName);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}

}
