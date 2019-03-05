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
public class PublicFile {
    int fileId;
    String fileName;
    byte[] fileContent;
    LocalDate publishDate;
    Teacher publisherTeacher;

    public PublicFile(int fileId, String fileName, byte[] fileContent, LocalDate publishDate, Teacher publisherTeacher) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.publishDate = publishDate;
        this.publisherTeacher = publisherTeacher;
    }
    
        public PublicFile(int fileId, String fileName, LocalDate publishDate, Teacher publisherTeacher) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.publishDate = publishDate;
        this.publisherTeacher = publisherTeacher;
    }

    public Teacher getPublisherTeacher() {
        return publisherTeacher;
    }

    public void setPublisherTeacher(Teacher publisherTeacher) {
        this.publisherTeacher = publisherTeacher;
    }


    

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + this.fileId;
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
        final PublicFile other = (PublicFile) obj;
        if (this.fileId != other.fileId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "FolderContent{" + "fileId=" + fileId + ", fileName=" + fileName + ", fileContent=" + fileContent + ", publishDate=" + publishDate + ", publisher=" + publisherTeacher + '}';
    }


    
}
