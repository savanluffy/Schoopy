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
import pkgData.Lesson;
import pkgData.Room;
import pkgData.Subject;

/**
 *
 * @author schueler
 */
@Path("lessons")
public class LessonService {
    
    Gson gson;
    Database db = null;

    public LessonService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    @GET
    @Path("/{roomNr}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLessonsBySchoolRoom(@PathParam("roomNr") String roomNr) throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllLessonsBySchoolRoom(roomNr))).build();
    }

    @GET
    @Path("/teachers/{teacherUN}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getLessonsByTeacher(@PathParam("teacherUN") String teacherUN) throws Exception {
       return Response.ok().entity(gson.toJson(db.getAllLessonsByTeacher(teacherUN))).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addNewLesson(String newLesson) throws Exception {

        Response r = Response.status(Response.Status.CREATED).entity("new lesson created").build();
        try {

            db.addLesson(gson.fromJson(newLesson, Lesson.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }
   
    
    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateLesson(String lessonToUpdate) throws Exception {
        Response r = Response.ok().build();
        try {
            boolean res = db.updateLesson(gson.fromJson(lessonToUpdate, Lesson.class));
            if(res==false)
                throw new Exception("lesson not updated");
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }
    
    @DELETE
    @Path("/{schoolRoom}/{weekDay}/{schoolHour}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteLesson(@PathParam("schoolRoom") String schoolRoom,@PathParam("weekDay") String weekDay,@PathParam("schoolHour") int schoolHour) throws Exception {
        Response isDeleted = Response.ok().build();
        try {
            db.deleteLesson(schoolRoom,weekDay,schoolHour);
        } catch (Exception ex) {
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        return isDeleted;
    }
}