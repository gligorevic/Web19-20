package controller;

import java.io.File;
import java.io.IOException;
import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

@Path("/images")
public class ImageController {

	@GET
	@Path("/{id}/{filename}")
    public Response loadAsResource(@PathParam("id") Long id, @PathParam("filename") String filename) throws IOException {
		File f = new File("images/" + id + "/" + filename);
		
		if (!f.exists()) {
			throw new WebApplicationException(404);
	    }
		String mt = new MimetypesFileTypeMap().getContentType(f);
	    
		return Response.ok(f, mt).build();
    }
}
