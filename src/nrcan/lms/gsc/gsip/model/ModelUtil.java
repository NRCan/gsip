package nrcan.lms.gsc.gsip.model;

import static nrcan.lms.gsc.gsip.Constants.BASE_URI;
import static nrcan.lms.gsc.gsip.Constants.PERSISTENT_URI;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import nrcan.lms.gsc.gsip.Constants;
import nrcan.lms.gsc.gsip.Manager;
import nrcan.lms.gsc.gsip.conf.Configuration;

public class ModelUtil {
	/**
	 * Just serialize to a string
	 * @param mdl
	 * @param format
	 * @return
	 */
	public static String modelToString(Model mdl, Lang format)
	{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		RDFDataMgr.write(baos, mdl, format);
		try {
			return new String(baos.toByteArray(),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			Logger.getAnonymousLogger().log(Level.SEVERE, "failed to create a String for model in " + format.toString(),e);
			return null;
		}
	}
	
	/**
	 * deal with < and >.  Does not check if inconsistent ie, "<" but no ">", this will generate a SPARQL error anyway
	 * @param resource
	 * @return
	 */
	public static String formatResource(String resource)
	{
		if (resource.startsWith("<") && resource.endsWith(">"))
			return resource;
		else return "<" + resource +">";
	}
	
	/**
	 * rename resource.. not efficient, and maybe can cause problem, but it's for debug.
	 * not expected to be used in a production system
	 * @param m
	 * @param inPrefix
	 * @param outPrefix
	 * @return
	 */
	public static Model alternateResource(Model m, String outPrefix,String inPrefix)
	{
		String syntax = "TURTLE"; 
		StringWriter out = new StringWriter();
		m.write(out, syntax);
		String result = out.toString();
		// replace resource
		String n = result.replaceAll("<"+ inPrefix, "<"+outPrefix);
		Model newModel = ModelFactory.createDefaultModel();
		try {
			RDFDataMgr.read(newModel, IOUtils.toInputStream(n,"UTF-8"),Lang.TURTLE);
			//newModel.read(IOUtils.toInputStream(n, "UTF-8"),null,"TURTLE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to convert Model",e);
		}
		return newModel;

	}
	
	public static String alternateUri(String u,String outPrefix,String inPrefix)
	{
		if (u.startsWith(inPrefix))
		{
			return u.replaceFirst(inPrefix, outPrefix);
		}
		else
			return u;
	}
	
	public static String getAlternateResource(String rs)
	{
		Configuration c = Manager.getInstance().getConfiguration();
		String baseuri = (String) c.getParameter(BASE_URI);
		Object outuri = c.getParameter(PERSISTENT_URI,null); // can be null
		if (!baseuri.equals(outuri) && outuri != null)
			return rs.replace( (String)outuri,baseuri);
		else
			return rs;
	}
	
	/**
	 * serialize JSON with or without callback
	 * @param mdl9
	 * @param callBack
	 * @return
	 */
	public static Response serializeJSONLD(Model mdl,String callBack)
	{
		String pre = "";
		String post = "";
		if (callBack != null && callBack.trim().length() > 0)
		{
			pre = callBack.trim() + "(";
			post = ")";
		}
		StringWriter w = new StringWriter();
		mdl = getAlternateModel(mdl);
		mdl.write(w, "JSON-LD");
		return Response.ok(pre + w.toString() + post).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	/**
	 * This is for debugging or running the system on a alternate site.
	 * It converts the baseURI of resources in 
	 * into a site specific URI (using configuration variable proxdevuri).
	 * This help developers and tester running the system outside the official deployment (on a testserver)
	 * 
	 * This 
	 * 
	 * 
	 * @param mdl
	 * @return a new model with all {baseuri}/ turned into {proxdevuri}/.
	 */
	public static Model getAlternateModel(Model mdl)
	{
		Configuration c = Manager.getInstance().getConfiguration();
		String baseuri = (String) c.getParameter(BASE_URI);
		Object outuri = c.getParameter(PERSISTENT_URI,null); // can be null
		Boolean convert = c.getParameterAsBoolean(Constants.CONVERT_TO_BASEURI, true);
		// need to convert ?
		if (convert && !baseuri.equals(outuri) && outuri != null)
			return  ModelUtil.alternateResource(mdl, baseuri, (String)outuri);
		else
			return mdl;
		

	}

}
