package nrcan.lms.gsc.gsip;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import static nrcan.lms.gsc.gsip.Constants.BASE_URI;

import nrcan.lms.gsc.gsip.conf.Configuration;

@Path("/id/{seg:.*}")
public class NonInformationUri {
	@Context UriInfo uriInfo;
	@Context ServletContext ctx;
	@GET
	public Response redirectToResource(@QueryParam("f") String format,@QueryParam("callback") String callback)
	{
		URI newLocation = null;
		// rebuild a /info/ uri 
		Configuration conf = Configuration.getInstance(ctx);
		
		StringBuilder infoUri = new StringBuilder(conf.getParameter(BASE_URI) + "/info");
		boolean first = true;
		for(PathSegment segment: uriInfo.getPathSegments())
		{
			if (first)
			{
				// removing /id/ 
				first = false;
				continue;
			}
			infoUri.append("/" + segment.getPath());
		}
		
		//Logger.getAnonymousLogger().log(Level.INFO, infoUri.toString());
		
		//TODO: check if asking non hypermedia format, if so, redirect to this resource if it's the only one available
	
		try {
			String nl = infoUri.toString();
			StringBuilder sb = new StringBuilder();
			String f="";
			
			if (format != null && format.length() > 0 )
			{
				sb.append("f=" + format);
			}
			
			if (callback != null && callback.length() > 0)
			{
				if (sb.length() > 0)
					sb.append("&callback="+callback);
				else
					sb.append("callback="+callback);
			}
			
			if (sb.length() > 0)
			{
				if (nl.endsWith("?") || nl.endsWith("&"))
					f = sb.toString();
				else
					if (nl.contains("?"))
						f = "&" + sb.toString();
					else
						f = "?" + sb.toString();
			}

				

				newLocation = new URI(nl+f);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			return Response.status(400).entity("Invalid URI").type(MediaType.TEXT_PLAIN).build();
		}
		
		
		return Response.seeOther(newLocation).build();
	}
	
	

}
