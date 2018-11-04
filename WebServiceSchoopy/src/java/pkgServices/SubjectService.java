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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pkgData.Database;
import pkgData.Room;
import pkgData.Subject;

/**
 *
 * @author schueler
 */
@Path("subjects")
public class SubjectService {

    Gson gson;
    Database db = null;

    public SubjectService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getSubjects() throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllSubjects())).build();
    }

    @GET
    @Path("/{subjectId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getSubject(@PathParam("subjectId") int subjectId) throws Exception {
        Subject subject = db.getSubject(subjectId);
        Response r = null;
        if (subject == null) {
            r = Response.status(Response.Status.NOT_FOUND).entity("subject not found").build();
        } else {
            r = Response.ok().entity(gson.toJson(subject)).build();
        }

        return r;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addNewSubject(String newSubject) throws Exception {

        Response r = Response.status(Response.Status.CREATED).entity("subject created").build();
        try {

            db.addSubject(gson.fromJson(newSubject, Subject.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateSubject(String subjectToUpdate) throws Exception {
        Response r = Response.ok().entity("subject updated").build();
        try {
            boolean isUpdated = db.updateSubject(gson.fromJson(subjectToUpdate, Subject.class));
            if(isUpdated==false)
                throw new Exception("subject not updated");
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @DELETE
    @Path("/{subjectId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteSubject(@PathParam("subjectId") int subjectId) throws Exception {
        Response isDeleted = Response.ok().build();
        try {
            db.deleteSubject(subjectId);
        } catch (Exception ex) {
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        return isDeleted;
    }
}
