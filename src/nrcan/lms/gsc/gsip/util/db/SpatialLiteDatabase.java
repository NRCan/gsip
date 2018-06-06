package nrcan.lms.gsc.gsip.util.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Connection to spatialite
 * code copied in part from https://github.com/benstadin/spatialite4-jdbc
 * @author Eric Boisvert
 * Laboratoire de Cartographie Numérique et de Photogrammétrie
 * Commission géologique du Canada (c) 2018
 * Ressources naturelles Canada
 */
public class SpatialLiteDatabase extends DatabaseImpl {
	
	@Override
	public void executeRequestQuery(String sql, ResultSetReader r) throws SQLException {
		
		Statement stat = null;
		ResultSet reader = null;
		try {
		Connection conn = getConnection(prop,connectionString);
		
		if (r.start())
		{
		 stat = conn.createStatement();
		 reader = stat.executeQuery(sql);
		 while(reader.next())
		 {
			boolean status = r.read(reader);
			if (!status) break;
		 }
			 
		
		}
		r.end();
		}
		catch(SQLException e)
		{
			Logger.getAnonymousLogger().log(Level.SEVERE, "error in ", e);
		}
		
		
		
	}

	
	private Properties prop;
	private String connectionString;
	
	 public static Connection getConnection(Properties prop,String connectionString) throws SQLException {
	        return DriverManager.getConnection(connectionString, prop);
	    }
	
	/**
	 * 
	 * @param connectionString
	 * @throws SQLException 
	 */
	public SpatialLiteDatabase(String connectionString) throws SQLException
	{
		prop = new Properties();
        prop.setProperty("enable_shared_cache", "true");
        prop.setProperty("enable_load_extension", "true");
        prop.setProperty("enable_spatialite", "true");
        this.connectionString = connectionString;
		
	}
	
	

}
