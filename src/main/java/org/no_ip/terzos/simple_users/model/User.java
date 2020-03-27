package org.no_ip.terzos.simple_users.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.no_ip.terzos.simple_users.util.AddressType;
import org.no_ip.terzos.simple_users.util.Gender;
/**
 * 
 * @author Nikos
 *
 */
/**
 * 
 * User model class
 *
 */
@Entity
@Table(name="user")
public class User {
	/**
	 * Id of the User
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	/**
	 * First name of the User
	 */
	@Column(name="firstName", nullable=false, length=50)
	private String firstName;
	
	/**
	 * Last name of the User
	 */
	@Column(name="lastName", nullable=false, length=100)
	private String lastName;
	
	/**
	 * Gender of the User {@link org.no_ip.terzos.simple_users.util.Gender Gender}
	 */
	@Enumerated(EnumType.ORDINAL)
	@Column(length=1, nullable=false)
	private Gender gender;
	
	/**
	 * Birth date of the User
	 */
	@Temporal(TemporalType.DATE)
	@Column(name="birthDate", nullable=false)
	private Date birthDate;
	
	/**
	 * Address Set of the User
	 */
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.LAZY)	//EAGER automatically joins addresses
	@JoinTable(name="user_addr",
			joinColumns= { @JoinColumn(name="user_id")},
			inverseJoinColumns = { @JoinColumn(name="address_id")})
	private Set<Address> addresses = new HashSet<Address>();
	
	/**
	 * No parameters constructor
	 */
	public User() {
		super();
	}

	/**
	 * Constructor <b>without</b> id
	 * @param firstName First name String
	 * @param lastName Last name String
	 * @param gender {@link org.no_ip.terzos.simple_users.util.Gender Gender}
	 * @param birthDate Birth {@link java.util.Date Date}
	 */
	public User(String firstName, String lastName, Gender gender, Date birthDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
	}
	
	/**
	 * Constructor <b>with</b> id. Use to create an Object of an existing User in the database for update purposes.
	 * @param id User id
	 * @param firstName First name String
	 * @param lastName Last name String
	 * @param gender {@link org.no_ip.terzos.simple_users.util.Gender Gender}
	 * @param birthDate Birth {@link java.util.Date Date}
	 */
	public User(int id, String firstName, String lastName, Gender gender, Date birthDate) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.birthDate = birthDate;
	}
	
	/**
	 * Get the id of the User
	 * @return id of the User
	 */
	public int getId() {
		return id;
	}

	/**
	 * Get the first name of the User
	 * @return String with the first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Set the first name of the User
	 * @param firstName String with the first name
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Get the last name of the User
	 * @return String with the last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Set the last name of the User
	 * @param lastName String with the last name
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Get the Gender of the User
	 * @return {@link org.no_ip.terzos.simple_users.util.Gender Gender} of the User
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * Set the Gender of the User
	 * @param gender {@link org.no_ip.terzos.simple_users.util.Gender Gender} of the User
	 */
	public void setGender(Gender gender) {
		this.gender = gender;
	}

	/**
	 * Get the birth Date of the User
	 * @return Birth {@link java.util.Date Date} of the User
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * Set the birth Date of the User
	 * @param birthDate Birth {@link java.util.Date Date} of the User
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	/**
	 * Get the Address Set of the User
	 * @return Address Set of the User
	 */
	public Set<Address> getAddresses() {
		return addresses;
	}

	/**
	 * Set the Address Set of the User
	 * @param addresses Address Set of the User
	 */
	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}
	
	/**
	 * Add an address in the Address Set of this User and <b>also</b> add this User to the User Set of the Address
	 * @param address Address to link to this User
	 */
	public void addAddress(Address address) {
		this.addresses.add(address);
		address.getUsers().add(this);
	}
	 
	/**
	 * Scan the Address Set of the User and return the Address which is of {@link org.no_ip.terzos.simple_users.util.AddressType AddressType} {@link org.no_ip.terzos.simple_users.util.AddressType#HOME HOME}
	 * @return Home Address of the User
	 */
	public Address getHomeAddress() {
		for (Address a : this.addresses) {
			if (a.getType() == AddressType.HOME)
				return a;
		}
		return null;
	}
	
	/**
	 * Scan the Address Set of the User and return the Address which is of {@link org.no_ip.terzos.simple_users.util.AddressType AddressType} {@link org.no_ip.terzos.simple_users.util.AddressType#WORK WORK}
	 * @return Home Address of the User
	 */
	public Address getWorkAddress() {
		for (Address a : this.addresses) {
			if (a.getType() == AddressType.WORK)
				return a;
		}
		return null;
	}
}



