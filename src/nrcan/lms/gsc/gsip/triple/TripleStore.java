package nrcan.lms.gsc.gsip.triple;

import org.apache.jena.rdf.model.Model;


public interface TripleStore {
	
	public Model getSparqlConstructModel(String sparql);
	public Model describe(String resource);
	public boolean resourceExists(String resource);
	
	public void close();

}
