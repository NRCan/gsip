package nrcan.lms.gsc.gsip.triple;

import org.apache.jena.rdf.model.Model;

import nrcan.lms.gsc.gsip.model.ModelUtil;

public class TripleStoreImpl implements TripleStore {

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}
	
	/**
	 * describe a resource (DESCRIBE)
	 * @param resource
	 * @return
	 */
	public Model describe(String resource)
	{
		return getSparqlConstructModel("DESCRIBE " + ModelUtil.formatResource(resource));
	}
	
	public boolean resourceExists(String resource)
	{
		Model mdl = describe(resource);
		return !mdl.isEmpty();
	}

	@Override
	public Model getSparqlConstructModel(String sparql) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
