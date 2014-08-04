package br.com.elo7.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class DAOFactory {

	  public Session openSession(){
	        AnnotationConfiguration configuration = new AnnotationConfiguration();
	        configuration.configure();

	        SessionFactory factory = configuration.buildSessionFactory();
	        Session session = factory.openSession();
	        return session;
	    }
	    
	    
	    public void closeSession(Session session){        
	        session.close();
	    }
	    
}
