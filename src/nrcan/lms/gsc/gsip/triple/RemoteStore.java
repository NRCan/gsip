package nrcan.lms.gsc.gsip.triple;
/**
 
   _    ___ _  _ ___ 
 | |  / __| \| | _ \
 | |_| (__| .` |  _/
 |____\___|_|\_|_|  
                    
                    
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.IOUtils;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;


public class RemoteStore extends TripleStoreImpl {
	public static final String defaultSparqlEndpoint = "http://localhost:8080/fuseki/gsip";
	private String sparqlRepo;
	
	public RemoteStore(String store)
	{
		this.sparqlRepo = store;
	}
	
	// perform a sparql query on a data store
	public Model getSparqlConstructModel(String sparql)
	{
		 Query query = QueryFactory.create(sparql);
			  try ( RDFConnection conn = RDFConnectionFactory.connect(sparqlRepo) ) {
		          return  conn.queryConstruct(query);
		        }
		 catch(Exception ex)
		 {
			 Logger.getAnonymousLogger().log(Level.SEVERE, "failed to execute [\n" + sparql + "]\n from " + sparqlRepo ,ex);
			 
		 }
		 return null;
			 
	}
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws IOException
	
	{
		
		
		String sparql = "CONSTRUCT {<https://geosciences.ca/id/catchment/c>  ?p ?o. ?o ?p2 ?o2}\r\n" + 
				"WHERE {<https://geosciences.ca/id/catchment/c>  ?p ?o. OPTIONAL {?o ?p2 ?o2}}";
		RemoteStore tsj = new RemoteStore(RemoteStore.defaultSparqlEndpoint);
		Model m = tsj.getSparqlConstructModel(sparql);
		// merge some triples in there
		m.read(IOUtils.toInputStream("@prefix rdfs:  <http://www.w3.org/2000/01/rdf-schema#>  . <https://geosciences.ca/id/catchment/c> rdfs:label \"Hello\"@en.","UTF-8"),null,"TURTLE");
		m.write(System.out,"JSON-LD");
	}
	
	
	
	
	
	
	
	
	
	

}
