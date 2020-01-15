package nrcan.lms.gsc.gsip.triple;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
//import org.apache.jena.dboe.jenax.Txn;
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
import org.apache.jena.riot.Lang;
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
				Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to execute [" + sparql + "]",ex);
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
			// file must be ttl or rdf
			String name = f.getName();
			String ext = name.substring(name.lastIndexOf("."));
			if (".RDF".equalsIgnoreCase(ext) || ".TTL".equalsIgnoreCase(ext))
			{
				RDFDataMgr.read(m, f.getAbsolutePath());
				Logger.getAnonymousLogger().log(Level.INFO, " # loaded " + f.getAbsolutePath());
			}
			else
			{
				// is this an import file ?
				if (".IMP".equalsIgnoreCase(ext))
				{
					List<String> impList = readTextFile(f);
					for(String line:impList)
					{
						if (line == null || line.trim().length() == 0 || line.startsWith("#"))
							continue;
						try
						{
							String ont = IOUtils.toString(new URI(line).toURL(),"UTF-8");
							//System.out.print(ont);
							RDFDataMgr.read(m,IOUtils.toInputStream(ont,"UTF-8"),Lang.TTL);
							Logger.getAnonymousLogger().log(Level.INFO, "Loaded " + line + " from imp file");
							
						}
						catch(Exception ex)
						{
							Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to read from " + line,ex);
						}
					}
				}
				else
				{
					Logger.getAnonymousLogger().log(Level.WARNING,f.getName() + " ignored");
				}
				
			}
			
			
			}
			catch(Exception ex)
			{
				Logger.getAnonymousLogger().log(Level.WARNING," !* Failed to load " + f.getAbsolutePath(), ex);
			}
		}
		long t = System.currentTimeMillis();
		Logger.getAnonymousLogger().log(Level.INFO, m.size() + " statements loaded");
		Logger.getAnonymousLogger().log(Level.INFO, "Repo loaded - creating reasoner");
		Reasoner owl = ReasonerRegistry.getOWLReasoner();
		
		ds.setDefaultModel(ModelFactory.createInfModel(owl, m));
		Logger.getAnonymousLogger().log(Level.INFO, m.size() + " statements :" + (System.currentTimeMillis() - t) / 1000 + " s");

		
	}
	
	/**
	 * read a file into a list of strings
	 * @param f
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException
	 */
	private List<String> readTextFile(File f) throws IOException 
	{
		return FileUtils.readLines(f,Charset.forName("UTF-8"));
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
