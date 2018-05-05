package nrcan.lms.gsc.gsip.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.http.client.utils.URIBuilder;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.OWL;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;

import nrcan.lms.gsc.gsip.Manager;
import nrcan.lms.gsc.gsip.conf.Configuration;



/**
 * Wraps a Jena model and provide utilities for FreeMarker to access underlying model
 * @author Eric Boisvert
 * Laboratoire de Cartographie Numérique et de Photogrammétrie
 * Commission géologique du Canada (c) 2018
 * Ressources naturelles Canada
 */

public class ModelWrapper {
	private Model model;
	private Resource contextResource;
	
	public ModelWrapper(Model m,String contextResource)
	{
		this.model = m;
		this.contextResource = m.getResource(contextResource);
		Logger.getAnonymousLogger().log(Level.INFO, "Contexte : " + contextResource);
		model.write(System.out,"TURTLE");
	}
	
	public String getContextResourceUri()
	{
		return contextResource.getURI();
	}
	
	// get the prefix for this namespace
	public String getPrefix(String ns)
	{
		return model.getNsURIPrefix(ns);
	}
	
	/** 
	 * The a label for the context resource
	 * @param language
	 * @param defaultLabel
	 * @return
	 */
	public String getPreferredLabel(String language,String defaultLabel)
	{
		return getPreferredLabel(contextResource,language,defaultLabel);
	}
	
	public String getPreferredLabel(String resource,String language,String defaultLabel)
	{
		Resource res = model.getResource(getFullUri(resource));
		return getPreferredLabel(res,language,defaultLabel);
	}

	/**
	 * Find a label for this language.  If language is null or no label matches this language
	 * return the first one
	 * @param r
	 * @param language
	 * @param defaultLabel
	 * @return
	 */
	private String getPreferredLabel(Resource res,String language,String defaultLabel)
	{
		
		StmtIterator s = res.listProperties(RDFS.label);
		String atLeastThisOne = null;
		while(s.hasNext())
		{

			Statement st = s.next();
			if (atLeastThisOne == null)
				atLeastThisOne = st.getLiteral().getValue().toString();
			// is the language is null, we already found what we were looking for
			if (language == null) break;
			// got a language that matches 
			if (language.equals(st.getLanguage()))
				return st.getLiteral().getValue().toString();
		}
		
		if (atLeastThisOne == null)
			return defaultLabel;
		else
			return atLeastThisOne;
		
		
		
	}
	
	private List<Resource> getRepresentations(Resource res)
	{
		Logger.getAnonymousLogger().log(Level.INFO,"seeAlso "+ res.getURI());
		StmtIterator statements = res.listProperties(RDFS.seeAlso);
		List<Resource> seeAlso = new ArrayList<Resource>();
		while(statements.hasNext())
		{
			Statement s = statements.next();
			seeAlso.add(s.getResource());
		}
		return seeAlso;
	}
	
	public List<Resource> getRepresentations()
	{
		//Logger.getAnonymousLogger().log(Level.INFO, contextResource.getURI());
		return getRepresentations(this.contextResource.getURI());
	}

	public List<Resource> getRepresentations(String res)
	{
		if (res == null) return getRepresentations();
		Resource r = model.getResource(getFullUri(res));
		return getRepresentations(r);
	}
	
	public String getFormatOverride(String r,String mime)
	{
		Configuration c = Manager.getInstance().getConfiguration();
		
		
			
			String format = c.getFormatFromMimeType(mime);
			if (format != null)
				try {
					return appendFormat(r,"f",format);
				} catch (URISyntaxException e) {
					Logger.getAnonymousLogger().log(Level.WARNING, "problem with format for mime [" + mime + "] for resource " + r, e);
					return r;
				}
			else
			return r;
	}
	
	private Map<String,List<Link>> getRelevantLinkByGroup(Resource r,Aggregation a)
	{
		Map<String,List<Link>> out = new Hashtable<String,List<Link>>();
		List<Link> links = getRelevantLinks(r);
		List<Link> newCollection;
		for(Link l:links)
		{
			if (!out.containsKey(a.getKey(l)))
			{
				newCollection = new ArrayList<Link>();
				out.put(a.getKey(l),newCollection );
			}
			else
				newCollection = out.get(a.getKey(l));
			newCollection.add(l);
		}
		return out;
		
	}
	
	/**
	 * return a list of links, group by properties . keys are properties
	 * @param r
	 * @return
	 */
	public Map<String,List<Link>> getRelevantLinkByProperty(Resource r)
	{
		return getRelevantLinkByGroup(r,new Aggregation() {
			public String getKey(Link l) {return l.getLabel();}}
			);
	}
	
	/**
	 * return a list of links, group by resource.  keys are resources
	 * @param r
	 * @return
	 */
	public Map<String,List<Link>> getRelevantLinkByResource(Resource r)
	{
		return getRelevantLinkByGroup(r,new Aggregation() {
			public String getKey(Link l) {return l.getUrl();}}
			);

	}
	
	public Map<String,List<Link>> getRelevantLinkByResource()
	{
		return getRelevantLinkByResource(this.contextResource);
	}
	
