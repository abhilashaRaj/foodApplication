package com.incture.FoodApp.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.incture.FoodApp.Entity.UserDTO;
import com.incture.FoodApp.Entity.UserMaster;
import com.incture.FoodApp.Repo.RoleRepository;
import com.incture.FoodApp.Repo.UserRepository;
import com.incture.FoodApp.HibernateUtil;
import com.incture.FoodApp.Entity.Menu;
import com.incture.FoodApp.Entity.RoleMaster;

@Service
public class CommonService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	public UserDTO getMenu(String date, String category) {
		// TODO Auto-generated method stub
		Transaction transaction = null;

		UserDTO getMenuResponse = new UserDTO();
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			getMenuResponse.setStatus(true);
			getMenuResponse.setMessage("Success");

			@SuppressWarnings("deprecation")
			List<?> menu = session.createCriteria(Menu.class).add(Restrictions.eq("menuCategory", category))
					.add(Restrictions.eq("date", date)).list();

			getMenuResponse.setData(menu.stream().distinct().collect(Collectors.toList()));

			session.close();

			return getMenuResponse;
		} catch (Exception e) {
			System.out.println(e);
			getMenuResponse.setStatus(false);
			getMenuResponse.setMessage("Exception occured!" + e);
			transaction.rollback();
		}
		return getMenuResponse;
	}

	public UserDTO login(UserMaster user) {

		Transaction transaction = null;

		UserDTO loginResponse = new UserDTO();
		try {
			Session session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			loginResponse.setStatus(true);
			loginResponse.setMessage("Success! User exists");

			if (userRepository.findByUserId(user.getUserId()) != null) {
				UserMaster existingUser = session.find(UserMaster.class, user.getUserId());
				loginResponse.setData(existingUser);
			} else {
				UserMaster newUser = new UserMaster();
				newUser.setActive(true);
				newUser.setUserEmail(user.getUserEmail());
				newUser.setFirstName(user.getFirstName());
				newUser.setLastName(user.getLastName());
				newUser.setOrganization(user.getOrganization());
				newUser.setUserId(user.getUserId());

				RoleMaster role = roleRepository.findByName("User");
				newUser.getRoles().add(role);
				role.getUsers().add(newUser);

				userRepository.save(newUser);
				
				loginResponse.setMessage("Success! New user added");
				loginResponse.setData(newUser);
			}
			session.close();
			return loginResponse;
		} catch (Exception e) {
			System.out.println(e);
			loginResponse.setStatus(false);
			loginResponse.setMessage("Exception occured!" + e);
			transaction.rollback();
		}
		return loginResponse;
	}

}
