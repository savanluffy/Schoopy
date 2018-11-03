/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import com.google.gson.Gson;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pkgData.Database;
import pkgData.Student;
import pkgData.Teacher;

/**
 *
 * @author schueler
 */
@Path("teachers")
public class TeacherService {
    
    Gson gson;
    Database db = null;

    public TeacherService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTeachers() throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllTeachers())).build();
    }

    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTeacher(@PathParam("username") String username) throws Exception {
        Teacher t = db.getTeacher(username);
        Response r = null;
        if (t == null) {
            r = Response.status(Response.Status.NOT_FOUND).entity("teacher not found").build();
        } else {
            r = Response.ok().entity(gson.toJson(t)).build();
        }

        return r;
    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addTeacher(String newTeacher) throws Exception {
        Response r = Response.status(Response.Status.CREATED).entity("teacher added").build();
        try {
            db.addTeacher(gson.fromJson(newTeacher, Teacher.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response loginTeacher(String teacher) throws Exception {
        Response r = null;
        try {
            Teacher t = gson.fromJson(teacher, Teacher.class);
            t = db.loginTeacher(t);
            if (t == null) {
                throw new Exception("no teacher found");
            }
            //String token = Encrypth.generateRandomKey(s.getUsername(), s.getPassword());
            // Authentificator.loginToken(token);
            r = Response.ok().entity(gson.toJson(t)).build();
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @POST
    @Path("/update")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response update(String updateTeacher) {
        Response r = Response.ok().entity("teacher updated").build();
        try {
            boolean isUpdated = db.updateTeacher(gson.fromJson(updateTeacher,Teacher.class));
            if (!isUpdated) {
                throw new Exception("teacher not updated");
            }

        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

//    @POST
//    @Path("/logout")
//    @Consumes({MediaType.APPLICATION_JSON})
//    public Response logout(@Context HttpHeaders httpHeaders) {
//        String token = httpHeaders.getHeaderString("token");
//        System.out.println("kj");
//        Response r = Response.ok().build();
//        if (token == null || !Authentificator.isUserAuthenticated(token)) {
//            r = Response.status(Response.Status.UNAUTHORIZED).entity("you are not authorized.").build();
//        } else {
//            Authentificator.logoutToken(token);
//        }
//        return r;
//    }
}
