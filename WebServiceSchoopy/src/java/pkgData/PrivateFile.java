/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.time.LocalDate;

/**
 *
 * @author schueler
 */
public class PrivateFile extends PublicFile{
    private Student publisherStudent;
    private Room folderRoom;

    public PrivateFile(int fileId, String fileName, byte[] fileContent, LocalDate publishDate, Teacher publisherTeacher,Student publisherStudent, Room folderRoom) {
        super(fileId, fileName, fileContent, publishDate, publisherTeacher);
        this.publisherStudent = publisherStudent;
        this.folderRoom = folderRoom;
    }

    

    public Student getPublisherStudent() {
        return publisherStudent;
    }

    public void setPublisherStudent(Student publisherStudent) {
        this.publisherStudent = publisherStudent;
    }

    public Room getFolderRoom() {
        return folderRoom;
    }

    public void setFolderRoom(Room folderRoom) {
        this.folderRoom = folderRoom;
    }

    @Override
    public String toString() {
        return "PrivateFolderContent{" + "publisherStudent=" + publisherStudent + ", folderRoom=" + folderRoom + '}';
    }
  
    
    
}
