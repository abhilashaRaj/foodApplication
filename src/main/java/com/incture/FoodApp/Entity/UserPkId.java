package com.incture.FoodApp.Entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Embeddable
public class UserPkId implements Serializable {
	
	@Column(name = "user_id")
    private String userId;
    private Date date;

    public UserPkId(String userId, Date date) {
        this.userId = userId;
        this.date = date;
    }

    public UserPkId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPkId userPkId = (UserPkId) o;
        return Objects.equals(userId, userPkId.userId) && Objects.equals(date, userPkId.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, date);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
