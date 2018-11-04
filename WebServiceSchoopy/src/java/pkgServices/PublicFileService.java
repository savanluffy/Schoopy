/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pkgData.Database;
import pkgData.PublicFile;

/**
 *
 * @author schueler
 */
@Path("publicfiles")
public class PublicFileService {

    Gson gson;
    Database db = null;

    public PublicFileService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPublicFiles() throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllPublicFiles())).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addNewPublicFile(String newPublicFile) throws Exception {
        Response r = Response.status(Response.Status.CREATED).entity("publicfile created").build();
        try {

            db.addPublicFile(gson.fromJson(newPublicFile, PublicFile.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @DELETE
    @Path("/{fileId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePublicFile(@PathParam("fileId") int fileId) throws Exception {
        Response isDeleted = Response.ok().build();
        try {
            db.deletePublicFile(fileId);
        } catch (Exception ex) {
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        return isDeleted;
    }

}
