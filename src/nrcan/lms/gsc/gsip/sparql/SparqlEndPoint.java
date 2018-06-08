package nrcan.lms.gsc.gsip.sparql;

import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;

import nrcan.lms.gsc.gsip.Manager;

/**
 * sparql endpoint 
 * @author eboisver
 *
 */
@Path("sparql")
public class SparqlEndPoint {
	
	@GET
	@Produces("application/rdf+xml")
	Response getRdf(@QueryParam("query") String query) {
		
		try
		{
		Model m = Manager.getInstance().getTripleStore().getSparqlConstructModel(query);
		return serializeModel(m,Lang.RDFXML,"application/rdf+xml");
		}
		catch(Exception e)
		{
		return Response.serverError().entity(e.toString()).build(); // to improve
		}
		
	}
	
	private Response serializeModel(Model mdl, Lang format, String mimetype)
	{
		StreamingOutput stream = new StreamingOutput() {
			@Override
			public void write(OutputStream os) throws IOException
			{
				RDFDataMgr.write(os,mdl,format);
			}
		};
		
		return Response.ok(stream).type(mimetype).build();
		
			
	}

}
