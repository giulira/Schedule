package br.com.elo7.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.elo7.orm.Conta;
import br.com.elo7.orm.Transferencia;


public class TransferenciaDAO {
	public List listAgendaTransferencia(){
        DAOFactory factory = new DAOFactory();
        Session session = factory.openSession();
        Query query = session.createQuery("from Transferencia where status = 'NOK'");
        List lista = (List) query.list();
        session.close();
        return lista;
    }
    
 
    public void executarTransferencia(Transferencia transferencia){
        Conta contaOrigem = new Conta();
        Conta contaDestino = new Conta();
        DAOFactory factory = new DAOFactory();
        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();
        try{
            contaOrigem = descontarContaOrigem(session, transferencia);
            contaDestino = adicionarContaDestino(session, transferencia);
            transferencia.setDataTransferencia(new Date());
            transferencia.setStatus("OK");
            session.update(contaDestino);
            session.update(contaOrigem);                        
            session.update(transferencia);
            tx.commit();        
            session.close();
        }catch(Exception e){
            tx.rollback();
            session.close();
            e.getMessage();
        }        
    }
    
    public Conta descontarContaOrigem(Session session, Transferencia transferencia){        
         Query query = session.createQuery("from Conta where agencia = "+ transferencia.getAgOrigem()+" and numeroConta = "+transferencia.getContaOrigem());
         Object obj = query.uniqueResult();
         Conta conta = (Conta) obj;    
         conta.setSaldo(conta.getSaldo().subtract(transferencia.getValorTransferencia()));           
         return conta;
    }
    
    public Conta adicionarContaDestino(Session session, Transferencia transferencia){        
        Query query = session.createQuery("from Conta where agencia = "+ transferencia.getAgDestino()+" and numeroConta = "+transferencia.getContaDestino());
         Object obj = query.uniqueResult();
         Conta conta = (Conta) obj;    
         conta.setSaldo(conta.getSaldo().add(transferencia.getValorTransferencia()));           
         return conta;        
    }	   
	
}
