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
		return null;
	}
	
	public boolean resourceExists(String resource)
	{
		Model mdl = describe(resource);
		if (mdl != null)
			return !mdl.isEmpty();
		else return false;
	}

	@Override
	public Model getSparqlConstructModel(String sparql) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
