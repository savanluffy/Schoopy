/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import com.google.gson.Gson;
import java.time.LocalDate;
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
import pkgData.Department;
import pkgData.Message;
import pkgData.Room;
import pkgData.Student;

/**
 *
 * @author schueler
 */
@Path("messages")
public class MessageService {

    Gson gson;
    Database db = null;

    public MessageService() {
        try {
            db = Database.newInstance();
            gson = new Gson();
        } catch (Exception ex) {
            System.out.println("error while trying to create db.");
        }
    }

    @GET
    @Path("/{classRoomNr}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getChatMessages(@PathParam("classRoomNr") String roomNr) throws Exception {
        return Response.ok().entity(gson.toJson(db.getChatMessages(roomNr))).build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public Response addNewChatMessage(String newMessage) throws Exception {
        Response r = Response.status(Response.Status.CREATED).entity("message added").build();
        try {
            db.addChatMessage(gson.fromJson(newMessage, Message.class));
        } catch (Exception ex) {
            r = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }
        return r;
    }
    @DELETE
    @Path("/{messageId}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deleteChatMessage(@PathParam("messageId") int messageId) throws Exception {
        Response isDeleted = Response.ok().build();
        try {
            db.deleteChatMessage(messageId);
        } catch (Exception ex) {
            isDeleted = Response.status(Response.Status.BAD_REQUEST).entity(ex.getMessage()).build();
        }

        return isDeleted;
    }
}
