/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.util.Objects;

/**
 *
 * @author schueler
 */
public class Lesson {
    private Room schoolRoom;
    private Room teachingRoom;
    private Teacher teacher;
    private Subject teachingSubject;
    private WeekDay weekDay;
    private int schoolHour;

    public Lesson(Room schoolRoom, Room teachingRoom, Teacher teacher, Subject teachingSubject, WeekDay weekDay, int schoolHour) {
        this.schoolRoom = schoolRoom;
        this.teachingRoom = teachingRoom;
        this.teacher = teacher;
        this.teachingSubject = teachingSubject;
        this.weekDay = weekDay;
        this.schoolHour = schoolHour;
    }

    public Room getSchoolRoom() {
        return schoolRoom;
    }

    public void setSchoolRoom(Room schoolRoom) {
        this.schoolRoom = schoolRoom;
    }

    public Room getTeachingRoom() {
        return teachingRoom;
    }

    public void setTeachingRoom(Room teachingRoom) {
        this.teachingRoom = teachingRoom;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getTeachingSubject() {
        return teachingSubject;
    }

    public void setTeachingSubject(Subject teachingSubject) {
        this.teachingSubject = teachingSubject;
    }

    public WeekDay getWeekDay() {
        return weekDay;
    }

    public void setWeekDay(WeekDay weekDay) {
        this.weekDay = weekDay;
    }

    public int getSchoolHour() {
        return schoolHour;
    }

    public void setSchoolHour(int schoolHour) {
        this.schoolHour = schoolHour;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.schoolRoom);
        hash = 59 * hash + Objects.hashCode(this.weekDay);
        hash = 59 * hash + this.schoolHour;
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
        final Lesson other = (Lesson) obj;
        if (this.schoolHour != other.schoolHour) {
            return false;
        }
        if (!Objects.equals(this.schoolRoom, other.schoolRoom)) {
            return false;
        }
        if (this.weekDay != other.weekDay) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Lesson{" + "schoolRoom=" + schoolRoom + ", teachingRoom=" + teachingRoom + ", teacher=" + teacher + ", teachingSubject=" + teachingSubject + ", weekDay=" + weekDay + ", schoolHour=" + schoolHour + '}';
    }
    
    
    
}
