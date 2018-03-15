package nrcan.lms.gsc.gsip.triple;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;


public class TripleStoreJena {
	public static final String defaultSparqlEndpoint = "http://s-stf-gin.nrn.nrcan.gc.ca:8085/fuseki/gsip";
	private String sparqlRepo;
	public TripleStoreJena(String store)
	{
		this.sparqlRepo = store;
	}
	
	
	public Model getSparqlConstructModel(String sparql)
	{
		 Query query = QueryFactory.create(sparql);
			  try ( RDFConnection conn = RDFConnectionFactory.connect(sparqlRepo) ) {
		          return  conn.queryConstruct(query);
		        }
		 catch(Exception ex)
		 {
			 Logger.getAnonymousLogger().log(Level.SEVERE, "failed to execute [\n" + sparql + "]\n" ,ex);
			 
		 }
		 return null;
			 
	}
	
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
	
	
	
	public static void main(String[] args) throws IOException
	
	{
		
		
		String sparql = "CONSTRUCT {<https://geosciences.ca/id/catchment/c>  ?p ?o. ?o ?p2 ?o2}\r\n" + 
				"WHERE {<https://geosciences.ca/id/catchment/c>  ?p ?o. OPTIONAL {?o ?p2 ?o2}}";
		TripleStoreJena tsj = new TripleStoreJena(TripleStoreJena.defaultSparqlEndpoint);
		Model m = tsj.getSparqlConstructModel(sparql);
		// merge some triples in there
		m.read(IOUtils.toInputStream("@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#>  . <https://geosciences.ca/id/catchment/c> rdfs:label \"Hello\"@en.","UTF-8"),null,"TURTLE");
		m.write(System.out,"JSON-LD");
	}
	
	/**
	 * describe a resource (DESCRIBE)
	 * @param resource
	 * @return
	 */
	public Model describe(String resource)
	{
		return getSparqlConstructModel("DESCRIBE " + formatResource(resource));
	}
	
	public boolean resourceExists(String resource)
	{
		Model mdl = describe(resource);
		return !mdl.isEmpty();
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
