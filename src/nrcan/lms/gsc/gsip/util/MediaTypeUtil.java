package nrcan.lms.gsc.gsip.util;

import static nrcan.lms.gsc.gsip.Constants.APPLICATION_RDFXML;
import static nrcan.lms.gsc.gsip.Constants.APPLICATION_TURTLE;
import static nrcan.lms.gsc.gsip.Constants.TEXT_TURTLE;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import javax.ws.rs.core.MediaType;

import nrcan.lms.gsc.gsip.util.MediaTypeUtil.InfoOutputFormat;

public class MediaTypeUtil  {
	public enum InfoOutputFormat {ioHTML,ioRDFXML,ioTURTLE,ioJSONLD,ioXML,ioUnknown}
	public static List<QuantifiedMedia> getMediaTypesOrdered(String mediaTypes)
	{
		String[] medias = mediaTypes.split(",");
		List mediaList = new ArrayList<QuantifiedMedia>();
		for(String m:medias)
		{
			QuantifiedMedia q = new QuantifiedMedia(m);
			mediaList.add(q);
		}
		
		mediaList.sort(new MediaCompare());
		return mediaList;
	}
	
	public static InfoOutputFormat getOutputFormat(String format, String accepted)
	{
		InfoOutputFormat of = InfoOutputFormat.ioUnknown; // default
		// check if we have an override
		// TODO:  put this in a util class
		if (format != null && format.trim().length() > 0)
		{
			// TODO: use the Configuration instead
			if ("rdf".equalsIgnoreCase(format) || "application/rdf+xml".equalsIgnoreCase(format)) of = InfoOutputFormat.ioRDFXML;
			if ("ttl".equalsIgnoreCase(format) || "text/turtle".equalsIgnoreCase(format)) of = InfoOutputFormat.ioTURTLE;
			if ("xml".equalsIgnoreCase(format) || "text/xml".equalsIgnoreCase(format)) of = InfoOutputFormat.ioXML;
			if ("html".equalsIgnoreCase(format) || "htm".equalsIgnoreCase(format) || "text/html".equalsIgnoreCase(format)) of = InfoOutputFormat.ioHTML;
			if ("json".equalsIgnoreCase(format) || "jsonld".equalsIgnoreCase(format) || "application/ld+json".equalsIgnoreCase(format))  of = InfoOutputFormat.ioJSONLD;
			// otherwise, file not found

			
		}
		else
		{
			// figure it out from media type
			of = InfoOutputFormat.ioHTML; // default (unlike unknown extension, we have a default)
			if (accepted != null)
				of = getPreferedMedia(accepted);	
		}
		return of;
	}
		
	public static InfoOutputFormat getPreferedMedia(String accepts)
		{
		
			List<QuantifiedMedia> m = MediaTypeUtil.getMediaTypesOrdered(accepts);
			for(QuantifiedMedia qt:m)
			{
			

			
			if (qt.mt.isCompatible(MediaType.APPLICATION_JSON_TYPE)) return InfoOutputFormat.ioJSONLD;
			if (TEXT_TURTLE.equals(qt.mt.toString()) || APPLICATION_TURTLE.equals(qt.mt.toString())) return InfoOutputFormat.ioTURTLE;
			if (APPLICATION_RDFXML.equals(qt.mt.toString())) return InfoOutputFormat.ioRDFXML;
			if (qt.mt.isCompatible(MediaType.TEXT_HTML_TYPE)) return InfoOutputFormat.ioHTML;
			if (qt.mt.isCompatible(MediaType.TEXT_XML_TYPE)) return InfoOutputFormat.ioRDFXML;
			}
			// nothing matched
			return InfoOutputFormat.ioHTML;

		}

}
