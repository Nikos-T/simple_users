package org.no_ip.terzos.simple_users.web;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.HashSet;
import java.util.List;
//import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.no_ip.terzos.simple_users.dao.AddressDao;
import org.no_ip.terzos.simple_users.dao.UserDao;
import org.no_ip.terzos.simple_users.model.Address;
import org.no_ip.terzos.simple_users.model.User;
import org.no_ip.terzos.simple_users.util.AddressType;
import org.no_ip.terzos.simple_users.util.Gender;

import com.google.gson.Gson;
/**
 * 
 * @author Nikos
 * 
 */

/**
 * Servlet implementation class UserServlet
 * <p>Mapped to "/User"
 * <p>Servlet to do CRUD operations to User - Address objects.
 * <p>Uses jsp files: user-list.jsp (list, getAddresses) and user-form.jsp (new-insert, edit-update)
 */
@WebServlet("/User")
public class UserServlet extends HttpServlet {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
    
	/**
	 * UserDao to pass User updates to database
	 */
	private UserDao userDao;
	
	/**
	 * AddressDao to pass Address updates to database
	 */
	private AddressDao addressDao;
	
	/**
	 * Gson to easily convert objects to JSON
	 */
	private Gson gson;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
        super();
        this.userDao = new UserDao();
        this.addressDao = new AddressDao();
        this.gson = new Gson();
        // TODO Auto-generated constructor stub
    }

    /**
     * calls doGet to handle requests
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	
	/**
	 * Handles requests using key=action
	 * <ul>
	 * <li>action=new:<br>&nbsp;&nbsp;Forward request to user-form.jsp <b>without</b> User object. See {@link #showNewForm showNewForm}</li>
	 * <li>action=insert:<br>&nbsp;&nbsp;Insert new User in database and redirect to user-list.jsp. See {@link #insertUser insertUser}</li>
	 * <li>action=getAddresses&#38;userid=#:<br>&nbsp;&nbsp;Return Address List in JSON format. Used in AJAX</li>
	 * <li>action=delete&#38;userid=#:<br>&nbsp;&nbsp;Not yet implemented</li>
	 * <li>action=edit&#38;userid=#:<br>&nbsp;&nbsp;Forward request to user-form.jsp <b>with</b> User object. See {@link #showEditForm showEditForm}</li>
	 * <li>action=update&#38;userid=#:<br>&nbsp;&nbsp;Update User in database and redirect to user-list.jsp. See {@link #updateUser updateUser}</li>
	 * <li>default:<br>&nbsp;&nbsp;List User objects in user-list.jsp. See {@link #listUser listUser}</li>
	 * </ul>
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String action = " ";

		if (request.getParameterMap().containsKey("action")) {
            action = request.getParameter("action");
        }
		
		switch (action) {
		case "new":
			showNewForm(request, response);
			break;
		case "insert":
			try {
				insertUser(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			break;
		case "getAddresses":
			if (request.getParameterMap().containsKey("userid")) {
	            int userid = Integer.parseInt(request.getParameter("userid"));
	            List<Address> addrs = addressDao.getAddressesOfUser(userid); //userDao.getUserAddresses(userid);
	            if (addrs.get(0) != null) {
        			for (Address a : addrs) {
        				//Not setting this results in Lazy Fetch Error.
            			a.setUsers(null);
        			}
        		}
        		String addrsString = gson.toJson(addrs==null?"":addrs);
        		response.setContentType("application/json");
        		response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
        		response.getWriter().write(addrsString);
	        } else {
	        	response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
	        	response.setCharacterEncoding("UTF-8"); // You want world domination, huh?
			    response.getWriter().write("Please provide a userid parameter");       // Write response body.
	        }
			break;
		case "delete":
			
			try {
				deleteUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "edit":
			showEditForm(request, response);
			break;
		case "update":
			updateUser(request, response);
			break;
		default:
			// handle list
			try {
				listUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	/**
	 * Uses {@link userDao userDao} to get User List of all users, includes the User List to the request, and forwards it to user-list.jsp
	 * @param request
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 * @throws ServletException
	 */
	private void listUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		List<User> listUser = userDao.getAllUsers();
		
		request.setAttribute("listUser",  listUser);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * Simply forwards the request to user-form.jsp
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * Uses request parameters to create new User and insert it to database.
	 * <p>Also possibly creates Address objects and links them to User.
	 * @param request Parameters:<ul><li>Mandatory: firstName, lastName, birthDate, gender</li><li>Optional: homeAddress, workAddress</li></ul>
	 * @param response
	 * @throws SQLException
	 * @throws IOException
	 */
	private void insertUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		Date birthDate = null;
		try {
			birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gender gender = Gender.valueOf(request.getParameter("gender"));
		String homeAddress = request.getParameter("homeAddress");
		String workAddress = request.getParameter("workAddress");
		
		User newUser = new User(firstName, lastName, gender, birthDate);
		Address hAddress = null, wAddress = null;
		if (!homeAddress.isEmpty()) {
			hAddress = new Address(homeAddress, AddressType.HOME);
			newUser.addAddress(hAddress);
			addressDao.saveAddress(hAddress);
		}
		
		if (!workAddress.isEmpty()) {
			System.out.println("this should not be printed now");
			wAddress = new Address(workAddress, AddressType.WORK);
			newUser.addAddress(wAddress);
			addressDao.saveAddress(wAddress);
		}
		
		userDao.saveUser(newUser);
		
		response.sendRedirect("User?action=list");
	}
	
	/** 
	 * Retrieves User by id, includes User in the request, and forwards request to user-form.jsp
	 * @param request Parameters:<ul><li>Mandatory: userid</li></ul>
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int userid = Integer.parseInt(request.getParameter("userid"));
		User user = userDao.getUserWithAddresses(userid);
		request.setAttribute("user", user);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * Retrieves User to edit, uses request parameters to edit the User and handle the Address Set of the User, updates the User in the database, and redirects to User?action=list (default).
	 * @param request Parameters:<ul><li>Mandatory: userid, firstName, lastName, gender, birthDate</li><li>Optional: homeAddress, workAddress</li></ul>
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		int userid = Integer.parseInt(request.getParameter("userid"));
		
		User user = userDao.getUserWithAddresses(userid);
		
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		Date birthDate = null;
		try {
			birthDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("birthDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Gender gender = Gender.valueOf(request.getParameter("gender"));
		String homeAddress = request.getParameter("homeAddress");
		String workAddress = request.getParameter("workAddress");
		
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setBirthDate(birthDate);
		
		System.out.println(user.getWorkAddress() == null);
		System.out.println(workAddress.isEmpty());
		// Using EAGER for ManyToMany field addresses would update them automatically but every-time the user is changed. This is a more correct approach but quite tedious. TODO check entity graphs
		if (user.getHomeAddress() == null) {
			if (!homeAddress.isEmpty()) {
				Address hAddress = new Address(homeAddress, AddressType.HOME);
				user.addAddress(hAddress);
				addressDao.saveAddress(hAddress);
			}
		} else if (!user.getHomeAddress().getAddress().equals(homeAddress)) {
			// if another person uses this address (address's users > 1)
			if (addressDao.getAddressWithUsers(user.getHomeAddress().getId()).getUsers().size() > 1 ) {
				Address hAddress = new Address(homeAddress, AddressType.HOME);
				user.getAddresses().remove(user.getHomeAddress());
				user.addAddress(hAddress);
				addressDao.saveAddress(hAddress);
			} else {	//just change the value
				user.getHomeAddress().setAddress(homeAddress);
				addressDao.updateAddress(user.getHomeAddress());
			}
		}
		
		if (user.getWorkAddress() == null) {
			if (!workAddress.isEmpty()) {
				Address wAddress = new Address(workAddress, AddressType.WORK);
				user.addAddress(wAddress);
				addressDao.saveAddress(wAddress);
			}
		} else if (!user.getWorkAddress().getAddress().equals(workAddress)) {
			if (addressDao.getAddressWithUsers(user.getWorkAddress().getId()).getUsers().size() > 1 ) {
				Address wAddress = new Address(workAddress, AddressType.WORK);
				user.getAddresses().remove(user.getWorkAddress());
				user.addAddress(wAddress);
				addressDao.saveAddress(wAddress);
			} else {
				user.getWorkAddress().setAddress(workAddress);
				addressDao.updateAddress(user.getWorkAddress());
			}
		}
		
		userDao.updateUser(user);
		response.sendRedirect("User?action=list");
	}
	
	public void deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("userid"));
		userDao.deleteUser(id);
		response.sendRedirect("User?action=list");
	}
}
