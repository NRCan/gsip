package nrcan.lms.gsc.gsip.geo;
/**
 * Access a repository of spatial datasets
 * This version only access spatial lite repositories, could be extended to read postgres
 * @author Eric Boisvert
 * Laboratoire de Cartographie Numérique et de Photogrammétrie
 * Commission géologique du Canada (c) 2018
 * Ressources naturelles Canada
 */

import java.io.IOException;
import java.io.Writer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import nrcan.lms.gsc.gsip.util.db.ResultSetReader;
import nrcan.lms.gsc.gsip.util.db.SpatialLiteDatabase;

//TODO: user a proper geoJSON serialization, I've done manual encoding

@Path("geo")
public class Spatial {
	@Context ServletContext context;
	public static final String SQL = "SELECT id,name,asGeoJSON(geometry) g from %s";
	public static final String SQL_ID = "SELECT id,name,asGeoJSON(geometry) g from %s WHERE id = ?";
	@Path("local/{db}/{table}")
	@GET
	public Response getSpatialiteTable(@QueryParam("f") String format,@PathParam("db") String db,@PathParam("table") String table)
	{
		//String connectionString = "jdbc:sqlite:c:/temp/rich_bedrock.sqlite";
		String connectionString = "jdbc:sqlite:" + context.getRealPath("/geo/").replace("\\", "/") + db + ".sqlite";
		Logger.getAnonymousLogger().log(Level.INFO, "connecting to " + connectionString);
		try{
		String sql = String.format(SQL, table);
		
		ResponseStreamer rs = new ResponseStreamer(new DatasetHandler(connectionString,sql));
		return Response.ok(rs).type("application/vnd.geo+json").build();
		}
		catch(Exception ex)
		{
			Logger.getAnonymousLogger().log(Level.SEVERE,"Failed to get data",ex);
		return Response.serverError().build();
		}
	}
	@Path("local/{db}/{table}/{id}")
	public Response getSpatialLiteRecord(@QueryParam("f") String format,@PathParam("db") String db,@PathParam("table") String table,@PathParam("id") String id)
	{
		
		return Response.serverError().build();
	}
	
	/**
	 * 
	 * TODO
	 * remote/{connection}/{table}
	 */
	
	public class SpatialLiteReader implements ResultSetReader
	{
		public SpatialLiteReader(Writer w)
		{
			this.w = w;
		}
		private Writer w;
		private boolean hasRecord = false;
		@Override
		public boolean start() {
			// TODO Auto-generated method stub
			try {
				w.write("{\"type\":\"FeatureCollection\",\"name\":\"x\", \"features\":[");
				return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.SEVERE, "failed to start json");
				return false;
			}
		}

		@Override
		public boolean read(ResultSet r) {
			// to control the "," in the list.  all record, except the first one, must have a preceeding ","
			try {
			if (hasRecord)
			{
				
					w.write(",");
				
			}
			
			w.write("{\"type\":\"Features\", \"properties\":");
			w.write("\"id\":\"" + r.getString(1) + "\",");
			w.write("\"uri\":\"" + r.getString(2)+"\"},\"geometry\":");
			w.write(r.getString(3));
			w.write("}");
			
			
			
			
			hasRecord = true;
			return true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.SEVERE,"Failed to serialize geojson record",e);
				return false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.SEVERE,"Failed to serialize geojson record",e);
				return false;
			}
			
			
			
			
		}

		@Override
		public void end() {
			try {
				w.write("}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.SEVERE,"Failed to serialize geojson record",e);
				
			}
			
		}
		
	}

	public class DatasetHandler implements Handler
	{

		private String sql;
		private String connectionString;
		public DatasetHandler(String cs,String sql)
		{
			connectionString = cs;
			this.sql = sql;
		}
		@Override
		public void serialize(Writer w) {
			SpatialLiteDatabase sl = null;
			try {
				sl = new SpatialLiteDatabase(connectionString);
				sl.executeRequestQuery(sql, new SpatialLiteReader(w) );
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to read SpatialLite database",e);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.SEVERE, "Failed to find sql lite driver",e);
			}
			
		}
		
	}

}