	public Map<String,List<Link>> getRelevantLinkByProperty()
	{
		return getRelevantLinkByProperty(this.contextResource);
	}
	
	
	
	public List<Link> getRelevantLinks(Resource r)
	{
		List<Link> links = new ArrayList<Link>();
		StmtIterator i = r.listProperties();
		while(i.hasNext())
		{
			Statement statement = i.next();
			Property p = statement.getPredicate();
			String ns = p.getNameSpace();
		 // skip RDFS, RDF, OWL and DCT
			if (RDFS.getURI().equals(ns)) continue;
			if (RDF.getURI().equals(ns)) continue;
			if (OWL.getURI().equals(ns)) continue;
			if (DCTerms.getURI().equals(ns)) continue;
			// if we're here, we're good
			// the object must be a resource
			if (statement.getObject().isResource())
			{
				String l = getPreferredLabel(statement.getResource(),"en", this.getLastPart(statement.getResource().getURI()));
				links.add(new Link(p.getLocalName(),statement.getResource().getURI(),l));
			}

		}
		return links;
	}
	
	public List<Link> getRelevantLinks()
	{
		return getRelevantLinks(this.contextResource);
	}

	/** get the supported dct formats
	 * 
	 * @param r
	 * @return
	 */
	public List<String> getFormats(Resource r)
	{
		List<String> formats = new ArrayList<String>();
		StmtIterator i = r.listProperties(DCTerms.format);
		while(i.hasNext())
		{
			Statement s = i.next();
			formats.add(s.getString());
		}
		return formats;

	}
	public String appendFormat(String url,String key,String value) throws URISyntaxException
	{
		URI u = new URIBuilder(url).addParameter(key, value).build();
		return u.toString();
	}

	public boolean isPrefixed(String resource)
	{
		if (resource.startsWith("<") && resource.endsWith(">")) return false;
		QName name = QName.valueOf(resource);
		// check if the prefix exist
		String uri = model.getNsPrefixURI(name.getPrefix());
		return (uri != null && uri.length() > 0) ;
		
		
	}
	// assumes we are passing a prefix:value , if not, just return the value as-is
	public String getFullUri(String prefixedResource)
	{
		Logger.getAnonymousLogger().log(Level.INFO, "prefixed:" + prefixedResource);
		QName name = QName.valueOf(prefixedResource);
		//TODO: not sure this always work
		if (prefixedResource.equals(name.getLocalPart())) 
			return prefixedResource; // there's no prefix
		String uri = model.getNsPrefixURI(name.getPrefix());
		if (uri != null && uri.length() >0 )
			return uri + name.getLocalPart();
		else
			return prefixedResource;
			
		
	}
	
	
	/** return the complete context model in requested encoding
	 * 
	 * @param format
	 * @return jena supported 
	 */
	public String encode(String format)
	{
		Lang l = getLang(format);
		if (l != null)
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			RDFDataMgr.write(baos, model, l);
			return baos.toString(Charset.forName("UTF-8"));
		}
		
		else
			return format + " is Invalid \n use TTL,NT,JSON-LD,RDF/XML-ABBREV,RDF/XML,N3 or RDF/JSON";
	}
	
	public Model getModel()
	{
		return model;
	}
	
	/**
	 * get a resource from the model
	 * @param res
	 * @return
	 */
	public Resource getResource(String res)
	{
		return model.getResource(res);
	}
	
	private static Lang getLang(String s)
	{
		if ("TTL".equalsIgnoreCase(s)) return Lang.TURTLE;
		if ("NT".equalsIgnoreCase(s)) return Lang.NT;
		if ("JSON-LD".equalsIgnoreCase(s)) return Lang.JSONLD;
		if ("RDF-XML-ABBREV".equalsIgnoreCase(s)) return Lang.RDFTHRIFT;
		if ("RDF/XML".equalsIgnoreCase(s)) return Lang.RDFXML;
		if ("N3".equalsIgnoreCase(s)) return Lang.N3;
		if ("RDF/JSON".equalsIgnoreCase(s)) return Lang.RDFJSON;
		return null;
	}
	
	/** get the list of types 
	 * 
	 * @param r
	 * @return
	 */
	public String getTypeLabel(Resource r)
	{
		// TODO: create a function that only return leaf types
		StringBuilder sb = new StringBuilder();
		StmtIterator i = r.listProperties(RDF.type);
		while(i.hasNext())
		{
			String nextElement = null;
			Statement s = i.next();
			try {
			Resource typeResource = s.getResource();
			nextElement = this.getPreferredLabel(typeResource, "en", getLastPart(typeResource.getURI()));
			}
			catch(Exception ex)
			{
				nextElement = "ERR";
			}
			
			if (sb.length() > 0) sb.append(",");
			sb.append(nextElement);
			
		}
		
		return sb.toString();
	
		
		
	}
	
	public String getLastPart(String url)
	{
		String[] parts = url.split(":|/|#");
		return parts[parts.length-1];
	}
	
	
	/**
	 * get the type list in the form type > type > type
	 * @return
	 */
	public String getTypeLabel()
	{
		return getTypeLabel(this.contextResource);
	}

}
