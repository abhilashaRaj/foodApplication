package com.incture.FoodApp.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.incture.FoodApp.Entity.RoleMaster;

@Entity
@Table(name="user_master")
public class UserMaster {
    @Id
    @Column(name = "user_id")
    private String userId;
    
    @Column(name = "first_name")
    private String firstName;
    
    @Column(name = "last_name")
    private String lastName;
    
    @Column(name = "user_email")
    private String userEmail;
    private String organization;

    
    @Column(name = "is_active")
	private boolean isActive;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_role_master", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<RoleMaster> roles = new HashSet<>();
    
    public UserMaster() {
    }

    public UserMaster(String userId, String firstName, String lastName, String organization,String userEmail) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.userEmail=userEmail;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Set<RoleMaster> getRoles() {
		return roles;
	}

	public void setRoles(Set<RoleMaster> roles) {
		this.roles = roles;
	}
}
