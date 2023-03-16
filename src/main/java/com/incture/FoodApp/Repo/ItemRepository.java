package com.incture.FoodApp.Repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.incture.FoodApp.Entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>, CrudRepository<Item, Integer> {

	Item findByItemName(String itemName);

//	List<Item> findByMenus(Set<Menu> menus);

}
