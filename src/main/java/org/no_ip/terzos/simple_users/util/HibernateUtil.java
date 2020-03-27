package org.no_ip.terzos.simple_users.util;

import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import org.no_ip.terzos.simple_users.model.User;
import org.no_ip.terzos.simple_users.model.Address;
/**
 * 
 * Class HibernateUtil for ORM
 *
 */
public class HibernateUtil {
	private static SessionFactory sessionFactory;
	
	/**
	 * Returns static sessionFactory to handle database operations. Driver configured for MySQL, url points to localhost to db employees, HBM2DDL_AUTO set to update
	 * @return SessionFactory to open Sessions for reading and writing to database
	 */
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) {
			try {
				Configuration configuration = new Configuration();

				// Hibernate settings equivalent to hibernate.cfg.xml's properties
				Properties settings = new Properties();
				settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
				settings.put(Environment.URL, "jdbc:mysql://localhost:3306/employees?useSSL=false");
				settings.put(Environment.USER, "TestApp");
				settings.put(Environment.PASS, "######");
				settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

				settings.put(Environment.SHOW_SQL, "true");

				settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");

				settings.put(Environment.HBM2DDL_AUTO, "update");

				configuration.setProperties(settings);
				
				configuration.addAnnotatedClass(User.class);
				configuration.addAnnotatedClass(Address.class);

				ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
						.applySettings(configuration.getProperties()).build();
				
				sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sessionFactory;
	}
}