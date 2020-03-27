package org.no_ip.terzos.simple_users.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.no_ip.terzos.simple_users.model.Address;
//import org.no_ip.terzos.simple_users.model.User;
import org.no_ip.terzos.simple_users.util.HibernateUtil;
/**
 * 
 * @author Nikos
 * 
 */
/**
 * 
 * AddressDao handles the fetching and writing of Address objects to the database
 * 
 */
public class AddressDao {
	
	/**
	 * Saves an Address object to the database
	 * @param address The Address object to save to the database
	 */
	public void saveAddress(Address address) {
		Transaction transaction = null;
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start the transaction
			transaction = session.beginTransaction();
			
			// save user
			session.save(address);
			
			// commit the transaction
			transaction.commit();
			
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the Address with the passed id
	 * @param id The id of the Address
	 * @return Address with the selected id with the users that use it.
	 */
	public Address getAddressWithUsers(int id) {
		Transaction transaction = null;
		Address address = null;

		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start the transaction
			transaction = session.beginTransaction();
			
			// get address with users
			address = (Address) session.createQuery("SELECT a FROM Address a JOIN FETCH a.users WHERE a.id = " + id).getSingleResult();
			
			// commit the transaction
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return address;
	}
	
	/**
	 * Updates the Address passed
	 * @param address The Address to be updated
	 */
	public void updateAddress(Address address) {
		Transaction transaction = null;
		//if address changed
		try(Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start the transaction
			transaction = session.beginTransaction();
			
			// update user
			session.update(address);
			
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
	 * Gets all the User Addresses. Similar to {@link UserDao#getUserWithAddresses getUserWithAddresses}
	 * @param userid The id of the User in question
	 * @return The Address List that belong to the User
	 */
	@SuppressWarnings("unchecked")
	public List<Address> getAddressesOfUser(int userid) {
		Transaction transaction = null;
		List<Address> addresses = null;
		
		try (Session session = HibernateUtil.getSessionFactory().openSession()) {
			// start the transaction
			transaction = session.beginTransaction();
			
			// get Addresses of User Just added right, didn't check
			addresses = session.createQuery("SELECT a FROM Address a RIGHT JOIN a.users au WHERE au.id = " + userid).getResultList();
			
			// commit the transaction
			transaction.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return addresses;
	}
}
