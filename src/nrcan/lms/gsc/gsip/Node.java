package nrcan.lms.gsc.gsip;

import static nrcan.lms.gsc.gsip.Constants.APPLICATION_RDFXML;
import static nrcan.lms.gsc.gsip.Constants.TEXT_TURTLE;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.ws.rs.core.UriInfo;

import org.apache.http.HttpStatus;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;
import nrcan.lms.gsc.gsip.model.ModelUtil;
import nrcan.lms.gsc.gsip.template.TemplateManager;
import nrcan.lms.gsc.gsip.triple.TripleStore;
import nrcan.lms.gsc.gsip.util.MediaTypeUtil;
import nrcan.lms.gsc.gsip.util.MediaTypeUtil.InfoOutputFormat;

@Path("node")
public class Node {
	@Context UriInfo uriInfo;
	@GET
	@Path("{sub}")
	/**
	 * return an arbitrary set of node defined on the sparql query in a template
	 * @param sub : path is node/<sub> , sub will become the name of the template (node_<sub>.fht)
	 * @param callback
	 * @param accepts
	 * @param format
	 * @return
	 */
	public Response getNodeData(@PathParam("sub") String sub,@QueryParam("callback") String callback,@HeaderParam("Accept") String accepts,@QueryParam("f") String format)
	{
		InfoOutputFormat of = MediaTypeUtil.getOutputFormat(format, accepts);
		
		// no suitable format
		
		if (of == InfoOutputFormat.ioUnknown)
		{
			String message = format!=null?format +" extension is not supported : use html/ttl/rdf/xml/json":"No acceptable format found";
			return Response.status(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE).entity(message).build();
		}

		// at the point, we have a format
		try
		{
		String sparql = this.constructSparql(null,"node_" + sub+".ftl");
		//Logger.getAnonymousLogger().log(Level.INFO,sparql);
		// server is specified in servlet initialisation

		// get model from triple store
		TripleStore j = Manager.getInstance().getTripleStore();
		Model storedModel = j.getSparqlConstructModel(sparql);
		switch(of)
		{
			case ioTURTLE : return serializeModel(storedModel,Lang.TURTLE,TEXT_TURTLE);
			case ioRDFXML : return serializeModel(storedModel,Lang.RDFXML,APPLICATION_RDFXML);
			case ioJSONLD : return ModelUtil.serializeJSONLD(storedModel,callback);
			case ioXML : return serializeModel(storedModel,Lang.RDFXML,MediaType.TEXT_XML);

		}
		
		}
		catch(Exception ex)
		{
			// boom, return an error message
			Response.status(HttpStatus.SC_BAD_REQUEST).entity("Bad request for \n" + ex.getMessage()).build();
		}
		
		
		return Response.status(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE).entity(format +" extension is not supported : use html/ttl/rdf/xml/json").build();

	}
	
	
	/**
	 * return a transformed template
	 * @param p parameters passed to the template
	 * @param template template name
	 * @return
	 * @throws TemplateNotFoundException
	 * @throws MalformedTemplateNameException
	 * @throws ParseException
	 * @throws IOException
	 * @throws TemplateException
	 */
	private String constructSparql(Map<String,Object> p,String template) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException
	{
		TemplateManager tm = TemplateManager.getInstance();
		return tm.transform(p==null?new HashMap<String,Object>():p,template);
		
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
	

}
