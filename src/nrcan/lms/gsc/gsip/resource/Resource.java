package nrcan.lms.gsc.gsip.resource;


import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

import static nrcan.lms.gsc.gsip.Constants.APPLICATION_GEOJSON;

import java.io.File;
import java.util.regex.Pattern;

/**
 * simple access point to deliver simple static resouces
 * @author eboisver
 *
 */

@Path("resources/{category}/{item}")
public class Resource {
	public static final Pattern bad = Pattern.compile("\\W|^$");
	// ticket 3168  use "[_a-zA-Z0-9\\-\\.]+" ?
	
	
	@Context ServletContext context;
	@GET
	@Produces({MediaType.APPLICATION_JSON,APPLICATION_GEOJSON})
	public Response getJsonResource(@PathParam("category") String folder,@PathParam("item") String item)
	{
		// only accept word
		if (isBad(folder) || isBad(item))
			return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		
		// TODO , no secure, will return anything in the resource folder
		// just serialize the content of the folder	
		try{
		if (!resourceExists("/resources/" + folder+"/"+item+".json"))
			return Response.status(HttpStatus.SC_NOT_FOUND).build();
		return Response.ok(context.getResourceAsStream("/resources/" + folder+"/"+item+".json")).build();
		}
		catch(Exception ex)
		{
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
		}
	}
	
	
	// check if non word characters
	// stolen from https://stackoverflow.com/a/4434174/8691687 
	private boolean isBad(String suspect)
	{
		if (bad.matcher(suspect).find()) {
			  return true;
			} else {
			  return false;
			}
	}
	
	// check if a file with this name exists in this path
	private boolean resourceExists(String path)
	{
		File file = new File(context.getRealPath(path));
		return (file.exists());
	}

}
