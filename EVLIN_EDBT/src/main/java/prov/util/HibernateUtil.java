package prov.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // loads configuration and mappings
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            // builds a session factory from the service registry
            sessionFactory = configuration.
            		buildSessionFactory(serviceRegistry);           
        }

        return sessionFactory;
    }
    
    public  static Session  getSessionFactoryvariable(String dbName) {
      //  if (sessionFactory == null) {
        	
        /*	String conf_name = "jdbc:postgresql://localhost:5433/"+dbName;     	
   
            Configuration configuration = new Configuration().configure();
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();

            sessionFactory = configuration.setProperty("hibernate.connection.url", conf_name).
            		buildSessionFactory(serviceRegistry);   */        
      //  }
    	Configuration config =  new AnnotationConfiguration().configure();
    	config.configure(dbName);
        ServiceRegistry serviceRegistry
            = new StandardServiceRegistryBuilder()
                .applySettings(config.getProperties()).build();

        // builds a session factory from the service registry
        sessionFactory = config.buildSessionFactory(serviceRegistry);   
    	
        return sessionFactory.getCurrentSession();
    } 
    
    
    
    public  static Session  ChangeSessionFactory(String dbName) {
    	String conf_name = "jdbc:postgresql://localhost:5433/"+dbName;    
    	 Configuration cfg = new Configuration();
    	    cfg.configure();
    	    cfg.setProperty("hibernate.connection.url", conf_name);
    	    SessionFactory sessionFactory = cfg.buildSessionFactory();
    	    return sessionFactory.openSession();
      } 
    
    
    
}