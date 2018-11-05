/**
 * 
 */
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
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

/**
 * @author manum
 *
 */

@Entity
@Table(name = "project_table", uniqueConstraints = { @UniqueConstraint(columnNames = "project_name") })
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@DatesConstraint
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "project_id", unique = true, nullable = false)
	private int projectId;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "start_date")
	private Date startDate;

	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "end_date")
	private Date endDate;

	@NotNull
	@PriorityConstraint(message = ERROR_E007_INVALID_PRIORITY)
	@Column(name = "priority", nullable = false, columnDefinition = "int default 0")
	private int priority;

	@NotNull
	@Size(min = 1, message = "Name should have atleast 1 characters")
	@Column(name = "project_name", unique = true, nullable = false)
	private String projectName;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinColumn(name = "user_id", nullable = true)
	private User user;

	@Column(name = "status", nullable = false, columnDefinition = "int default 0")
	private int status;

	@Transient
	private long taskCount;

	@Transient
	private long closedTaskCount;

	/**
	 * @return the projectId
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * @param projectId
	 *            the projectId to set
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
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
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * @param projectName
	 *            the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
	 * @return the taskCount
	 */
	public long getTaskCount() {
		return taskCount;
	}

	/**
	 * @param taskCount
	 *            the taskCount to set
	 */
	public void setTaskCount(long taskCount) {
		this.taskCount = taskCount;
	}

	/**
	 * @return the closedTaskCount
	 */
	public long getClosedTaskCount() {
		return closedTaskCount;
	}

	/**
	 * @param closedTaskCount
	 *            the closedTaskCount to set
	 */
	public void setClosedTaskCount(long closedTaskCount) {
		this.closedTaskCount = closedTaskCount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Project [projectId=");
		builder.append(projectId);
		builder.append(", startDate=");
		builder.append(startDate);
		builder.append(", endDate=");
		builder.append(endDate);
		builder.append(", priority=");
		builder.append(priority);
		builder.append(", projectName=");
		builder.append(projectName);
		builder.append(", user=");
		builder.append(user);
		builder.append(", status=");
		builder.append(status);
		builder.append(", taskCount=");
		builder.append(taskCount);
		builder.append(", closedTaskCount=");
		builder.append(closedTaskCount);
		builder.append("]");
		return builder.toString();
	}

}
