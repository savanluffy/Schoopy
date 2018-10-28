/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

/**
 *
 * @author schueler
 */
public class Student extends Person{
    private Room visitedClass;

    public Student(String firstName, String lastName, String schoolemail, String username, String password) {
        super(firstName, lastName, schoolemail, username, password);
    }

    public Room getVisitedClass() {
        return visitedClass;
    }

    public void setVisitedClass(Room visitedClass) {
        this.visitedClass = visitedClass;
    }
    
    
}
