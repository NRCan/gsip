package nrcan.lms.gsc.gsip.data;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import nrcan.lms.gsc.gsip.data.MatchType.MimeType;

public class DataManager {
	private List<MatchType> matches = null;
	public static final String DATA_CONF_FOLDER = "data"; // read all the files in this folder
	private DataManager()
	{
		
	}
	
	public static class DataManagerHolder
	{
		static DataManager instance = new DataManager();
	}
	
	// make sure this is not called simultaneously by two threads
	public static synchronized DataManager getInstance(ServletContext ctx)
	{
		if (DataManagerHolder.instance.matches == null)
			DataManagerHolder.instance.init(ctx);
		return DataManagerHolder.instance;
	}
	public static DataManager getInstance()
	{
		if (DataManagerHolder.instance.matches != null)
			return DataManagerHolder.instance;
		else
			return null; // should not happen
	}
	
	private DataType getData(File f) throws JAXBException
	{
		JAXBContext jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		Object o = unmarshaller.unmarshal(f);
	
	    JAXBElement<DataType> unmarshalledObject = 
	            (JAXBElement<DataType>) o;
	    return unmarshalledObject.getValue();
	}
	
	private void init(ServletContext ctx)
	{
		this.matches = new ArrayList<MatchType>();
		// get all the files in DATA 
		File dataFolder = new File(ctx.getRealPath("/"+DATA_CONF_FOLDER));
		// get all the files in the folder
		List<File> files = new ArrayList<File>();
		
		FilenameFilter filter = new FilenameFilter()
				{

					@Override
					public boolean accept(File dir, String name) {
						// TODO Auto-generated method stub
						return name.endsWith(".xml");
					}
			
				};
		
		
		for(File fl:dataFolder.listFiles(filter))
		{
			try {
				Logger.getAnonymousLogger().log(Level.INFO, "getting matching from " + fl.getAbsolutePath());
				DataType d = getData(fl);
				// load all the matches to matches
				matches.addAll(d.match);
			} catch (JAXBException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.WARNING, "failed to load " + fl.getName(),e);
			}
			
		}
		
		
		
	}
	public MatchType getMatch(String pattern,MediaType mimeType)
	{
		
		//TODO: improve this by moving this into hashes
		//TODO: also, this only uses the type and not the subtype, We need to improve the logic of mime-type mapping
		for(MatchType m:matches)
		{
			if (Pattern.matches(m.pattern, pattern))
			{

				for(MimeType mt:m.getMimeType())
				{
					//System.out.println(m.pattern +"," + mt.value);
					//Logger.getAnonymousLogger().log(Level.INFO, "About to get value :" +mt.value);
					MediaType toMatch = MediaType.valueOf(mt.value);
				// check if the mimetype matches
				if (toMatch.isCompatible(mimeType))
					return m;
				}
			}
		}
		return null;
	}
	

}
