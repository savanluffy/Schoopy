/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import javax.ws.rs.Path;
import java.util.Collection;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
/**
 *
 * @author schueler
 */
@Path("rooms")
public class RoomService {
        
    @Context
    private UriInfo context;
    //Gson gson;
    //Database db = null;


    public RoomService() {
         try{
            //db = Database.newInstance();
            //gson = new Gson();
        }catch(Exception ex){
            System.out.println("error while trying to create db.");
        }
    }
    
    //gets all articles ordered by articleId (default)
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getArticles() throws Exception {  
        return Response.ok().entity("sie wollen das ich falle").build();
       // return Response.ok().entity(gson.toJson(db.getAllArticles())).build();       
    }
    
}
