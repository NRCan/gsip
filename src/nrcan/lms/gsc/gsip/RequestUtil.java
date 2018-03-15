package nrcan.lms.gsc.gsip;

import java.util.Locale;

import nrcan.lms.gsc.gsip.conf.Configuration;



public class RequestUtil {

	/** figure out what language to use from a request.  It check first if there is a parameter override
	 * if not, will check the header, and then 
	 * @param lang
	 * @param locale
	 * @param parameter
	 * @return
	 */
	public static String getLocale(String lang, Locale locale) {
		if (lang != null && lang.trim().length() > 0)
			return lang; // priority
		// otherwise, check the locale
		String l = locale.getLanguage();
		if (Configuration.getInstance().isValidLanguage(l))
			return l;
		else
			return Configuration.getInstance().getDefaultLanguage();
	}

}
