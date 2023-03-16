package com.incture.FoodApp.Repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.incture.FoodApp.Entity.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {

	List<Menu> findByDate(String date);

	List<Menu> findByMenuCategory(String menuCategory);

	@Query("SELECT m FROM Menu m WHERE m.date=?1 AND m.menuCategory=?2")
	Menu findByMenuAndCategory(String date, String menuCategory);

}
