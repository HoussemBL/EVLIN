package henry.database.connection;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;


import henry.database.*;

public class vizDBUtil {
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            // loads configuration and mappings
       //     Configuration configuration = new Configuration()/*.configure()*/.configure("hibernateHistory.cfg.xml");
        	
        	
        	Configuration config =  new AnnotationConfiguration().configure();
        	config.addAnnotatedClass(QueryPath.class);
    		config.addAnnotatedClass(QueryTable.class);
    		config.addAnnotatedClass(Query_FromTable.class);
    		config.addAnnotatedClass(Query_SelectTable.class);
    		config.addAnnotatedClass(Query_ConditionTable.class);
    		config.addAnnotatedClass(VizTable.class);
    		config.addAnnotatedClass(MarkTable.class);
    		config.addAnnotatedClass(DatasetTable.class);
    		config.addAnnotatedClass(ScaleTable.class);
    		config.addAnnotatedClass(ChannelTable.class);
    		config.addAnnotatedClass(ChannelUsageTable.class);
    		config.addAnnotatedClass(AxisTable.class);
    		config.addAnnotatedClass(AxisUsageTable.class);
    		config.configure("hibernateEvolutionProv.cfg.xml");
            ServiceRegistry serviceRegistry
                = new StandardServiceRegistryBuilder()
                    .applySettings(config.getProperties()).build();

            // builds a session factory from the service registry
            sessionFactory = config.buildSessionFactory(serviceRegistry);           
        }

        return sessionFactory;
    }
}