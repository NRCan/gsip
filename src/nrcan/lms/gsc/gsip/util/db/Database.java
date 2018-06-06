package nrcan.lms.gsc.gsip.util.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import nrcan.lms.gsc.gsip.geo.Spatial.DatasetHandlerBinder;
import nrcan.lms.gsc.gsip.geo.Spatial.PostgresqlReader;



/**
 * naive connection pooling manager
 * @author Eric Boisvert
 * Laboratoire de Cartographie Numérique et de Photogrammétrie
 * Commission géologique du Canada (c) 2018
 * Ressources naturelles Canada
 */
public  class Database  {
	private Context envCtx;
	private Database()
	{
		 try {
				envCtx = (Context) new InitialContext();
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to initialise context",e);
			}
			
	}
	
	/**
	 * single instance
	 * @author Laptop
	 *
	 */
	public static class DatabaseSingleHolder 
	{
		
		static Database instance = new Database();
	}
	
	public static Database getInstance()
	{
		
		return DatabaseSingleHolder.instance;
	}
	
	
	/**
	 * get a connection from the pool
	 * @param key
	 * @return
	 * @throws SQLException
	 */
	public  Connection getConnection(String key) throws SQLException
	{
		try {
		
			
			DataSource datasource = (DataSource) envCtx.lookup("java:/comp/env/jdbc/" + key);
			if (datasource == null)
			{
				Logger.getAnonymousLogger().log(Level.SEVERE,"failed to get a datasource [" + key + "]");
				return null;
			}
			else
				return datasource.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			Logger.getAnonymousLogger().log(Level.SEVERE,"Failed to connect to database jdbc/trit",e);
			return null;
		}
				
	}
	

	
	public void executeRequestQuery(String db,String sql, ResultSetReader r)  {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		 try {
			conn = getConnection(db);
			stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (r.start())
			{
				rs = stmt.executeQuery(sql);
				while(rs.next())
					if (!r.read(rs)) break; // if the handler tells to stop, break the loop
			}
			r.end();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally
		 {
			 if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to close resultset for " + sql, e);
				}
			 if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to close statemnt for " + sql, e);
				}
			 if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to close connection for " + sql, e);
					
				}
			 
			 
			 
		 }
		
		

	}


	/**
	 * execute a query on a statement that must be bounded 
	 * @param db
	 * @param sql
	 * @param rs
	 * @param b
	 */
	public void executePreparedRequestQuery(String db, String sql, ResultSetReader r,
			Binder b) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		 try {
			conn = getConnection(db);
			stmt = conn.prepareStatement(sql, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			b.bind(stmt);
			if (r.start())
			{
				rs = stmt.executeQuery();
				while(rs.next())
					if (!r.read(rs)) break; // if the handler tells to stop, break the loop
			}
			r.end();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 finally
		 {
			 if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to close resultset for " + sql, e);
				}
			 if (stmt != null)
				try {
					stmt.close();
				} catch (SQLException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to close statemnt for " + sql, e);
				}
			 if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to close connection for " + sql, e);
					
				}
			 
			 
			 
		 }
		
		
	
		
	}

}
