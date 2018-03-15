package nrcan.lms.gsc.gsip.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


import javax.ws.rs.core.MediaType;

public class MediaTypeUtil  {
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

}
