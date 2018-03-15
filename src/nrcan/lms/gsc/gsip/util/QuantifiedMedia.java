package nrcan.lms.gsc.gsip.util;

import javax.ws.rs.core.MediaType;

public class QuantifiedMedia {
	public double q = 1.0;
	public MediaType mt;
	public QuantifiedMedia(String m)
	{
		String[] parts = m.split("q="); // 
		if (parts.length == 2)
			q = Double.parseDouble(parts[1]);
		else
			q = 1.0;
		mt = MediaType.valueOf(m);
	}

}
