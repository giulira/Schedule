package br.com.elo7.job;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.elo7.dao.TransferenciaDAO;
import br.com.elo7.orm.Transferencia;

public class Elo7Job implements Job{

	 @Override
	    public void execute(JobExecutionContext context)
	            throws JobExecutionException {
		 		        
		  TransferenciaDAO dao = new TransferenciaDAO();
	        List lista = dao.listAgendaTransferencia(); 
	     	        
	        for(int i = 0; i < lista.size(); i++){
	            Transferencia transf = new Transferencia();
	            transf = (Transferencia)lista.get(i);
	          
	            if(compararDatas(transf)){                
	                dao.executarTransferencia(transf);
	            }
	            
	            
	        }
      
	    }
	 
	 public boolean compararDatas(Transferencia transferencia){
	        
	        GregorianCalendar dtTransf = new GregorianCalendar();
	        dtTransf.setTime(transferencia.getDataTransferencia());       
	        
	        Date hjDt= new Date();
	        GregorianCalendar hoje = new GregorianCalendar();
	        hoje.setTime(hjDt);
	        
	        if(dtTransf.before(hoje) || dtTransf.equals(hoje)){
	            return true;
	        }else{
	            return false;
	        }
	    }
	    
}
