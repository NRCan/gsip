package nrcan.lms.gsc.gsip.geo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.StreamingOutput;

/**
 * streams data to a Response 
 * @author Eric Boisvert
 * Laboratoire de Cartographie Numérique et de Photogrammétrie
 * Commission géologique du Canada (c) 2018
 * Ressources naturelles Canada
 */
public class ResponseStreamer  implements StreamingOutput {

	public ResponseStreamer(Handler h)
	{
		this.h  = h;
	}
	private Handler h;
	@Override
	public void write(OutputStream output) throws IOException, WebApplicationException {
		Writer writer = new BufferedWriter(new OutputStreamWriter(output));
		h.serialize(writer);
		writer.flush();

	}

}
