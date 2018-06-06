package nrcan.lms.gsc.gsip.util.db;

import java.sql.ResultSet;

public interface ResultSetReader {
	public boolean start();
	public boolean read(ResultSet r);
	public void end();

}
