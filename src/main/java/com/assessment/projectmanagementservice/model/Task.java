package com.assessment.projectmanagementservice.model;

import static com.assessment.projectmanagementservice.util.ProjectManagementConstants.ERROR_E007_INVALID_PRIORITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.assessment.projectmanagementservice.util.JsonDateDeSerializer;
import com.assessment.projectmanagementservice.util.JsonDateSerializer;
import com.assessment.projectmanagementservice.validator.DatesConstraint;
import com.assessment.projectmanagementservice.validator.PriorityConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "task_table", uniqueConstraints = { @UniqueConstraint(columnNames = "task_name") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@DatesConstraint
public class Task implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public Task() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "task_id")
	private int taskId;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "start_date")
	private Date startDate;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "end_date")
	private Date endDate;

	@PriorityConstraint(message = ERROR_E007_INVALID_PRIORITY)
	@Column(name = "priority", nullable = false, columnDefinition = "int default 0")
	private int priority;

	@NotNull
	@Size(min = 1, message = "Name should have atleast 1 characters")
	@Column(name = "task_name", unique = true, nullable = false)
	private String taskName;

	@Column(name = "status", nullable = false, columnDefinition = "int default 0")
	private int status;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_task_id")
	private ParentTask parentTask;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "project_id", nullable = true)
	private Project project;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "is_parent", nullable = false, columnDefinition = "int default 0")
	private int isParent;

	/**
	 * @return the taskId
	 */
	public int getTaskId() {
		return taskId;
	}

	/**
	 * @param taskId
	 *            the taskId to set
	 */
	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	/**
	 * @return the startDate
	 */
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeSerializer.class)
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeSerializer.class)
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate
	 *            the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the taskName
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * @param taskName
	 *            the taskName to set
	 */
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	/**
	 * @return the parentTask
	 */
	public ParentTask getParentTask() {
		return parentTask;
	}

	/**
	 * @param parentTask
	 *            the parentTask to set
	 */
	public void setParentTask(ParentTask parentTask) {
		this.parentTask = parentTask;
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
	 * @return the project
	 */
	public Project getProject() {
		return project;
	}

	/**
	 * @param project
	 *            the project to set
	 */
	public void setProject(Project project) {
		this.project = project;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the isParent
	 */
	public int getIsParent() {
		return isParent;
	}

	/**
	 * @param isParent
	 *            the isParent to set
	 */
	public void setIsParent(int isParent) {
		this.isParent = isParent;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Task [taskId=");
		builder.append(taskId);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", taskName=");
		builder.append(taskName);
		builder.append(", status=");
		builder.append(status);
		builder.append(", parentTask=");
		builder.append(parentTask);
		builder.append(", project=");
		builder.append(project);
		builder.append(", user=");
		builder.append(user);
		builder.append(", isParent=");
		builder.append(isParent);
		builder.append("]");
		return builder.toString();
	}

}
