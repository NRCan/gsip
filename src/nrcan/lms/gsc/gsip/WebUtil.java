package nrcan.lms.gsc.gsip;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class WebUtil {
	/**
	 * just grab a string (or whatever) from the web. 
	 * used for reverse proxying source that does not support CORS or have non-http protocols (ie, ftp)
	 * @param location
	 * @return
	 * @throws IOException
	 */
	public static String getStringFromWeb(String location) throws IOException
	{
		// virtually a copy-paste from https://docs.oracle.com/javase/tutorial/networking/urls/readingWriting.html
		 URL oracle = new URL(location); // URL to Parse
         URLConnection yc = oracle.openConnection();
         return IOUtils.toString(yc.getInputStream(),"UTF-8");
	}

}
