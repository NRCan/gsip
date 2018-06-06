package nrcan.lms.gsc.gsip.util.db;

import java.sql.SQLException;

public interface Database {
	public void executeRequestQuery(String sql,ResultSetReader r) throws SQLException ;

}
