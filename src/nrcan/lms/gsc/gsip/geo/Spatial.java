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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import nrcan.lms.gsc.gsip.Constants;
import nrcan.lms.gsc.gsip.Manager;
import nrcan.lms.gsc.gsip.util.db.Binder;
import nrcan.lms.gsc.gsip.util.db.Database;
import nrcan.lms.gsc.gsip.util.db.ResultSetReader;

import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;

import freemarker.template.utility.StringUtil;


//TODO: user a proper geoJSON serialization, I've done manual encoding

@Path("geo")
public class Spatial {
	public static final String VALID_POSTGRES_TABLE = "^[A-Za-z_][A-Za-z_0-9$]*$"; // letter, number, underscore , must start with a letter
	private final Pattern valid_table = Pattern.compile(VALID_POSTGRES_TABLE);
	@Context ServletContext context;
	//TODO: lots of assumption about the naming, but I suppose one could configure views in the database to address discrepencies
	public static final String SQL = "SELECT json_build_object(\r\n" + 
			"    'type',       'Feature',\r\n" + 
			"    'id',         id,\r\n" + 
			"    'geometry',   ST_AsGeoJSON(geom)::json,\r\n" + 
			"    'properties', json_build_object(\r\n" + 
			"        'id', id,\r\n" + 
			"        'name', unit_code,\r\n" + 
			"        'uri', uri\r\n" + 
			"     )\r\n" + 
			" )\r\n" + 
			" FROM  %s LIMIT 500";
	public static final String SQL_FILTER = "SELECT json_build_object(\r\n" + 
			"    'type',       'Feature',\r\n" + 
			"    'id',         id,\r\n" + 
			"    'geometry',   ST_AsGeoJSON(geom)::json,\r\n" + 
			"    'properties', json_build_object(\r\n" + 
			"        'id', id,\r\n" + 
			"        'name', unit_code,\r\n" + 
			"        'uri', uri\r\n" + 
			"     )\r\n" + 
			" )\r\n" + 
			" FROM  %s WHERE id = ?";
	/**
	 * 
	 * @param format
	 * @param db connection pooling name
	 * @param table table in the database to read from.  might need to add schema.table if schema is different from user/public
	 * @return
	 */
	@Path("remote/{db}/{table}")
	@GET
	public Response getSpatialData(@QueryParam("f") String format,@PathParam("db") String db,@PathParam("table") String table)
	{
		
		// db can only be a valid connection id (configured in context.xml)
		// the table must be a valid table name, nothing else
		
		try{
		if (isValid(table))
		{
		String sql = String.format(SQL, table);
		
		ResponseStreamer rs = new ResponseStreamer(new DatasetHandler(db,sql));
		return Response.ok(rs).type("application/vnd.geo+json").build();
		}
		else
			return Response.status(HttpStatus.SC_NOT_ACCEPTABLE).entity("Not acceptable").build();
		}
		catch(Exception ex)
		{
			Logger.getAnonymousLogger().log(Level.SEVERE,"Failed to get data",ex);
		return Response.serverError().build();
		}
	}
	
	/**
	 * checks if the table name is valid, and somewhat safe, I exclude stuff that begins with pg_
	 * @return
	 */
	private boolean isValid(String t)
	{
		if (t == null) return false;
		 Matcher matcher = valid_table.matcher(t);
		 if (matcher.matches())
		 {
			 return !t.toLowerCase().startsWith("pg_");
		 }
		 else
			 return false;
	}
	
	@Path("remote/{db}/{table}/{id}")
	@GET
	public Response getSpatialLiteRecord(@QueryParam("f") String format,@PathParam("db") String db,@PathParam("table") String table,@PathParam("id") String id)
	{
		try{
		if (isValid(table))
			{
			String sql = String.format(SQL_FILTER, table);
			ResponseStreamer rs = new ResponseStreamer(new DatasetHandlerBinder(db,sql,Integer.parseInt(id)));
			return Response.ok(rs).type("application/vnd.geo+json").build();
			}
		else
			return Response.status(HttpStatus.SC_NOT_ACCEPTABLE).entity("Not acceptable").build();
		}
		catch(Exception ex)
		{
			Logger.getAnonymousLogger().log(Level.SEVERE,"Failed to get data",ex);
		return Response.serverError().build();
		}

	}
	
	/**
	 * 
	 * TODO
	 * remote/{connection}/{table}
	 */
	
	public class PostgresqlReader implements ResultSetReader
	{
		public PostgresqlReader(Writer w)
		{
			this.w = w;
		}
		private Writer w;
		private boolean hasRecord = false;
		private boolean needReplacePersistantUri = Manager.getInstance().getConfiguration().getNeedReplacePersistant();
		private String persistentUri = Manager.getInstance().getConfiguration().getParameterAsString(Constants.PERSISTENT_URI, null);
		private String baseUri = Manager.getInstance().getConfiguration().getParameterAsString(Constants.BASE_URI,context.getContextPath());

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
			
			String record = r.getString(1);
			//TODO: issue, postgresql does not replace // with \/\/.. not sure it's necessary actually.
			if (this.needReplacePersistantUri)

				w.write(StringUtil.replace(record, this.persistentUri, this.baseUri));

			else
				w.write(record);
			
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
				w.write("]}");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.SEVERE,"Failed to serialize geojson record",e);
				
			}
			
		}
		
	}

	public class DatasetHandler implements Handler
	{

		private String sql;
		private String db;
		public DatasetHandler(String db,String sql)
		{
			this.db = db;
			this.sql = sql;
		}
		@Override
		public void serialize(Writer w) {
			Database.getInstance().executeRequestQuery(db,sql, new PostgresqlReader(w) );
		}
		
		
	}
	
	/**
	 * handler that must bind parameters before execution
	 * @author Eric Boisvert
	 * Laboratoire de Cartographie Numérique et de Photogrammétrie
	 * Commission géologique du Canada (c) 2018
	 * Ressources naturelles Canada
	 */
	public class DatasetHandlerBinder implements Handler,Binder
	{

		private String sql;
		private String db;
		private int id;
		public DatasetHandlerBinder(String db,String sql,int id)
		{
			this.sql = sql;
			this.db = db;
			this.id = id;
		}
		@Override
		public void bind(PreparedStatement pstmt) {
			try {
				pstmt.setInt(1, id);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Logger.getAnonymousLogger().log(Level.SEVERE, "Bounding fails",e);
			}
			
		}

		@Override
		public void serialize(Writer w) {
			
			Database.getInstance().executePreparedRequestQuery(db,sql, new PostgresqlReader(w),this );
			
		}
		
	}

}
