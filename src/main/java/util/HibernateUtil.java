package util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static final SessionFactory sessionFactory;
    
    static {
	    	// Add this to your HibernateUtil class before building SessionFactory
	    	System.setProperty("javax.net.ssl.trustStore", "ca.pem");
	    	System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    public static void shutdown() {
        if (sessionFactory != null) {
            sessionFactory.close();
        }

    }
}
