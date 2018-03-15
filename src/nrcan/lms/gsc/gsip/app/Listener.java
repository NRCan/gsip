package nrcan.lms.gsc.gsip.app;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import nrcan.lms.gsc.gsip.conf.Configuration;
import nrcan.lms.gsc.gsip.data.DataManager;
import nrcan.lms.gsc.gsip.template.TemplateManager;

/**
 * 
 * @author Eric Boisvert
 * Laboratoire de Cartographie Numérique et de Photogrammétrie
 * Commission géologique du Canada (c) 2018
 * Ressources naturelles Canada
 */

public class Listener implements ServletContextListener {
	
	
	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
		//TODO: clear any resource (none so far)
		
		Logger.getAnonymousLogger().log(Level.INFO,"### Application has stopped - killing running tasks !");

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		Logger.getAnonymousLogger().log(Level.INFO,"### Application has started");
		// initialise
		Logger.getAnonymousLogger().log(Level.INFO, "Initialise configuration");
		Configuration.getInstance(arg0.getServletContext());
		Logger.getAnonymousLogger().log(Level.INFO, "Initialise template manager");
		TemplateManager.getInstance(arg0.getServletContext()); 
		Logger.getAnonymousLogger().log(Level.INFO, "Initialise Data manager");
		DataManager.getInstance(arg0.getServletContext());

	}

}
