package com.incture.FoodApp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.incture.FoodApp.Entity.UserMaster;

@Repository
public interface UserRepository extends JpaRepository<UserMaster, String> {
	
	UserMaster findByUserId(String userId);

}
