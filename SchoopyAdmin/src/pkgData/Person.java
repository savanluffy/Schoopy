/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.util.Objects;

/**
 *
 * @author schueler
 */
public abstract class Person {
    String firstName, lastName;
    String schoolemail;
    String username; //for teachers its kürzel for schueler its lastname+first letter of firstname
    String password;  

    public Person(){
    
    }
    
    public Person(String firstName, String lastName, String schoolemail, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.schoolemail = schoolemail;
        this.username = username;
        this.password = password;
    }
    public Person(String firstName, String lastName, String schoolemail, String username) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.schoolemail = schoolemail;
        this.username = username;
    }

    
    
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSchoolemail() {
        return schoolemail;
    }

    public void setSchoolemail(String schoolemail) {
        this.schoolemail = schoolemail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.username);
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
        final Person other = (Person) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + ", schoolemail=" + schoolemail + ", username=" + username;
    }
    
    
    
}
