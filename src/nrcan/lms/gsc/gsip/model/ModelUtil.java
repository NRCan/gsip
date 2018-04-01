package nrcan.lms.gsc.gsip.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

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
	public static Model alternateResource(Model m, String inPrefix,String outPrefix)
	{
		String syntax = "TURTLE"; 
		StringWriter out = new StringWriter();
		m.write(out, syntax);
		String result = out.toString();
		// replace resource
		String n = result.replaceAll("<" + inPrefix, "<"+outPrefix);
		Model newModel = ModelFactory.createDefaultModel();
		try {
			newModel.read(IOUtils.toInputStream(n, "UTF-8"),null,"TURTLE");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to convert Model",e);
		}
		return newModel;

	}
	
	public static String alternateUri(String u,String inPrefix,String outPrefix)
	{
		if (u.startsWith(inPrefix))
		{
			return u.replaceFirst(inPrefix, outPrefix);
		}
		else
			return u;
	}

}
