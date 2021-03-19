package nrcan.lms.gsc.gsip.vocabulary;

import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;


public class SCHEMA {
	// code base on package org.apache.jena.vocabulary;
	public static final String uri="https://schema.org/";
	 protected static final Resource resource( String local )
     { return ResourceFactory.createResource( uri + local ); }

 protected static final Property property( String local )
     { return ResourceFactory.createProperty( uri, local ); }
 
 public static final Property subjectOf = Init.subjectOf();
 public static final Property about = Init.about();
 public static final Property provider = Init.provider();
 public static final Property url = Init.url();

 
 public static class Init {
     
     public static Property subjectOf()        	{ return property( "subjectOf"); }
     public static Property about()         	{ return property( "about"); }
     public static Property provider()			{ return property("provider");}
     public static Property url() 				{ return property("url");}

     
 }

 public static String getURI() {return uri;}

}
