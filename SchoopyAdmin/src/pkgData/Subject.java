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
public class Subject {

    private int subjectId;
    private String subjectName;
    private String subjectShortcut;

    public Subject(int subjectId, String subjectName, String subjectShortcut) {
        this.subjectId = subjectId;
        this.subjectName = subjectName;
        this.subjectShortcut = subjectShortcut;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectShortcut() {
        return subjectShortcut;
    }

    public void setSubjectShortcut(String subjectShortcut) {
        this.subjectShortcut = subjectShortcut;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.subjectId;
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
        final Subject other = (Subject) obj;
        if (this.subjectId != other.subjectId) {
            return false;
        }
        return true;
    }

}
