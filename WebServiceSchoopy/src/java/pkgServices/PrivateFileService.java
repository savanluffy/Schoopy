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
import pkgData.PrivateFile;

/**
 *
 * @author schueler
 */
@Path("privatefiles")
public class PrivateFileService {

    Gson gson;
    Database db = null;

    public PrivateFileService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }
   
    @GET
    @Path("/{folderRoomNr}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPrivateFilesWithoutContent(@PathParam("folderRoomNr") String folderRoomNr) throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllPrivateFilesWithoutContent(folderRoomNr))).build();
    }
    
    @GET
    @Path("/{folderRoomNr}/{fileId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getPrivateFileWithContent(@PathParam("folderRoomNr") String folderRoomNr,@PathParam("fileId") int fileId) throws Exception {
        return Response.ok().entity(gson.toJson(db.getPrivateFileWithContent(folderRoomNr,fileId))).build();
    }
    
    

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addNewPrivateFile(String newPrivateFile) throws Exception {
        Response r = Response.status(Response.Status.CREATED).entity("publicfile created").build();
        try {

            db.addPrivateFile(gson.fromJson(newPrivateFile, PrivateFile.class));
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
            db.deletePrivateFile(fileId);
        } catch (Exception ex) {
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        return isDeleted;
    }
}
