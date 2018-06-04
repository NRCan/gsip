package nrcan.lms.gsc.gsip.triple;

import nrcan.lms.gsc.gsip.Manager;
import static nrcan.lms.gsc.gsip.Constants.TRIPLE_STORE;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;

/**
 * Create a TripleStore based on configuration.  So far, this only create a link to a remote Fuseki
 * server of create a local server with data provided in a folder
 * rule:
 * 	if the configuration starts with http|s://, it's a remote fuseki server
 *  otherwise, it's an internal server
 *  if the configuration has the form "folder/folder", it's a relative path to context that points to a folder full of tripled
 *  if the configuration has the form "/folder..." it's a absolute path
 *  
 *  folder should contain RDF datasets (.rdf, .ttl, etc.).  All "non-RDF" file will be skipped
 *  if a file .rdf or .ttl has an error, error will be logged, and skipped, but the service will start nonetheless
 *  with whatever consequence.
 *  
 * @author Eric Boisvert
 * Laboratoire de Cartographie Numérique et de Photogrammétrie
 * Commission géologique du Canada (c) 2018
 * Ressources naturelles Canada
 */
public class TripleStoreFactory {

	public static TripleStore createTripleStore(ServletContext context)
	{
		// get the configuration, if not configuration available, just use the default value in old version of GSIP (not a very good design, but hey)
		String tpconf = (String) Manager.getInstance().getConfiguration().getParameter(TRIPLE_STORE, RemoteStore.defaultSparqlEndpoint);
		if (tpconf != null)
		{
			if (tpconf.startsWith("http"))
				return new RemoteStore(tpconf);
			
			// check if it's a local resource (webapp)
			if (tpconf.startsWith("webapp:"))
			{
				
				String p = context.getRealPath(tpconf.replaceFirst("webapp:", "/"));
				//String p = context.getClass().getResource(tpconf.replaceFirst("webapp:", "")).getPath();
				if (p == null)
				{
					Logger.getAnonymousLogger().log(Level.SEVERE, "could not get real path for " + tpconf.replaceFirst("webapp:", ""));
				}
				return new EmbeddedStore(p);
				
			}
			
			//TODO: improve security / error control
			// some sort of absolute path
			return new EmbeddedStore(tpconf);
		}
		else
		{
			Logger.getAnonymousLogger().log(Level.SEVERE,"No triple store");
			
		}

		return null;  // did not go well
	}

}
