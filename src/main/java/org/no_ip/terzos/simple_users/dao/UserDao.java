package org.no_ip.terzos.simple_users.dao;

import java.util.List;
//import java.util.Set;

//import javax.persistence.EntityManager;

//import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
//import org.no_ip.terzos.simple_users.model.Address;
import org.no_ip.terzos.simple_users.model.User;
//import org.no_ip.terzos.simple_users.model.Address;
import org.no_ip.terzos.simple_users.util.HibernateUtil;
/**
 * 
 * @author Nikos
 *
 */
/**
 * 
 * UserDao handles the fetching and writing of User objects to the database
 *
 */
public class UserDao {

	/**
	 * Saves a User object to the database
	 * @param user The User object to save to the database
	 */
	public void saveUser(User user) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start the transaction
			transaction = session.beginTransaction();
			
			// save user
			session.save(user);
			
			// commit the transaction
			transaction.commit();
			
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
		}
	}
	
	/**
	 * List of all User objects saved in the database
	 * @return User List of all User objects saved in the database
	 */
	public List<User> getAllUsers() {
		Transaction transaction = null;
		List<User> users = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start the transaction
			transaction = session.beginTransaction();
			
			// list users
			users = session.createQuery("from User", User.class).list();
			
			// commit the transaction
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return users;
	}
	
	/**
	 * Gets the User with the passed id <b>without</b> its Address List
	 * @param id The id of the User
	 * @return User with the selected id <b>without</b> its Address List
	 */
	public User getUser(int id) {
		Transaction transaction = null;
		User user = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start the transaction
			transaction = session.beginTransaction();
			
			// get user
			user = session.get(User.class, id);
			
			// commit the transaction
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	/**
	 * Gets the User with the passed id <b>including</b> its Address List
	 * @param id Id of the User in question
	 * @return The User <b>including</b> its Address List
	 */
	public User getUserWithAddresses(int id) {
		Transaction transaction = null;
		User user = null;
		

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start the transaction
			transaction = session.beginTransaction();
			
			// get user with addresses
			user = (User) session.createQuery("SELECT u FROM User u LEFT JOIN FETCH u.addresses WHERE u.id = " + id).getSingleResult();
			
			// commit the transaction
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	/**
	 * Updates the User passed
	 * @param user The User to be updated
	 */
	public void updateUser(User user) {
		Transaction transaction = null;
		//if address changed
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start the transaction
			transaction = session.beginTransaction();
			
			// update user
			session.update(user);
			
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * Removes the User with the passed id from the database
	 * @param id The id of the User for deletion
	 */
	public void deleteUser(int id) {
		Transaction transaction = null;
		
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start the transaction
			transaction = session.beginTransaction();
			
			// user exists?delete
			User user = session.get(User.class, id);
			if (user != null) {
				session.delete(user);
				System.out.println("user is deleted.");
			}
			
			// commit transaction
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	
}
