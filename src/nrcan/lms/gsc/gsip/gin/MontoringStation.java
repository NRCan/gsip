package nrcan.lms.gsc.gsip.gin;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// test with station = prj_27.53529

@Path("data/monitoring/{structure}/{semantic}/{source}/{station}/{ctx}")
public class MontoringStation {
	public static final String HTML = "http://s-stf-ngwd.nrn.nrcan.gc.ca:8085/cocoon/gin/sos2/timeseries/natres?ID=";
	public static final String WFS = "http://s-stf-ngwd.nrn.nrcan.gc.ca:8085/GinService/sos/gw?REQUEST=GetObservation&VERSION=2.0.0&SERVICE=SOS&offering=GW_LEVEL&observedProperty=urn:ogc:def:phenomenon:OGC:1.0.30:groundwaterlevel&featureOfInterest=";
	@GET
	@Produces(MediaType.TEXT_XML)
	public Response getGML(@PathParam("station") String station)
	{
		try {
			return Response.seeOther(new URI(WFS + station)).type(MediaType.TEXT_XML).build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			return Response.serverError().entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getHTML(@PathParam("station") String station)
	{
		
		
		try {
			return Response.seeOther(new URI(HTML + station)).type(MediaType.TEXT_HTML).build();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			return Response.serverError().entity(e.getMessage()).type(MediaType.TEXT_PLAIN).build();
		}
	}

}
