/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author schueler
 */
public class Room {
    private String roomNr;    //eindeutig
    private String roomDescription;
    private Department department; //is null if it not a class room
    private ArrayList<Point> roomCoordinates;

    public Room() {
    }

    public Room(String roomNr, String roomDescription, Department department) {
        this.roomNr = roomNr;
        this.roomDescription = roomDescription;
        this.department = department;
        roomCoordinates = new ArrayList<>();
    }

    
    public String getRoomNr() {
        return roomNr;
    }

    public void setRoomNr(String roomNr) {
        this.roomNr = roomNr;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.roomNr);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Room other = (Room) obj;
        if (!Objects.equals(this.roomNr, other.roomNr)) {
            return false;
        }
        return true;
    }

    
}
