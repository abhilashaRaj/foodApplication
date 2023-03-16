package com.incture.FoodApp.Entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Date;

@Entity
@Table(name = "meal_attendance")
public class MealAttendance {

	@EmbeddedId
	private UserPkId userPkId;

	@Column(name = "breakfast_attendance")
	private boolean breakfastAttendance;

	@Column(name = "lunch_attendance")
	private boolean lunchAttendance;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "created_on")
	private Date createdOn;

	@Column(name = "updated_by")
	private String updatedBy;

	@Column(name = "updated_on")
	private Date updatedOn;

	public MealAttendance(UserPkId userPkId, boolean breakfastAttendance, boolean lunchAttendance, String createdBy,
			Date createdOn, String updatedBy, Date updatedOn) {
		this.userPkId = userPkId;
		this.breakfastAttendance = breakfastAttendance;
		this.lunchAttendance = lunchAttendance;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public MealAttendance() {
	}

	public UserPkId getUserPkId() {
		return userPkId;
	}

	public void setUserPkId(UserPkId userPkId) {
		this.userPkId = userPkId;
	}

	public boolean isBreakfastAttendance() {
		return breakfastAttendance;
	}

	public void setBreakfastAttendance(boolean breakfastAttendance) {
		this.breakfastAttendance = breakfastAttendance;
	}

	public boolean isLunchAttendance() {
		return lunchAttendance;
	}

	public void setLunchAttendance(boolean lunchAttendance) {
		this.lunchAttendance = lunchAttendance;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}
}
