package nrcan.lms.gsc.gsip.template;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;

import freemarker.core.ParseException;
import freemarker.template.Configuration;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import nrcan.lms.gsc.gsip.data.MatchType.Source;

import java.io.StringWriter;
import java.io.Writer;

public class TemplateManager {
	public static final String DYN_CONF = "dynamic/conf.xml";
	private Configuration freemarkerConfiguration;
	private TemplatesType dynamicTemplates;
	private boolean initialised = false;
	private void init(ServletContext context)
	{
		freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_23);
		if (context != null)
		{
		freemarkerConfiguration.setServletContextForTemplateLoading(context, "templates");
		freemarkerConfiguration.setDefaultEncoding("UTF-8");
		
		initialised = true;
		Logger.getAnonymousLogger().log(Level.INFO, "Template initialised");
		}
		else
			Logger.getAnonymousLogger().log(Level.WARNING,"## context is null");
		
		// load dynamic templates
		try {
			loadDynamicConfiguration(context);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			Logger.getAnonymousLogger().log(Level.SEVERE, "failed to load dynamic configuration at " + DYN_CONF,e);
		}
		
	}
	
	
	
	/** 
	 * load the dynamic template configuration
	 * shameless copy-paste from https://dzone.com/articles/using-jaxb-generate-java
	 * @throws JAXBException
	 */
	private void loadDynamicConfiguration(ServletContext ctx) throws JAXBException
	{
		
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
	    JAXBElement<TemplatesType> unmarshalledObject = 
	            (JAXBElement<TemplatesType>)unmarshaller.unmarshal(
	                ctx.getResourceAsStream(DYN_CONF));
	    dynamicTemplates = unmarshalledObject.getValue();
		
	}
	
	
	
	private TemplateManager()
	{
		initialised = false;
	}
	
	public static class TemplateSingleHolder
	{
		static TemplateManager instance = new TemplateManager();
	}
	
	// this is synch because you don't want a single thread to start initialisation while it's initializing
	public static synchronized TemplateManager getInstance(ServletContext ctx)
	{
		if (!TemplateSingleHolder.instance.initialised)
			TemplateSingleHolder.instance.init(ctx);
		return TemplateSingleHolder.instance;
	}
	
	// check if initialised, of not, return null
	public static TemplateManager getInstance()
	{
		if (TemplateSingleHolder.instance.initialised)
			return TemplateSingleHolder.instance;
		else
			return null;
	}
	
	
	public InputStream getGraph(Map<String,Object> parameters, String template) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		Template t = freemarkerConfiguration.getTemplate(template);
		StringWriter sw = new StringWriter();
		t.process(parameters, sw);
		return IOUtils.toInputStream(sw.toString(), "UTF-8");
		
	}
	
	public String transform(Map<String,Object> parameters,String template) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		Template t = freemarkerConfiguration.getTemplate(template);
		if (t == null)
			return null;
		StringWriter sw = new StringWriter();
		t.process(parameters, sw);
		return sw.toString();
	}
	
	public boolean templateExists(String templatefile)
	{
		Template t;
		try {
			t = freemarkerConfiguration.getTemplate(templatefile);
		} catch (IOException e) {
			// if something wrong happens, we'll assume it's not there
			return false;
		}
		return (t != null);
			
	}
	
	public String getMatchingTemplate(String uri,boolean hasEntry)
	{
		
		for(TemplateType t:dynamicTemplates.template)
		{
			if (t.isRequiresEntry() && !hasEntry) continue;
			//TODO: create a precompiled regexp instead of compiling every time
			if (Pattern.matches(t.pattern,uri))
				return t.template;
			
		}
		return null;
	}



	/**
	 * apply a template 
	 * @param source
	 * @param parameters
	 * @return
	 * @throws IOException 
	 * @throws TemplateException 
	 */
	public String applyTemplate(String source, Map<String, String> parameters) throws IOException, TemplateException {
		Template t = new Template("x",new StringReader(source),this.freemarkerConfiguration);
		Writer out = new StringWriter();
		t.process(parameters, out);
		return out.toString();
		
	}
	
	
	
	

}
