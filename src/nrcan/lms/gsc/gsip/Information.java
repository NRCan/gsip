package nrcan.lms.gsc.gsip;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import static nrcan.lms.gsc.gsip.Constants.APPLICATION_RDFXML;
import static nrcan.lms.gsc.gsip.Constants.APPLICATION_TURTLE;
import static nrcan.lms.gsc.gsip.Constants.TEXT_TURTLE;
import static nrcan.lms.gsc.gsip.Constants.APP_URI;
import static nrcan.lms.gsc.gsip.Constants.PERSISTENT_URI;


import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import nrcan.lms.gsc.gsip.conf.Configuration;
import nrcan.lms.gsc.gsip.model.ModelUtil;
import nrcan.lms.gsc.gsip.model.ModelWrapper;
import nrcan.lms.gsc.gsip.template.TemplateManager;
import nrcan.lms.gsc.gsip.triple.TripleStore;
import nrcan.lms.gsc.gsip.triple.RemoteStore;
import nrcan.lms.gsc.gsip.util.MediaTypeUtil;
import nrcan.lms.gsc.gsip.util.MediaTypeUtil.InfoOutputFormat;
import nrcan.lms.gsc.gsip.util.QuantifiedMedia;

import static nrcan.lms.gsc.gsip.Constants.BASE_URI;

/** new version of the information page (the page that processes /info/ 
 * 
 * @author eboisver
 *
 */


@Path("info/{seg:.*}")
public class Information {
	
	
	
	private static final String NIRMIR = "_mir2nir.ftl";
	@Context UriInfo uriInfo;
	@Context ServletContext context;
	@Context HttpHeaders headers;
	@Context HttpServletRequest request;
	
	@GET
	@Produces({MediaType.TEXT_HTML,MediaType.TEXT_XML,MediaType.APPLICATION_JSON,APPLICATION_RDFXML,APPLICATION_TURTLE,TEXT_TURTLE})
	public Response getResource(@QueryParam("format") String format,@QueryParam("f") String f,@QueryParam("callback") String callback,@HeaderParam("Accept") String accepted,@QueryParam("lang") String lang)
	{
		
		if (format == null)
			format = f;
		
		String locale = RequestUtil.getLocale(lang,request.getLocale());
			
		/** all media types works pretty much the same way, except 
		 - HTML that need an extra template
		 - JSON and JSONLD that might require a call back
		 format override (f=) will override the header
		  */
		
		InfoOutputFormat of = MediaTypeUtil.getOutputFormat(format, accepted);
		
		if (of == InfoOutputFormat.ioUnknown)
		{
			String message = format!=null?format +" extension is not supported : use html/ttl/rdf/xml/json":"No acceptable format found";
			return Response.status(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE).entity(message).build();
		}


		// at this point, we have a ioType or it 404'ed
		
		// we need to fetch back the original uri, wich is just the current URL, but replacing the /info/ by /id/
		// TODO: not very robust, but this info page MUST be about an /id/ page with the same structure
		
		String idUri = this.getIdResource(uriInfo);
		// get a model from the sparql endpoint
		try
		{
		String sparql = constructSparql(idUri);
		//Logger.getAnonymousLogger().log(Level.INFO,sparql);
		// server is specified in servlet initialisation

		// get model from triple store
		TripleStore j = Manager.getInstance().getTripleStore();
		Model storedModel = j.getSparqlConstructModel(sparql);
		// this URI might match a pattern in the template manager
		String matchedTemplate = TemplateManager.getInstance().getMatchingTemplate(idUri,!storedModel.isEmpty());
		if (matchedTemplate != null)
		{
			Map<String,Object> p = getParameters(uriInfo,idUri); // build from already parsed information
			// add info about emptyness
			p.put("hasStatements", storedModel.isEmpty()?"false":"true");
			p.put("model", new ModelWrapper(storedModel,idUri));
			storedModel.read(TemplateManager.getInstance().getGraph(p, matchedTemplate),null,"TURTLE");
		}
		
		// if the model is still empty, return a 404
		
		if (storedModel.isEmpty()) 
				return Response.status(HttpStatus.SC_NOT_FOUND).entity("resource " + idUri + " not found").build();
		//TODO: add a validation to check if persitentURI is defined in a prefix (not sure what will happen at this point)
		// serialize
		switch(of)
		{
			case ioTURTLE : return serializeModel(storedModel,Lang.TURTLE,TEXT_TURTLE);
			case ioRDFXML : return serializeModel(storedModel,Lang.RDFXML,APPLICATION_RDFXML);
			case ioJSONLD : return ModelUtil.serializeJSONLD(storedModel,callback);
			case ioXML : return serializeModel(storedModel,Lang.RDFXML,MediaType.TEXT_XML);
			default : return serializeHTML(storedModel,idUri,locale);
		}
		
		}
		catch(Exception ex)
		{
			// boom, return an error message
			Response.status(HttpStatus.SC_BAD_REQUEST).entity("Bad request for " + idUri + "\n" + ex.getMessage()).build();
		}
		
		
		// serialize to the correct format
		
		
		return null;
	}
	
	
	
