package nrcan.lms.gsc.gsip.geo;
/**
 * Access a repository of spatial datasets
 * This version only access spatial lite repositories, could be extended to read postgres
 * @author Eric Boisvert
 * Laboratoire de Cartographie Numérique et de Photogrammétrie
 * Commission géologique du Canada (c) 2018
 * Ressources naturelles Canada
 */

import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("geo")
public class Spatial {
	@Path("local/{db}/{table}")
	public Response getSpatialiteTable(@QueryParam("f") String format)
	{
		
		
		return Response.serverError().build();
	}
	@Path("local/{db}/{table}/{id}")
	public Response getSpatialLiteRecord(@QueryParam("f") String format)
	{
		
		return Response.serverError().build();
	}
	
	/**
	 * 
	 * TODO
	 * remote/{connection}/{table}
	 */

}
