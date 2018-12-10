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
import pkgData.Department;
import pkgData.Room;
import pkgData.Student;

/**
 *
 * @author schueler
 */
@Path("rooms")
public class RoomService {

    Gson gson;
    Database db = null;

    public RoomService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRooms() throws Exception {
        return Response.ok().entity(gson.toJson(db.getAllRooms())).build();
    }

    @GET
    @Path("/{roomNr}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getRoom(@PathParam("roomNr") String roomNr) throws Exception {
        Room room = db.getRoom(roomNr);
        Response r = null;
        if (room == null) {

            r = Response.status(Response.Status.NOT_FOUND).entity("room not found").build();
        } else {
            r = Response.ok().entity(gson.toJson(room)).build();
        }

        return r;
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addNewRoom(String newRoom) throws Exception {

        Response r = Response.status(Response.Status.CREATED).entity("room created").build();
        try {

            db.addRoom(gson.fromJson(newRoom, Room.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public Response updateRoom(String roomToUpdate) throws Exception {
        Response r = Response.ok().build();
        try {
            db.updateRoom(gson.fromJson(roomToUpdate, Room.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }

    @DELETE
    @Path("/{roomNr}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteRoom(@PathParam("roomNr") String roomNr) throws Exception {
        Response isDeleted = Response.ok().build();
        try {
            db.deleteRoom(roomNr);
        } catch (Exception ex) {
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        return isDeleted;
    }
}
