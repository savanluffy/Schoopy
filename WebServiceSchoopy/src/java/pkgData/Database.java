/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.security.spec.KeySpec;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import pkgMisc.Encrypth;

/**
 *
 * @author schueler
 */
public class Database {

    private static final String CONNECTSTRING = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";
    //private static final String CONNECTSTRING = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
    private static final String USER = "d5a11";
    private static final String PASSWD = "d5a";
    private Connection conn = null;

    /**
     * Singleton
     */
    private static Database database = null;

    public static Database newInstance() throws Exception {
        if (database == null) {
            database = new Database();
        }

        return database;
    }

    private Database() throws Exception {
    }

    private Connection createConnection() throws Exception {
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        return DriverManager.getConnection(CONNECTSTRING, USER, PASSWD);
    }

    public void registerStudent(Student student) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO Student VALUES(?,?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, student.getUsername());
        student.setPassword(Encrypth.hashPW(student.getPassword()));
        stmt.setString(2, student.getPassword());
        stmt.setString(3, student.getFirstName());
        stmt.setString(4, student.getLastName());
        stmt.setString(5, student.getSchoolemail());
        stmt.setString(6, student.getVisitedClass().getRoomNr());
        stmt.executeQuery();
        conn.close();
    }

    public Student loginStudent(Student student) throws Exception {
        conn = createConnection();
        String select = "SELECT * FROM Student WHERE username=? and password=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, student.getUsername());
        stmt.setString(2, Encrypth.hashPW(student.getPassword()));
        ResultSet rs = stmt.executeQuery();
        Student foundStudent = null;
        while (rs.next()) {
            foundStudent = getStudentValues(rs);
        }
        conn.close();
        return foundStudent;
    }

    private Student getStudentValues(ResultSet rs) throws Exception {
        Student newStudent = new Student(getRoom(rs.getString("visitedClass")), rs.getString("firstName"),
                rs.getString("lastName"), rs.getString("schoolemail"), rs.getString("username"), rs.getString("password"));

        return newStudent;
    }

    public boolean updateStudent(Student s) throws Exception {
        boolean res = false;
        conn = createConnection();
        String select = "UPDATE Student SET firstName=?,lastName=?,schoolemail=?,visitedClass=?, password=? where username=?";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, s.getFirstName());
        stmt.setString(2, s.getLastName());
        stmt.setString(3, s.getSchoolemail());
        stmt.setString(4, s.getVisitedClass().getRoomNr());
        stmt.setString(5, Encrypth.hashPW(s.getPassword()));
        stmt.setString(6, s.getUsername());
        int numOfUpdatedRows = stmt.executeUpdate();
        if (numOfUpdatedRows == 1) {
            res = true;
        }
        conn.close();

        return res;
    }

    public Collection<Student> getAllStudents() throws Exception {
        ArrayList<Student> collStudents = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM Student";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collStudents.add(getStudentValues(rs));
        }
        conn.close();
        return collStudents;
    }

    public Student getStudent(String username) throws Exception {
        conn = createConnection();
        String select = "SELECT * FROM Student WHERE username=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        Student foundStudent = null;
        while (rs.next()) {
            foundStudent = getStudentValues(rs);
        }
        conn.close();
        return foundStudent;
    }

    public Collection<Room> getAllRooms() throws Exception {
        ArrayList<Room> collRooms = new ArrayList<>();

        conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Room ORDER BY roomNr");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collRooms.add(getRoomValues(rs));
        }
        conn.close();
        return collRooms;
    }

    public Room getRoom(String roomNr) throws Exception {
        conn = createConnection();
        String select = "SELECT * FROM Room WHERE roomNr=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, roomNr);
        ResultSet rs = stmt.executeQuery();
        Room foundRoom = null;
        while (rs.next()) {
            foundRoom = getRoomValues(rs);
        }
        conn.close();
        return foundRoom;
    }

    private Room getRoomValues(ResultSet rs) throws Exception {
        return (new Room(rs.getString("roomNr"), rs.getString("roomDescription"), Department.valueOf(rs.getString("department"))));
    }

    public void addRoom(Room r) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO Room VALUES(?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, r.getRoomNr());
        stmt.setString(2, r.getRoomDescription());
        stmt.setString(3, r.getDepartment().name());

        stmt.executeQuery();
        conn.close();
    }

    public void updateRoom(Room roomToUpdate) throws Exception {
        conn = createConnection();

        String select = "UPDATE Room SET roomDescription=?, department=? WHERE roomNr = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, roomToUpdate.getRoomDescription());
        stmt.setString(2, roomToUpdate.getDepartment().name());
        stmt.setString(3, roomToUpdate.getRoomNr());

        stmt.executeQuery();
        conn.close();
    }

    public void deleteRoom(String roomNr) throws Exception {
        conn = createConnection();

        String select = "DELETE FROM Room WHERE roomNr=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, roomNr);
        stmt.executeQuery();
        conn.close();
    }

    public Collection<Teacher> getAllTeachers() throws Exception {
        ArrayList<Teacher> collTeachers = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM Teacher";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collTeachers.add(getTeacherValues(rs));
        }
        conn.close();
        return collTeachers;
    }

    public Teacher getTeacher(String username) throws Exception {
        conn = createConnection();
        String select = "SELECT * FROM Teacher WHERE username=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        Teacher foundTeacher = null;
        while (rs.next()) {
            foundTeacher = getTeacherValues(rs);
        }
        conn.close();
        return foundTeacher;
    }

    public void addTeacher(Teacher newTeacher) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO Teacher VALUES(?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, newTeacher.getUsername());
        newTeacher.setPassword(Encrypth.hashPW(newTeacher.getPassword()));
        stmt.setString(2, newTeacher.getPassword());
        stmt.setString(3, newTeacher.getFirstName());
        stmt.setString(4, newTeacher.getLastName());
        stmt.setString(5, newTeacher.getSchoolemail());
        stmt.executeQuery();
        conn.close();
    }

    public Teacher loginTeacher(Teacher t) throws Exception {
        conn = createConnection();
        String select = "SELECT * FROM Teacher WHERE username=? and password=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, t.getUsername());
        stmt.setString(2, Encrypth.hashPW(t.getPassword()));
        ResultSet rs = stmt.executeQuery();
        Teacher foundTeacher = null;
        while (rs.next()) {
            foundTeacher = getTeacherValues(rs);
        }
        conn.close();
        return foundTeacher;
    }

    public boolean updateTeacher(Teacher t) throws Exception {
        boolean res = false;
        conn = createConnection();
        String select = "UPDATE Teacher SET firstName=?,lastName=?,schoolemail=?, password=? where username=?";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, t.getFirstName());
        stmt.setString(2, t.getLastName());
        stmt.setString(3, t.getSchoolemail());
        stmt.setString(4, Encrypth.hashPW(t.getPassword()));
        stmt.setString(5, t.getUsername());
        int numOfUpdatedRows = stmt.executeUpdate();
        if (numOfUpdatedRows == 1) {
            res = true;
        }
        conn.close();

        return res;
    }

    private Teacher getTeacherValues(ResultSet rs) throws Exception {
        return new Teacher(rs.getString("firstName"), rs.getString("lastName"), rs.getString("schoolemail"), rs.getString("username"), rs.getString("password"));

    }

    public Collection<Message> getChatMessages(String roomNr) throws Exception {
        ArrayList<Message> collChatMessages = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM ChatMessage WHERE classNr = ? ORDER BY messageId";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, roomNr);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collChatMessages.add(getChatMessageValues(rs));
        }
        conn.close();
        return collChatMessages;
    }

    private Message getChatMessageValues(ResultSet rs) throws Exception {
        Message chatMessage = new Message(rs.getInt("messageId"), getStudent(rs.getString("sender")), rs.getString("message"),
                rs.getDate("sendDate").toLocalDate(), getRoom(rs.getString("classNr")));
        return chatMessage;
    }

    public void deleteChatMessage(int messageId) throws Exception {
        conn = createConnection();
        String select = "DELETE FROM ChatMessage WHERE messageId=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, messageId);
        stmt.executeQuery();
        conn.close();
    }

    public void addChatMessage(Message msg) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO ChatMessage VALUES(seqMessage.NEXTVAL,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, msg.getChatRoom().getRoomNr());
        stmt.setString(2, msg.getSender().getUsername());
        stmt.setString(3, msg.getMessage());
        stmt.setDate(4, Date.valueOf(LocalDate.now()));
        stmt.executeQuery();
        conn.close();
    }

    public Collection<PublicFile> getAllPublicFiles() throws Exception {
        ArrayList<PublicFile> collPublicFiles = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM PublicFile";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collPublicFiles.add(getPublicFileValues(rs));
        }
        conn.close();
        return collPublicFiles;
    }

    private PublicFile getPublicFileValues(ResultSet rs) throws Exception {
        PublicFile pf = new PublicFile(rs.getInt("fileId"), rs.getString("fileName"), rs.getBytes("fileContent"),
                rs.getDate("publishDate").toLocalDate(), getTeacher(rs.getString("publisherTeacher")));
        return pf;
    }

    public void addPublicFile(PublicFile newPubicFile) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO PublicFile VALUES(seqPublicFile.NEXTVAL,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, newPubicFile.getFileName());
        stmt.setBytes(2, newPubicFile.getFileContent());
        stmt.setDate(3, Date.valueOf(LocalDate.now()));
        stmt.setString(4, newPubicFile.getPublisherTeacher().getUsername());
        stmt.executeQuery();
        conn.close();
    }

    public void deletePublicFile(int fileId) throws Exception {
        conn = createConnection();
        String select = "DELETE FROM PublicFile WHERE fileId=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, fileId);
        stmt.executeQuery();
        conn.close();
    }

    public Collection<PrivateFile> getPrivateFiles(String folderRoomNr) throws Exception {
        ArrayList<PrivateFile> collPrivateFiles = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM PrivateFile WHERE folderRoom=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, folderRoomNr);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collPrivateFiles.add(getPrivateFileValues(rs));
        }
        conn.close();
        return collPrivateFiles;
    }

    private PrivateFile getPrivateFileValues(ResultSet rs) throws Exception {
        PrivateFile pf = new PrivateFile(rs.getInt("fileId"), rs.getString("fileName"), rs.getBytes("fileContent"),
                rs.getDate("publishDate").toLocalDate(), getTeacher(rs.getString("publisherTeacher")), getStudent(rs.getString("publisherStudent")), getRoom(rs.getString("folderRoom")));
        return pf;

    }

    public void addPrivateFile(PrivateFile newPrivateFile) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO PrivateFile VALUES(seqPrivateFile.NEXTVAL,?,?,?,?,?,?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, newPrivateFile.getFileName());
        stmt.setBytes(2, newPrivateFile.getFileContent());
        stmt.setDate(3, Date.valueOf(LocalDate.now()));
        Teacher t = newPrivateFile.getPublisherTeacher();
        if (t == null) {
            stmt.setString(4, null);
            stmt.setString(5, newPrivateFile.getPublisherStudent().getUsername());

        } else {
            stmt.setString(4, t.getUsername());
            stmt.setString(5, null);
        }
        stmt.setString(6, newPrivateFile.getFolderRoom().getRoomNr());
        stmt.executeQuery();
        conn.close();
    }

    public void deletePrivateFile(int fileId) throws Exception {
        conn = createConnection();
        String select = "DELETE FROM PrivateFile WHERE fileId=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, fileId);
        stmt.executeQuery();
        conn.close();
    }

}
