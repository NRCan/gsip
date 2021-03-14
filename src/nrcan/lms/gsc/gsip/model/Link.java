package nrcan.lms.gsc.gsip.model;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FilenameUtils;

import freemarker.template.SimpleScalar;
import freemarker.template.TemplateModel;

public class Link {
	private String label;
	private String url;
	private String resLabel;
	private String mimetype;
	public String getLabel()
	{
		return label==null?"":label;
	}
	public String getUrl()
	{
		return url == null?"":url;
	}
	
	/**
	 * returns the target of the url (the last part)
	 * @return
	 */
	public TemplateModel getUrlTarget()
	{
		try {
			URL u = new URL(url);
			String fname = FilenameUtils.getName(u.getPath());
			if (fname != null)
				return new SimpleScalar(fname);
			else
				return TemplateModel.NOTHING;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			return TemplateModel.NOTHING;
		}
		
	}
	
	public String getResLabel()
	{
		return resLabel==null?"":resLabel;
	}
	
	public Link(String label,String url,String resLabel)
	{
		this.label = label;
		this.url = url;
		this.resLabel = resLabel;
	}
	
	public void setMimeType(String mt)
	{
		this.mimetype = mt;
	}
	
	public String getMimeType()
	{
		return mimetype==null?"":mimetype;
	}

}
