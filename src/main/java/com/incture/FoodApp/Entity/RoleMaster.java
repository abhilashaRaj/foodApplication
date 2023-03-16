package com.incture.FoodApp.Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.incture.FoodApp.Entity.UserMaster;

@Entity
@Table(name = "role_master")
public class RoleMaster {

	@Id
	@Column(name="role_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;
	
	@ManyToMany(mappedBy = "roles", cascade = { CascadeType.ALL })
	@JsonIgnore
	private Set<UserMaster> users = new HashSet<UserMaster>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserMaster> getUsers() {
		return users;
	}

	public void setUsers(Set<UserMaster> users) {
		this.users = users;
	}
}