	/**
	 * serialize in HTML using FreeMarker
	 * @param model
	 * @return
	 */
	private Response serializeHTML(Model model,String resource,String locale)
	{
		ModelWrapper mw = new ModelWrapper(ModelUtil.getAlternateModel(model),ModelUtil.getAlternateResource(resource));
		// get the template used to create 
		String htmlTemplate = Configuration.getInstance().getHtmlTemplate(mw.getContextResourceUri());
		String out = null;
		try{
			Map<String,Object> p = new HashMap<String,Object>();
			p.put("host",Configuration.getInstance().getParameter("gsip"));
			// note: HTML template always work with persistent model, the conversion is done in the wrapper
			// TODO: what a mess
			p.put("model", mw);
			p.put("locale",locale);
			// get all configuration variables
			p.putAll(Manager.getInstance().getConfiguration().getParameters());
			out = TemplateManager.getInstance().transform(p, htmlTemplate);
		}
		catch(Exception ex)
		{
			Logger.getAnonymousLogger().log(Level.SEVERE, "failed to serialize ",ex );
			Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity("Failed to create HTML page\n"+ex.getMessage()).build();
		}
		
		//TODO: serialize the output as HTML document
		if (out == null)
		{
			return Response.noContent().build();
		}else
			
			return Response.ok(out).type(MediaType.TEXT_HTML_TYPE).build();

	}
	
	
	
	
	

	
	private Response serializeModel(Model mdl, Lang format, String mimetype)
	{
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException
			{
			
				
				RDFDataMgr.write(os,ModelUtil.getAlternateModel(mdl),format);
			}
		};
		
		return Response.ok(stream).type(mimetype).build();
		
			
	}
	
	
	
	/**
	 * create a series of parameters
	 * @param i
	 * @return
	 */
	private Map<String,Object> getParameters(UriInfo i,String idUri)
	{
		Map<String,Object> mp = new HashMap<String,Object>();
		mp.put("resource",idUri);
		// decompose the uriInfo in parts
		int p=1;
		StringBuilder path = new StringBuilder("id");
		boolean first = true;
		for(PathSegment segment: uriInfo.getPathSegments())
		{
			if (first) {first = false; continue;}
			mp.put("p"+(p++), segment.getPath());
			path.append("/" + segment.getPath());
		}
		mp.put("p0", path.toString()); // the whole path , starting from id is in item 0 
		
		// get the configuration parameters
		mp.putAll(Configuration.getInstance().getParameters());
		
		return mp;
	}
	
	public  String getIdResource(UriInfo uriInfo)
	{
		String userDefinedNir = applyNirTemplate(uriInfo.getAbsolutePath().toString());
		if (userDefinedNir != null)
		{
			return userDefinedNir.trim();
		}
			
		// get the /id/ resource and convert to persistant
		String persistentUri = Manager.getInstance().getConfiguration().getParameterAsString(PERSISTENT_URI, BASE_URI);
		// this one is called by /info/, but the real NIR is /id/
		StringBuilder infoUri = new StringBuilder(persistentUri + "/id");
		boolean first = true;
		for(PathSegment segment: uriInfo.getPathSegments())
		{
			if (first) {first = false; continue;}
			infoUri.append("/"+segment.getPath());
		}
		//Logger.getAnonymousLogger().log(Level.INFO, "processed URI:" + uriInfo.getPath());
		//Logger.getAnonymousLogger().log(Level.INFO,"/id/ URI:" + infoUri);
		return infoUri.toString();
		// 
	}
	
	
	private String applyNirTemplate(String mir)
	{
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("mir", mir);
		
		try {
			return TemplateManager.getInstance().transform(parameters,NIRMIR);
		} catch (IOException | TemplateException e) {
			return null;
		}

	}
	
	
	// Added supress warning because compiled expected generics (<T> templates) and
	// Jena does not use any in the example code.  need to test it, but for now just shut the compiler up
	@SuppressWarnings("unchecked")
	public  static String constructSparql(String resource) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		TemplateManager tm = TemplateManager.getInstance();
		@SuppressWarnings("rawtypes")
		Map p = new HashMap<String,Object>();
		p.put("resource", resource);
		return tm.transform(p,"describe.ftl" );
		
	}
	
	

}
