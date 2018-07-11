package nrcan.lms.gsc.gsip.util;

import java.io.File;
import java.io.IOException;

public class FileUtil {
	/**
	 * Checks, whether the child directory is a subdirectory of the base 
	 * directory.  Used to make sure resources are read from a specific folder (ie, WebContent)
	 * Code taken from https://stackoverflow.com/a/25036212/8691687
	 *
	 * @param base the base directory.
	 * @param child the suspected child directory.
	 * @return true, if the child is a subdirectory of the base directory.
	 * @throws IOException if an IOError occured during the test.
	 */
	public static boolean isSubDirectory(File base, File child)
	    throws IOException {
	    base = base.getCanonicalFile();
	    child = child.getCanonicalFile();

	    File parentFile = child;
	    while (parentFile != null) {
	        if (base.equals(parentFile)) {
	            return true;
	        }
	        parentFile = parentFile.getParentFile();
	    }
	    return false;
	}

}
