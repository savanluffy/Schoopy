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
import pkgData.SchoopyAdmin;
import pkgData.Teacher;

/**
 *
 * @author schueler
 */
@Path("schoppyadmins")
public class SchoopyAdminService {

    Gson gson;
    Database db = null;

    public SchoopyAdminService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getTest() throws Exception {
        return Response.ok().entity("test").build();
    }

    @POST
    @Path("/add")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addSchoopyAdmin(String newSchoopyAdmin) throws Exception {
        Response r = Response.status(Response.Status.CREATED).entity("schoopyadmin added").build();
        try {
            db.addSchoopyAdmin(gson.fromJson(newSchoopyAdmin, SchoopyAdmin.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @POST
    @Path("/login")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response loginSchoopyAdmin(String schoopyAdmin) throws Exception {
        Response r = null;
        try {
            SchoopyAdmin sa = gson.fromJson(schoopyAdmin, SchoopyAdmin.class);
            sa = db.loginSchoopyAdmin(sa);
            if (sa == null) {
                throw new Exception("no schoopyAdmin found");
            }
            r = Response.ok().entity(gson.toJson(sa)).build();
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }
}
