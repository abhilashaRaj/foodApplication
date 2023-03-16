package com.incture.FoodApp.Entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "meal_feedback")
public class MealFeedback {
	@EmbeddedId
	private UserPkId userPkId;

	@Column(name = "breakfast_feedback")
	private String breakfastFeedback;

	@Column(name = "lunch_feedback")
	private String lunchFeedback;

	@Column(name = "breakfast_rating")
	private int breakfastRating;

	@Column(name = "lunch_rating")
	private int lunchRating;
	
    @Column(name = "created_by")
	private String createdBy;
    
    @Column(name = "created_on")
	private Date createdOn;
    
    @Column(name = "updated_by")
	private String updatedBy;
    
    @Column(name = "updated_on")
	private Date updatedOn;

	public MealFeedback(UserPkId userPkId, String breakfastFeedback, String lunchFeedback, int breakfastRating,
			int lunchRating, String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
		this.userPkId = userPkId;
		this.breakfastFeedback = breakfastFeedback;
		this.lunchFeedback = lunchFeedback;
		this.breakfastRating = breakfastRating;
		this.lunchRating = lunchRating;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.updatedBy = updatedBy;
		this.updatedOn = updatedOn;
	}

	public MealFeedback() {
	}

	public UserPkId getUserPkId() {
		return userPkId;
	}

	public void setUserPkId(UserPkId userPkId) {
		this.userPkId = userPkId;
	}

	public String getBreakfastFeedback() {
		return breakfastFeedback;
	}

	public void setBreakfastFeedback(String breakfastFeedback) {
		this.breakfastFeedback = breakfastFeedback;
	}

	public String getLunchFeedback() {
		return lunchFeedback;
	}

	public void setLunchFeedback(String lunchFeedback) {
		this.lunchFeedback = lunchFeedback;
	}

	public int getBreakfastRating() {
		return breakfastRating;
	}

	public void setBreakfastRating(int breakfastRating) {
		this.breakfastRating = breakfastRating;
	}

	public int getLunchRating() {
		return lunchRating;
	}

	public void setLunchRating(int lunchRating) {
		this.lunchRating = lunchRating;
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
