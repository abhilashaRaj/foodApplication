package com.incture.FoodApp.Entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.Date;

@Entity
@Table(name="meal_consent")
public class MealConsent {
    @EmbeddedId
    private UserPkId userPkId;
    
    @Column(name = "breakfast_consent")
    private int breakfastConsent;
    
    @Column(name = "lunch_consent")
    private int lunchConsent;
    
    @Column(name = "created_by")
    private String createdBy;
    
    @Column(name = "created_on")
    private Date createdOn;
    
    @Column(name = "updated_by")
    private String updatedBy;
    
    @Column(name = "updated_on")
    private Date updatedOn;

    public MealConsent(UserPkId userPkId, int breakfastConsent, int lunchConsent, String createdBy, Date createdOn, String updatedBy, Date updatedOn) {
        this.userPkId = userPkId;
        this.breakfastConsent = breakfastConsent;
        this.lunchConsent = lunchConsent;
        this.createdBy = createdBy;
        this.createdOn = createdOn;
        this.updatedBy = updatedBy;
        this.updatedOn = updatedOn;
    }

    public MealConsent() {
    }

    public UserPkId getUserPkId() {
        return userPkId;
    }

    public void setUserPkId(UserPkId userPkId) {
        this.userPkId = userPkId;
    }

    public int getBreakfastConsent() {
        return breakfastConsent;
    }

    public void setBreakfastConsent(int breakfastConsent) {
        this.breakfastConsent = breakfastConsent;
    }

    public int getLunchConsent() {
        return lunchConsent;
    }

    public void setLunchConsent(int lunchConsent) {
        this.lunchConsent = lunchConsent;
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
