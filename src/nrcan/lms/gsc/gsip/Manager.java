package nrcan.lms.gsc.gsip;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

import nrcan.lms.gsc.gsip.conf.Configuration;
import nrcan.lms.gsc.gsip.data.DataManager;
import nrcan.lms.gsc.gsip.template.TemplateManager;
import nrcan.lms.gsc.gsip.triple.TripleStore;
import nrcan.lms.gsc.gsip.triple.TripleStoreFactory;

/**
 * 
 * @author Eric Boisvert
 * Laboratoire de Cartographie Numérique et de Photogrammétrie
 * Commission géologique du Canada (c) 2018
 * Ressources naturelles Canada
 * Manages resources and configurations for the application.  must be invoked only once at the 
 * first launch of the service.  If needs the servlet context to load local resources (just to get the real path)
 * The manager also loads a triplestore if required.
 */
public class Manager {
	private Configuration conf = null;
	private TripleStore store = null;
	private TemplateManager template = null;
	private DataManager data = null;
	
	public boolean isInitialised()
	{
		return conf != null && store != null;
	}
	// singleton
	public static class ManagerSingleHolder
	{
		static  Manager instance = new Manager();
	}
	
	// TODO: added synch on the get Instance, but not sure I really need it.
	public static synchronized Manager getInstance()
	{
		return ManagerSingleHolder.instance;
	}
	
	public Configuration getConfiguration()
	{
		return conf;
	}
	
	public TripleStore getTripleStore()
	{
		return store;
	}
	
	public TemplateManager getTemplateManager()
	{
		return template;
	}
	
	public DataManager getDataManager()
	{
		return data;
	}
	
	/**
	 * initialise the manager with configuration values
	 * @param ctx
	 */
	public synchronized void init(ServletContext ctx)
	{
		// will only initialise once
		if (!isInitialised())
		{
			Logger.getAnonymousLogger().log(Level.INFO, "Initialise configuration");
			conf = Configuration.getInstance(ctx);
			Logger.getAnonymousLogger().log(Level.INFO, "Initialise template manager");
			template = TemplateManager.getInstance(ctx); 
			Logger.getAnonymousLogger().log(Level.INFO, "Initialise Data manager");
			data = DataManager.getInstance(ctx);
			Logger.getAnonymousLogger().log(Level.INFO, "Initialise TripleStore");
			store = TripleStoreFactory.createTripleStore(ctx);
			Logger.getAnonymousLogger().log(Level.INFO, "Init done --");

		}
		else
		{
			Logger.getAnonymousLogger().log(Level.WARNING,"Already initialized");
		}
	}
	
	public void terminate()
	{
		conf = null;
		template = null;
		if (store != null) store.close();
		store = null;
		data = null;
		
		
	}
	
}
