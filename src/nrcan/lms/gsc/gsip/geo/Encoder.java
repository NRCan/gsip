package nrcan.lms.gsc.gsip.geo;

import java.io.OutputStreamWriter;
import java.sql.ResultSet;

public interface Encoder {
	public String getSQLTemplate();
	public void openDataset(OutputStreamWriter osw);
	public void writeRecord(ResultSet r,OutputStreamWriter osw);
	public void closeDataset(OutputStreamWriter ows);

}
