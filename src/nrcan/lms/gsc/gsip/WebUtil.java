package nrcan.lms.gsc.gsip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.io.IOUtils;

public class WebUtil {
	public static String getStringFromWeb(String location) throws IOException
	{
		 URL oracle = new URL(location); // URL to Parse
         URLConnection yc = oracle.openConnection();
         return IOUtils.toString(yc.getInputStream(),"UTF-8");
	}

}
