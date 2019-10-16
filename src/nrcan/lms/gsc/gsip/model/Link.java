package nrcan.lms.gsc.gsip.model;

public class Link {
	private String label;
	private String url;
	private String resLabel;
	private String mimetype;
	public String getLabel()
	{
		return label;
	}
	public String getUrl()
	{
		return url;
	}
	
	public String getResLabel()
	{
		return resLabel;
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
		return mimetype;
	}

}
