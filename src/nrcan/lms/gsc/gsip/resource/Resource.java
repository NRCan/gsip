package nrcan.lms.gsc.gsip.resource;


import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;

import nrcan.lms.gsc.gsip.Constants;
import nrcan.lms.gsc.gsip.Manager;

import static nrcan.lms.gsc.gsip.Constants.APPLICATION_GEOJSON;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

/**
 * simple access point to deliver simple static resouces.
 * Not mapped as "default" servlet as this is a placeholder for more complex resource service 
 * TODO: implement alternate model rules for URI in JSON resources.
 * @author eboisver
 *
 */


@Path("resources/{category}/{item}")
public class Resource {
	// this is probably overkill since the matching is now done with Jersey, so there are no way (?) to escalate the path (ie ../../)
	// ticket 3168  use "[_a-zA-Z0-9\\-\\.]+" ?
	//public static final Pattern bad = Pattern.compile("\\W|^$");

	
	
	@Context ServletContext context;
	@GET
	@Produces({MediaType.APPLICATION_JSON,APPLICATION_GEOJSON})
	public Response getJsonResource(@PathParam("category") String folder,@PathParam("item") String item)
	{
		// only accept word
		// TODO: not sure this is even useful...
		//if (isBad(folder) || isBad(item))
		//	return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		
		// TODO , not secure, will return anything in the resource folder
		// just serialize the content of the folder	
		try{
		if (!resourceExists("/resources/" + folder+"/"+item+".json"))
			return Response.status(HttpStatus.SC_NOT_FOUND).build();
		// convert persistentUri to baseURI is required
		if (needConversion())
		{
			return Response.ok(getConvertedResource("/resources/" + folder+"/"+item+".json")).type("application/vnd.geo+json").build();
		}
		else return Response.ok(context.getResourceAsStream("/resources/" + folder+"/"+item+".json")).type("application/vnd.geo+json").build();
		}
		catch(Exception ex)
		{
			Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to serialise internal resource " + folder+"/"+item+".json" , ex);
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
		}
	}
	
	private boolean needConversion()
	{
		boolean doConvert = Manager.getInstance().getConfiguration().getParameterAsBoolean(Constants.CONVERT_TO_BASEURI,true);
		if (!doConvert) return false; // end of the story
		// get baseUri and persistent URI and check if they are the same
		String persistentUri = Manager.getInstance().getConfiguration().getParameterAsString(Constants.PERSISTENT_URI, null);
		if (persistentUri == null) return false; // end of the story
		String baseUri = Manager.getInstance().getConfiguration().getParameterAsString(Constants.BASE_URI,context.getContextPath());
		return (!baseUri.equals(persistentUri));
	}
	
	private String getConvertedResource(String path) throws IOException
	{
		
		String persistentUri = Manager.getInstance().getConfiguration().getParameterAsString(Constants.PERSISTENT_URI, null);

		String baseUri = Manager.getInstance().getConfiguration().getParameterAsString(Constants.BASE_URI,context.getContextPath());
		if (path.endsWith(".json"))
		{
			//TODO: this is a terrible hack
			persistentUri = persistentUri.replace("/", "\\/");
			baseUri = baseUri.replace("/", "\\/");
			
		}
		String text = IOUtils.toString(context.getResourceAsStream(path),"UTF-8");
		return StringUtils.replace(text,persistentUri , baseUri);

	}
	
	
	// check if non word characters
	// stolen from https://stackoverflow.com/a/4434174/8691687 
	/**
	private boolean isBad(String suspect)
	{
		if (bad.matcher(suspect).find()) {
			  return true;
			} else {
			  return false;
			}
	}
	**/
	
	// check if a file with this name exists in this path
	private boolean resourceExists(String path)
	{
		File file = new File(context.getRealPath(path));
		return (file.exists());
	}

}
