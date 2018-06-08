package nrcan.lms.gsc.gsip.triple;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.jena.dboe.jenax.Txn;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.query.ReadWrite;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdfconnection.RDFConnection;
import org.apache.jena.rdfconnection.RDFConnectionFactory;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.RDFDataMgr;

public class EmbeddedStore extends TripleStoreImpl {

	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
		ds.close();
	}

	@Override
	public Model getSparqlConstructModel(String sparql) {

			Model m = null;
			try(RDFConnection conn = RDFConnectionFactory.connect(ds)){
	
			m = conn.queryConstruct(sparql);
			return m;
			}
			catch(Exception ex)
			{
				Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to execute [" + sparql + "]");
				return null;
			}
			
	
		
	}

	private Dataset ds;
	// at this point, expects a real path
	public EmbeddedStore(String path)
	{
		File f = new File(path);
		initServer(getAllFiles(f));
	}
	
	private void initServer(List<File> datasets)
	{
		// if the file if folder, get all the files in the folder
		
		ds = DatasetFactory.createTxnMem();
		// add the model
		Model m = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF);
		

		for(File f:datasets )
		{
			// load the data from the repo
			try {
			RDFDataMgr.read(m, f.getAbsolutePath());
			Logger.getAnonymousLogger().log(Level.INFO, " # loaded " + f.getAbsolutePath());
			}
			catch(Exception ex)
			{
				Logger.getAnonymousLogger().log(Level.WARNING," !* Failed to load " + f.getAbsolutePath(), ex);
			}
		}
		Reasoner owl = ReasonerRegistry.getOWLReasoner();
		ds.setDefaultModel(ModelFactory.createInfModel(owl, m));
		

		
	}
	
	private List<File> getAllFiles(File f)
	{
		List<File> all = new ArrayList<File>();
		if (f.isDirectory())
		{
			all.addAll(Arrays.asList(f.listFiles()));
		}
		else
		{
			all.add(f);
		}
		return all;
	}

}
