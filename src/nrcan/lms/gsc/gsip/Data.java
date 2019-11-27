package nrcan.lms.gsc.gsip;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.http.HttpStatus;

import freemarker.template.TemplateException;
import nrcan.lms.gsc.gsip.conf.Configuration;
import nrcan.lms.gsc.gsip.data.DataManager;
import nrcan.lms.gsc.gsip.data.MatchType;
import nrcan.lms.gsc.gsip.template.TemplateManager;
import nrcan.lms.gsc.gsip.util.MediaTypeUtil;
import nrcan.lms.gsc.gsip.util.QuantifiedMedia;
import static nrcan.lms.gsc.gsip.Constants.TEXT_HTML;

@Path("data/{seg:.*}")
public class Data {
	@Context UriInfo uriInfo;
	@GET
	public Response getData(@QueryParam("callback") String callback,@HeaderParam("Accept") String accepts,@QueryParam("format") String format,@QueryParam("f") String f)
	{
		//Logger.getAnonymousLogger().log(Level.INFO, "Accept : [" + accepts + "]" );
	
		if (format == null)
			format = f;
		
		//TODO: this function is a mess, need to partition
		MediaType mimeType = null;
		String pattern = getPattern(uriInfo); // should return everything after /data/
		NewUrl newUrl = null;
		// at this point, I need a string to match and a format/mime-type
		if (format != null && format.trim().length() > 0)
		{
			mimeType = Configuration.getInstance().getMimeFromFormat(format);
			// get a url for this patter and url
			try {
				newUrl = getUrl(pattern,mimeType,getParameters(uriInfo));
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "IO Error ",e);
				return Response.serverError().entity("Server IO Error").build();
			} catch (TemplateException e) {
				// TODO Auto-generated catch block
				long errorTime = System.currentTimeMillis();
				Logger.getAnonymousLogger().log(Level.SEVERE, "Template Error [" + errorTime + "]",e);
				return Response.serverError().entity("Server Template Error - please report this error ["+errorTime+"]").build();
			}
		}
		else
			
		// if mimeType is null, therefore, no valid format was proposed, so we get from the header
		{
		List<QuantifiedMedia> m = MediaTypeUtil.getMediaTypesOrdered(accepts);
		if (m.isEmpty())
			return Response.status(HttpStatus.SC_UNSUPPORTED_MEDIA_TYPE).entity("Unsupported media (null)").build();
		// let's loop in all the media and find a representation that fit
		for(QuantifiedMedia media:m)
		{
			// as soon as we hit "anything", it's HTML
			if ("*".equals(media.mt.getType()))
			{
				
					try {
						newUrl = getUrl(pattern,MediaType.valueOf(TEXT_HTML),getParameters(uriInfo));
					} catch (IOException e) {
						Logger.getAnonymousLogger().log(Level.SEVERE, "IO Error ",e);
						return Response.serverError().entity("Server IO Error").build();
					} catch (TemplateException e) {
						long errorTime = System.currentTimeMillis();
						Logger.getAnonymousLogger().log(Level.SEVERE, "Template Error [" + errorTime + "]",e);
						return Response.serverError().entity("Server Template Error - please report this error ["+errorTime+"]").build();

					}
				
				break;
			}
			MediaType mimetype = Configuration.getInstance().getOfficialMimeType(media.mt);
			try {
				newUrl = getUrl(pattern,mimetype,getParameters(uriInfo));
			} catch (IOException e) {
				Logger.getAnonymousLogger().log(Level.SEVERE, "IO Error ",e);
				return Response.serverError().entity("Server IO Error").build();
			} catch (TemplateException e) {
				long errorTime = System.currentTimeMillis();
				Logger.getAnonymousLogger().log(Level.SEVERE, "Template Error [" + errorTime + "]",e);
				return Response.serverError().entity("Server Template Error - please report this error ["+errorTime+"]").build();

			}
			if (newUrl != null) break;
			
			
		}
		
		}
		

		// at this point, we should have a newUrl.
		
		// did not find a proper new location for this path and format, so 404
		if (newUrl == null)
			return Response.noContent().build();
		// we have a new URL
		

