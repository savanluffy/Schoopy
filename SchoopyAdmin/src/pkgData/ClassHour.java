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
public class ClassHour {

    Lesson[] dayLessons = new Lesson[18];
    private int hour;

    public ClassHour(int hour) {
        this.hour = hour;
    }

    public Lesson[] getDayLessons() {
        return dayLessons;
    }

    public void setDayLessons(Lesson[] dayLessons) {
        this.dayLessons = dayLessons;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    @Override
    public String toString() {
        return "ClassHour{" + "dayLessons=" + dayLessons + ", hour=" + hour + '}';
    }
    
    
    
}
