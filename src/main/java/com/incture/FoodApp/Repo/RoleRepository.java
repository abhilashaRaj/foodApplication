package com.incture.FoodApp.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.incture.FoodApp.Entity.RoleMaster;
import com.incture.FoodApp.Entity.UserMaster;

@Repository
public interface RoleRepository extends JpaRepository<RoleMaster, Integer>, CrudRepository<RoleMaster, Integer> {

	RoleMaster findByName(String name);
}
