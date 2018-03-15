package nrcan.lms.gsc.gsip;

import java.io.File;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.http.HttpStatus;

@Path("app")
public class Application {
	public static final Pattern bad = Pattern.compile("\\W|^$");
	@Context ServletContext context;
	
//TODO: there is a better way to do this using web.xml 
	
	@Path("{folder}/{resource}.png")
	@Produces("image/png")
	@GET
	public Response getImage(@PathParam("folder") String folder,@PathParam("resource") String resource)
	{
		if (isBad(folder) || isBad(resource))
			return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		
		// else, we have a valid file
		try{
			if (!resourceExists("/app/" + folder+"/"+resource+".png"))
				return Response.status(HttpStatus.SC_NOT_FOUND).build();
			return Response.ok(context.getResourceAsStream("/app/" + folder+"/"+resource+".png")).build();
			}
			catch(Exception ex)
			{
				return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
			}
		
	}
	
	@Path("{folder}/{resource}.ico")
	@Produces("image/x-icon")
	@GET
	public Response getImageIcon(@PathParam("folder") String folder,@PathParam("resource") String resource)
	{
		if (isBad(folder) || isBad(resource))
			return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		
		// else, we have a valid file
		try{
			if (!resourceExists("/app/" + folder+"/"+resource+".ico"))
				return Response.status(HttpStatus.SC_NOT_FOUND).build();
			return Response.ok(context.getResourceAsStream("/app/" + folder+"/"+resource+".ico")).build();
			}
			catch(Exception ex)
			{
				return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
			}
		
	}
	
	@Path("{folder}/{resource}.{ext}")
	@GET
	public Response getResource(@PathParam("folder") String folder,@PathParam("resource") String resource,@PathParam("ext") String ext)
	{
		if (isBad(folder) || isBad(resource) || isBad(ext))
			return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		String mediatype = "text/plain";
		if ("css".equals(ext)) mediatype = "text/css";
		if ("js".equals(ext)) mediatype = "application/javascript";
		
		// else, we have a valid file
		try{
			if (!resourceExists("/app/" + folder+"/"+resource+"."+ext))
				return Response.status(HttpStatus.SC_NOT_FOUND).build();
			return Response.ok(context.getResourceAsStream("/app/" + folder+"/"+resource+"."+ext)).type(mediatype).build();
			}
			catch(Exception ex)
			{
				return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
			}
		
	}
	


	
	@Path("{html}.html")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getHtmlResource(@PathParam("html") String page)
	{
		if (isBad(page))
			return Response.status(HttpStatus.SC_BAD_REQUEST).build();
		try{
			if (!resourceExists("/app/" +page + ".html"))
				return Response.status(HttpStatus.SC_NOT_FOUND).build();
			return Response.ok(context.getResourceAsStream("/app/" +page + ".html")).type(MediaType.TEXT_HTML_TYPE).build();
			}
			catch(Exception ex)
			{
				return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).build();
			}
		
	}
	
	// check if a file with this name exists in this path
	private boolean resourceExists(String path)
	{
		File file = new File(context.getRealPath(path));
		return (file.exists());
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
	

}
