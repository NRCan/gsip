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
	public void executeRequestQuery(String sql, ResultSetReader r) {
		
		Statement stat = null;
		ResultSet reader = null;
		Connection conn = null;
		try {
		conn = getConnection(prop,connectionString);
		Statement stmt = conn.createStatement();
		stmt.execute("SELECT load_extension('mod_spatialite')");
		
		
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
		finally
		{
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Logger.getAnonymousLogger().log(Level.SEVERE, "could not close connection " + connectionString,e);
				}
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
	 * @throws ClassNotFoundException 
	 */
	public SpatialLiteDatabase(String connectionString) throws SQLException, ClassNotFoundException
	{
		Class.forName("org.sqlite.JDBC");
		prop = new Properties();
        prop.setProperty("enable_shared_cache", "true");
        prop.setProperty("enable_load_extension", "true");
        prop.setProperty("enable_spatialite", "true");
        this.connectionString = connectionString;
		
	}
	
	

}
