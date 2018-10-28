/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.awt.Point;
import java.util.ArrayList;

/**
 *
 * @author schueler
 */
public class Room {
    private String roomId;    //eindeutig
    private String roomDescription;
    private Department department; //is null if it not a class room
    private ArrayList<Point> roomCoordinates;

    public Room() {
    }

    public Room(String roomId, String roomDescription, Department department) {
        this.roomId = roomId;
        this.roomDescription = roomDescription;
        this.department = department;
        roomCoordinates = new ArrayList<>();
    }

    
    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getRoomDescription() {
        return roomDescription;
    }

    public void setRoomDescription(String roomDescription) {
        this.roomDescription = roomDescription;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    
}
