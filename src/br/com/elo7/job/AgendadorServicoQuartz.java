package br.com.elo7.job;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;

public class AgendadorServicoQuartz implements ServletContextListener {

	/**
	* Nome do serviço que será armazenado pelo “Scheduler”
	*/
	public static final String NOME_JOB = "Elo7Job";
	
	public void contextInitialized(ServletContextEvent event) {
		agendar();
	}

	public void contextDestroyed(ServletContextEvent event) {
		try {
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.removeJobListener("Elo7JobjOB");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	/**
	* Método que realiza o agendamento
	*/
	public static void agendar(){
		try{		
			Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();			
			if (scheduler.getJobDetail(NOME_JOB, Scheduler.DEFAULT_GROUP) == null) {			
				JobDetail jobDetail = new JobDetail(NOME_JOB, Scheduler.DEFAULT_GROUP,
				Elo7Job.class);		
				CronTrigger trigger = new CronTrigger(NOME_JOB, Scheduler.DEFAULT_GROUP);			
				trigger.setCronExpression("0/10 * * * * ?"); 
				scheduler.scheduleJob(jobDetail, trigger);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}	

	
}
