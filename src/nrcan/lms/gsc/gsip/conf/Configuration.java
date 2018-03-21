package nrcan.lms.gsc.gsip.conf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import nrcan.lms.gsc.gsip.conf.ParametersType.Parameter;





/**
 * Various configuration options, taken from /conf/configuration.xml
 * @author eboisver
 *
 */
public class Configuration {
	public static final String DEFAULT_LANGUAGE = "en";
	public static final String CONF_FILE = "conf/configuration.xml";
	private Hashtable<String,String> formatToMime = null;
	private Hashtable<String,String> equivalentMime = null;
	private Hashtable<String,Object> parameters = null;
	private ConfigurationType conf = null;
	
	private Configuration()
	{
		
	}
	
	public static class ConfigurationSingleHolder
	{
		static Configuration instance = new Configuration();
	}
	
	/**
	 * get a single instance when the servlet context is known
	 * 
	 * @param srv
	 * @return
	 */
	public static Configuration getInstance(ServletContext srv)
	{
		//TODO: bad design, the servlet context exists during a request, I should find a way to get
		// this information without 
		if (ConfigurationSingleHolder.instance.conf == null)
			try {
				ConfigurationSingleHolder.instance.init(srv);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.SEVERE,"Failed to load configuration",e);
				return null;
			}
		return ConfigurationSingleHolder.instance;
	}
	
	
	public Object getParameter(String param)
	{
		return parameters.get(param);
	}
	public Object getParameter(String param,Object defaultValue)
	{
		Object v = getParameter(param);
		return v==null?defaultValue:v;
	}
	/** we assume it has been already configured, because it will be invoked from the app
	 * 
	 * @return
	 */
	public static Configuration getInstance()
	{
		if (ConfigurationSingleHolder.instance.conf != null)
			return 
					ConfigurationSingleHolder.instance;
		else
			return null; // this will generate a npe
	}
	
	private void init(ServletContext ctx) throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    JAXBElement<ConfigurationType> unmarshalledObject = 
	            (JAXBElement<ConfigurationType>)unmarshaller.unmarshal(
	                ctx.getResourceAsStream(CONF_FILE));
	    conf = unmarshalledObject.getValue();
	    
	    // load the types
	    
	    this.formatToMime = new Hashtable<String,String>();
	    this.equivalentMime = new Hashtable<String,String>();
	    for(TypeType tp:conf.types.type)
	    {
	    	if (tp.formats != null)
	    		for(String f:tp.formats.split(";"))
	    		{
	    			formatToMime.put(f, tp.mimeType);
	    		}
	    	// check if equivalenet
	    	if (tp.sameAs !=  null)
	    	{
	    		equivalentMime.put(tp.mimeType, tp.sameAs);
	    	}
	    }
	    
	    // holds configuration parameters
	    
	    parameters = new Hashtable<String,Object>(); 
	    // read all the parameters, for now, they are just text
	   
	    for(Parameter p : conf.parameters.parameter)
	    {
	    	parameters.put(p.name, p.value);
	    }
	    
	   
	}
	
	/**
	 * return the mime type this format is supposed to be
	 * @param f
	 * @return
	 */
	public MediaType getMimeFromFormat(String f)
	{
		String media =  formatToMime.get(f); 
		return MediaType.valueOf(media);
	}
	

	/**
	 * check the list of equivalence, if any, otherwise, return the same
	 * @param m
	 * @return
	 */
	public MediaType getOfficialMimeType(MediaType m)
	{
		String t = m.getType();
		String s = m.getSubtype();
		String key = t + (s!=null&&s.length() > 0?"/"+s:"");
		if (this.equivalentMime.containsKey(key))
			return MediaType.valueOf(equivalentMime.get(key));
		else
			return m;
	}


	public Hashtable<String,Object> getParameters() {
		// TODO Auto-generated method stub
		return parameters;
	}


	/**
	 * get the default languages, as defined in the configuration (defaultLanguages)
	 * @param l
	 * @return
	 */
	public boolean isValidLanguage(String l) {
		// TODO Auto-generated method stub
		return getDefaultLanguages().contains(l);
	}
	
	public List<String> getDefaultLanguages()
	{
		return new ArrayList<String>(
				Arrays.asList(
							((String)getParameter("defaultLanguages",DEFAULT_LANGUAGE)).split(",")
							)
				);
	}


	public String getDefaultLanguage() {
		return (String) getParameter("defaultLanguage",DEFAULT_LANGUAGE);
	}
}