		try {
			// force a new mediatype if header is set
			MediaType h = (newUrl.header != null && newUrl.header.trim().length()>0)?MediaType.valueOf(newUrl.header):newUrl.m;
			if (newUrl.isProxy)
			{
				URL url = new URL(newUrl.newUrl);
				String out = null;
				if ("ftp".equalsIgnoreCase(url.getProtocol()))
					out = getStringFromFtp(url,newUrl.useAnonFtp);
				else
					out = getSringFromUrl(url);
				if (out != null)
					return Response.ok().entity(out).type(h).build();
				else
					return Response.serverError().entity("Failed to test proxied resource").build();
			}
			else
			{
				// check if we shall use ftp
				
				URI u = new URI(newUrl.newUrl);
				return Response.seeOther(u).type(h).build();
			}
			
		} catch (URISyntaxException | IOException e) {
			Logger.getAnonymousLogger().log(Level.SEVERE, "problem accessing " + newUrl.newUrl,e);
			return Response.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).entity("problem accessing " + newUrl.newUrl + (newUrl.isProxy?" (proxied) ":"")+ e.getMessage()).build();
			
		}
		
		
		
	}
	
	
	
	
	private static String getSringFromUrl(URL url) throws IOException {

		
        URLConnection uc;
        InputStream inputStream = null;

        String output = null;
        try {

            uc = (URLConnection)url.openConnection();
           
            //uc.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:6.0a2) Gecko/20110613 Firefox/6.0a2");
            uc.connect();
            inputStream = uc.getInputStream();
            uc.setConnectTimeout(1000*30);
            output = IOUtils.toString(inputStream,"UTF-8");
        } catch (IOException e) {

            Logger.getAnonymousLogger().log(Level.SEVERE,"failed to get " + url.toExternalForm(),e);
        }
        finally
        {IOUtils.closeQuietly(inputStream);
        }
        
        return output;
        
    }
	
	private static String getStringFromFtp(URL urlTemp,boolean isAnon) throws SocketException, IOException
	{
		
		String server = urlTemp.getHost();
        int port = 21;
        String userInfo = urlTemp.getUserInfo();
        String user = "anonymous";
        String pass = "";
        if (!isAnon && userInfo != null && userInfo.contains(":"))
        {
        	pass = userInfo.split(":")[1];
            user = userInfo.split(":")[0];
        }
        String output = null;

        FTPClient ftpClient = new FTPClient();
        try {

            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE); // we assume encoding is ok

          
            // APPROACH #2: using InputStream retrieveFileStream(String)
           // String remoteFile = "/pub/hydro/GeEau/tests/gsip/prod/030421.json";
            String remoteFile = urlTemp.getPath();
           
            
            InputStream inputStream = ftpClient.retrieveFileStream(remoteFile);
            output = IOUtils.toString(inputStream,"UTF-8");
            

            boolean success = ftpClient.completePendingCommand();
            if (!success) {
               Logger.getAnonymousLogger().log(Level.SEVERE, "failed to complete " + urlTemp.toExternalForm());
            }

            inputStream.close();
     

        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        return output;
		
	}
	
	public static String getPattern(UriInfo info)
	{
		StringBuilder b = new StringBuilder();
		boolean gotData = false;
		for(PathSegment s:info.getPathSegments())
		{
			if ("data".equals(s.getPath())) 
				{
				gotData = true;
				continue;
				}
			// it's not data here, so check if we got data yet
			if (!gotData) continue;
			// we have something here after data
			if (b.length() > 0) b.append("/");
			b.append(s.getPath());
		}
		return b.toString();
		}
	
	/**
	 * get a new URL that matchs this pattern and this mimetype
	 * @param pattern
	 * @param mimetype
	 * @return
	 * @throws TemplateException 
	 * @throws IOException 
	 */
	private NewUrl getUrl(String pattern,MediaType mimetype,Map<String,String> parameters) throws IOException, TemplateException
	{
		
		DataManager d = DataManager.getInstance();
		MatchType m =  d.getMatch(pattern, mimetype);
		if (m != null)
		{
			String u = TemplateManager.getInstance().applyTemplate(m.getSource().getValue(),parameters);
			NewUrl url = new NewUrl(u,m.getSource().isProxy(),mimetype,m.getSource().isUseAnonFtp(),m.getSource().getAltMediaType());
			return url;
		}
		else
		return null;
	}
	
	
	public class NewUrl {
		String newUrl;
		boolean isProxy = false;
		MediaType m;
		boolean useAnonFtp = false;
		String header;
		public NewUrl(String u,boolean proxy,MediaType m,boolean useAnonFtp,String header)
		{
			newUrl = u;
			isProxy = proxy;
			this.m = m;
			this.useAnonFtp = useAnonFtp;
			this.header = header;
		}
	}
	
	private Map<String,String> getParameters(UriInfo u)
	{
		//TODO: this does the same thing than Information.getParameters.  Should consolidate
		Map<String,String> s = new HashMap<String,String>();
		// load the path parameters
		StringBuilder path= new StringBuilder("data");
		// get Configuration parameters
		Hashtable<String,Object> conf = Configuration.getInstance().getParameters();
		for(String k:conf.keySet())
		{
			s.put(k, conf.get(k).toString());
		}
		int c=0;
		boolean gotData = false;
		for(PathSegment p:u.getPathSegments())
		{
			if ("data".equals(p.getPath())) 
				{
				gotData = true;
				continue;
				}
			// it's not data here, so check if we got data yet
			if (!gotData) continue;
			// we have something here after data
			s.put("p"+(++c), p.getPath());
			path.append("/"+p.getPath());
		}
		
		s.put("p0", path.toString()); // p0 = whole path starting from data/
		// now put other usefull stuff
		// hmm.. this was probably ignored ?
		//s.put("baseUri", u.getBaseUri().toString());
		// add all the parameters
		for(String k : u.getQueryParameters().keySet())
		{
			s.put("param_" + k.toLowerCase(),makeCommaDelimited(u.getQueryParameters().get(k)));
		}
		return s;
		
	}
	
	private String makeCommaDelimited(List<String> s)
	{
		return StringUtils.join(s,",");
		
	}
	

}
