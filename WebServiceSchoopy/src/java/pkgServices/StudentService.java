/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import com.google.gson.Gson;
import java.sql.SQLException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import pkgData.Database;
import pkgData.Student;
import pkgMisc.Authentificator;

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

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response register(String newStudent) throws Exception {
        Response r = Response.ok().build();
        try {
            db.registerStudent(gson.fromJson(newStudent, Student.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updatePW(@Context HttpHeaders httpHeaders,@PathParam("username") String un) {
        String token = httpHeaders.getHeaderString("token");
        String newPW = httpHeaders.getHeaderString("newPW");
        String newPjW = httpHeaders.getHeaderString("newPW");
        Response r = Response.ok().build();
        if (token == null || !Authentificator.isUserAuthenticated(token)) {
           r = Response.status(Response.Status.UNAUTHORIZED).entity("you are not authorized.").build();
        }else{
            
        }
        return null;
    }
}
