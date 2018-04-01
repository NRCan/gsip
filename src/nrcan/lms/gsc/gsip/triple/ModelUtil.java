package nrcan.lms.gsc.gsip.triple;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.jena.rdf.model.Model;
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

}
