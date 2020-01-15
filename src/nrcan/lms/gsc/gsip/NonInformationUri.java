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

import org.apache.http.client.utils.URIBuilder;

import static nrcan.lms.gsc.gsip.Constants.BASE_URI;
import static nrcan.lms.gsc.gsip.Constants.APP_URI;

import nrcan.lms.gsc.gsip.conf.Configuration;

@Path("/id/{seg:.*}")
public class NonInformationUri {
	@Context UriInfo uriInfo;
	@Context ServletContext ctx;
	@GET
	/***
	 * this essentially redirect to "info" page. It just rewrite the URI by
	 *  - strip everything in front of /id/ and replace by BASEURI to match catalog.
	 *     - in productions system, it should actually be the same
	 *  - replace the /id/ by /info/
	 *  - transfer the format(f) and callback parameters
	 *  TODO: callback is probably not useful anymore since we set CORS to *
	 * @param format
	 * @param callback
	 * @return
	 */
	public Response redirectToResource(@QueryParam("f") String f,@QueryParam("format") String format,@QueryParam("callback") String callback)
	{
		if (format==null)
			format = f;
		
		URI newLocation = null;
		// rebuild a /info/ uri from the /id/
		Configuration conf = Manager.getInstance().getConfiguration();
		
		StringBuilder infoUri = new StringBuilder(conf.getParameter(APP_URI) + "/info");
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
	
		
		// transfer format and callback
			URIBuilder ub;
			try {
				ub = new URIBuilder(infoUri.toString());
			if (format != null && format.length() > 0 )
			{
				ub.addParameter("f" , format);
			}
			
			if (callback != null && callback.length() > 0)
			{
				ub.addParameter("callback", callback);
				
			}
			
			newLocation = ub.build();
			
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				return Response.status(400).entity("Invalid URI").type(MediaType.TEXT_PLAIN).build();
			}

	
		
		
		return Response.seeOther(newLocation).build();
	}
	
	

}
