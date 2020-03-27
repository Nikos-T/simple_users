package org.no_ip.terzos.simple_users.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.no_ip.terzos.simple_users.util.AddressType;
/**
 * 
 * @author Nikos
 *
 */
/**
 * 
 * Address model class
 *
 */
@Entity
@Table(name="address")
public class Address {
	/**
	 * Id of the Address
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	/**
	 * Address string with the Address information
	 */
	@Column(name="address", nullable=false)
	private String address;
	
	/**
	 * Address type {@link org.no_ip.terzos.simple_users.util.AddressType AddressType}
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(length=1, nullable=false)
	private AddressType type;

	/**
	 * User Set that use this Address
	 */
	@ManyToMany(mappedBy="addresses")
	private Set<User> users = new HashSet<User>();
	
	/**
	 * No parameters constructor
	 */
	public Address() {
		super();
	}
	
	/**
	 * Constructor
	 * @param address Sting with the Address information
	 * @param type {@link org.no_ip.terzos.simple_users.util.AddressType AddressType}
	 */
	public Address(String address, AddressType type) {
		super();
		this.address = address;
		this.type = type;
	}
	
	/**
	 * Get the id of the Address
	 * @return Id of the Address
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Get the information String of the Address
	 * @return String with the Address information
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Set the information String of the Address
	 * @param address String with the Address information
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Get the type of the Address
	 * @return Type of the Address {@link org.no_ip.terzos.simple_users.util.AddressType AddressType}
	 */
	public AddressType getType() {
		return type;
	}

	/**
	 * Set the type of the Address
	 * @param type Type of the Address {@link org.no_ip.terzos.simple_users.util.AddressType AddressType}
	 */
	public void setType(AddressType type) {
		this.type = type;
	}
	
	/**
	 * Get the User Set that use this Address
	 * @return User Set of the Address
	 */
	public Set<User> getUsers() {
		return users;
	}
	
	/**
	 * Set the User Set that use this Address.
	 * @param users User Set that use this Address
	 */
	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	/**
	 * Add a User in the User Set of this Address and <b>also</b> add this Address to the Address Set of the User
	 * @param user User to link to this Address
	 */
	public void addUser(User user) {
		this.users.add(user);
		user.getAddresses().add(this);
	}
	
}
