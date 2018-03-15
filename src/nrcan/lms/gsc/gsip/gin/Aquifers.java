package nrcan.lms.gsc.gsip.gin;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("data/aquifers/{structure}/{semantic}/{source}/{aquifer}/{ctx}")
public class Aquifers {
	public static final String WFS = "http://gin.gw-info.net/GinService/wfs/gwie?REQUEST=GetFeature&VERSION=2.0.0&SERVICE=WFS&STOREDQUERY_ID=urn:ogc:def:query:OGC-WFS::GetFeatureById&ID=Richelieu_contexte_";
	public static final String HTML = "http://gin.gw-info.net/service/api_ngwds:gin2/en/data/standard.hydrogeologicunit.html?id=";
	@GET
	@Produces(MediaType.TEXT_XML)
	public Response getGML(@PathParam("aquifer") String aquifer,@PathParam("ctx") String ctx)
	{
		try {
			return Response.seeOther(new URI(WFS + ctx)).type(MediaType.TEXT_XML).build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			return Response.serverError().entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getHTML(@PathParam("aquifer") String aquifer,@PathParam("ctx") String ctx)
	{
		
		
		try {
			return Response.seeOther(new URI(HTML + swap(ctx))).type(MediaType.TEXT_HTML).build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			return Response.serverError().entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}
	
	public static String swap(String ctx)
	{
		if ("5".equals(ctx)) return "2";
		if ("4".equals(ctx)) return "5";
		if ("3".equals(ctx)) return "3";
		if ("2".equals(ctx)) return "4";
		return ctx;
	}
}
