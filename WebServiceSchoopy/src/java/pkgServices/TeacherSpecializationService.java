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
import pkgData.Subject;
import pkgData.TeacherSpecialization;

/**
 *
 * @author schueler
 */
@Path("teacherspecializations")
public class TeacherSpecializationService {

    Gson gson;
    Database db = null;

    public TeacherSpecializationService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAllTeacherSpecializations() throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllTeacherSpecializations())).build();

    }

    @GET
    @Path("/allSubjects/{teacherUN}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTeacherSpecializationsByTeacher(@PathParam("teacherUN") String teacherUsername) throws Exception {
        return Response.ok().entity(gson.toJson(db.getTeacherSpecializationsByTeacher(teacherUsername))).build();

    }



    @GET
    @Path("/allTeachers/{subjectId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTeacherSpecializationsBySubject(@PathParam("subjectId") int subjectId) throws Exception {
        return Response.ok().entity(gson.toJson(db.getTeacherSpecializationsBySubject(subjectId))).build();

    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addNewTeacherSpecialization(String newTeacherSpecialization) throws Exception {

        Response r = Response.status(Response.Status.CREATED).entity("TeacherSpecialization created").build();
        try {
            db.addTeacherSpecialization(gson.fromJson(newTeacherSpecialization, TeacherSpecialization.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @DELETE
    @Path("/{teacherUN}/{subjectId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteTeacherSpecialization(@PathParam("teacherUN") String teacherUN, @PathParam("subjectId") int subjectId) throws Exception {
        Response isDeleted = Response.ok().build();
        try {
            db.deleteTeacherSpecialization(teacherUN, subjectId);
        } catch (Exception ex) {
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        return isDeleted;
    }
}
