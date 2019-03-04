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
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import pkgData.Database;
import pkgData.Department;
import pkgData.Room;
import pkgData.Student;

/**
 *
 * @author schueler
 */
@Path("students")
public class StudentService {

    Gson gson;
    Database db = null;

    public StudentService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getStudents() throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllStudents())).build();
    }

    @GET
    @Path("/{username}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getStudent(@PathParam("username") String username) throws Exception {
        Student s = db.getStudent(username);
        Response r = null;
        if (s == null) {
            r = Response.status(Response.Status.NOT_FOUND).entity("student not found").build();
        } else {
            r = Response.ok().entity(gson.toJson(s)).build();
        }

        return r;
    }

    @POST
    @Path("/register")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response registerStudent(String newStudent) throws Exception {
        Response r = Response.status(Response.Status.CREATED).entity("student registered").build();
        try {
            db.registerStudent(gson.fromJson(newStudent, Student.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response loginStudent(String newStudent) throws Exception {
        Response r = null;
        try {
            Student s = gson.fromJson(newStudent, Student.class);
            s = db.loginStudent(s);
            if (s == null) {
                throw new Exception("no user found");
            }
            r = Response.ok().entity(gson.toJson(s)).build();
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @PUT
    @Path("/update")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateStudent(String updateStudent) {
        Response r = Response.ok().entity("student updated").build();
        try {
            boolean isUpdated = db.updateStudent(gson.fromJson(updateStudent,Student.class));
            if (!isUpdated) {
                throw new Exception("student not updated");
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
