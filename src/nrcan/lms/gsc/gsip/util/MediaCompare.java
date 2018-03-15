package nrcan.lms.gsc.gsip.util;

import java.util.Comparator;

public class MediaCompare implements Comparator<QuantifiedMedia> {

	@Override
	public int compare(QuantifiedMedia o1, QuantifiedMedia o2) {
		// TODO Auto-generated method stub
		double delta = o1.q - o2.q;
		if (delta < 0) return 1;
		if (delta > 0) return -1;
		return 0;
	}

}
