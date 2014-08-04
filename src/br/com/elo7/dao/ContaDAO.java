package br.com.elo7.dao;

import org.hibernate.Query;
import org.hibernate.Session;

import br.com.elo7.orm.Conta;

public class ContaDAO {
	
	 public Conta buscarConta(long agencia, long numeroConta){
	       DAOFactory factory = new DAOFactory();
	        Session session = factory.openSession();
	        Query query = session.createQuery("from Conta where agencia = "+ agencia+" and numeroConta = "+numeroConta);
	        Object obj = query.uniqueResult();
	        Conta conta =(Conta) obj;
	        session.close();
	        return conta;	        
	    }

}
