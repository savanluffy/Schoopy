/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import oracle.spatial.geometry.JGeometry;
import oracle.sql.STRUCT;
import pkgMisc.Encrypth;
import pkgServices.SchoopyAdminService;

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

    public Collection<Room> getAllSchoolRooms() throws Exception {
        ArrayList<Room> collRooms = new ArrayList<>();

        conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Room WHERE department != 'NONE' ORDER BY roomNr");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collRooms.add(getRoomValues(rs));
        }
        conn.close();
        return collRooms;
    }

    public Collection<Room> getAllTeachingRooms(String roomNr) throws Exception {
        ArrayList<Room> collRooms = new ArrayList<>();

        conn = createConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Room WHERE department = 'NONE' OR roomNr = ?  ORDER BY roomNr");
        stmt.setString(1, roomNr);
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
        STRUCT st = (oracle.sql.STRUCT) rs.getObject("roomCoordinates");
        JGeometry roomCoordinates = JGeometry.loadJS(st);
        return (new Room(rs.getString("roomNr"), rs.getString("roomDescription"), Department.valueOf(rs.getString("department")), roomCoordinates.getOrdinatesArray()));
    }

    public void addRoom(Room r) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO Room VALUES(?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, r.getRoomNr());
        stmt.setString(2, r.getRoomDescription());
        stmt.setString(3, r.getDepartment().name());
        STRUCT obj = (STRUCT) JGeometry.storeJS(conn, new JGeometry(JGeometry.GTYPE_POLYGON, 0, new int[]{1, 1003, 1}, r.getRoomCoordinates()));
        stmt.setObject(4, obj);

        stmt.executeQuery();
        conn.close();
    }

    public void updateRoom(Room roomToUpdate) throws Exception {
        if (roomToUpdate.getDepartment().equals(Department.NONE)) {
            deleteLessonsBySchoolRoom(roomToUpdate);
        }else{
            Room r = getRoom(roomToUpdate.getRoomNr());
            if(r.getDepartment().equals(Department.NONE)){
                deleteLessonsByTeachingRoom(roomToUpdate);
            }
           
        }
        conn = createConnection();

        String select = "UPDATE Room SET roomDescription=?, department=?,roomCoordinates=? WHERE roomNr = ?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, roomToUpdate.getRoomDescription());
        stmt.setString(2, roomToUpdate.getDepartment().name());
        STRUCT obj = (STRUCT) JGeometry.storeJS(conn, new JGeometry(JGeometry.GTYPE_POLYGON, 0, new int[]{1, 1003, 1}, roomToUpdate.getRoomCoordinates()));
        stmt.setObject(3, obj);
        stmt.setString(4, roomToUpdate.getRoomNr());

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

    public Collection<Teacher> filterTeachers(String filterVal) throws Exception {
        ArrayList<Teacher> collTeachers = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM Teacher WHERE upper(username) LIKE upper(?) or upper(firstName) LIKE upper(?)"
                + " or upper(lastName) LIKE upper(?) or upper(schoolemail) LIKE upper(?)";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, "%" + filterVal + "%");
        stmt.setString(2, "%" + filterVal + "%");
        stmt.setString(3, "%" + filterVal + "%");
        stmt.setString(4, "%" + filterVal + "%");

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
        String select = "UPDATE Teacher SET firstName=?,lastName=?,schoolemail=? where username=?";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, t.getFirstName());
        stmt.setString(2, t.getLastName());
        stmt.setString(3, t.getSchoolemail());
        stmt.setString(4, t.getUsername());
        int numOfUpdatedRows = stmt.executeUpdate();
        if (numOfUpdatedRows == 1) {
            res = true;
        }
        conn.close();

        return res;
    }

    public void deleteTeacher(String username) throws Exception {

        deletePublicFileByTeacher(username);
        deletePrivateFileByTeacher(username);
        deleteTeacherSpecializationsByTeacher(username);
        conn = createConnection();
        String select = "DELETE FROM Teacher WHERE username=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        stmt.executeQuery();
        conn.close();
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

    //helper
    private void deletePublicFileByTeacher(String username) throws Exception {
        conn = createConnection();
        String select = "DELETE FROM PublicFile WHERE publisherTeacher=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
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

    private void deletePrivateFileByTeacher(String username) throws Exception {
        conn = createConnection();
        String select = "DELETE FROM PrivateFile WHERE publisherTeacher=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        stmt.executeQuery();
        conn.close();
    }

    public Collection<Subject> getAllSubjects() throws Exception {
        ArrayList<Subject> collSubjects = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM Subject";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collSubjects.add(getSubjectValues(rs));
        }
        conn.close();
        return collSubjects;
    }

    public Collection<Subject> filterSubjects(String filterValue) throws Exception {
        ArrayList<Subject> collSubjects = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM Subject WHERE upper(subjectName) LIKE upper(?) or upper(subjectShortcut) LIKE upper(?) ";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, "%" + filterValue + "%");
        stmt.setString(2, "%" + filterValue + "%");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collSubjects.add(getSubjectValues(rs));
        }
        conn.close();
        return collSubjects;
    }

    public Subject getSubject(int subjectId) throws Exception {
        conn = createConnection();
        String select = "SELECT * FROM Subject WHERE subjectId=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, subjectId);
        ResultSet rs = stmt.executeQuery();
        Subject foundSubject = null;
        while (rs.next()) {
            foundSubject = getSubjectValues(rs);
        }
        conn.close();
        return foundSubject;
    }

    public void addSubject(Subject newSubject) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO Subject VALUES(seqSubject.NEXTVAL,?,?)";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, newSubject.getSubjectName());
        stmt.setString(2, newSubject.getSubjectShortcut());

        stmt.executeQuery();
        conn.close();
    }

    public boolean updateSubject(Subject updateS) throws Exception {
        boolean res = false;
        conn = createConnection();
        String select = "UPDATE Subject SET subjectName=?, subjectShortcut=? where subjectId=?";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, updateS.getSubjectName());
        stmt.setString(2, updateS.getSubjectShortcut());
        stmt.setInt(3, updateS.getSubjectId());
        int numOfUpdatedRows = stmt.executeUpdate();
        if (numOfUpdatedRows == 1) {
            res = true;
        }
        conn.close();
        return res;
    }

    public void deleteSubject(int subjectId) throws Exception {
        deleteTeacherSpecializationsBySubject(subjectId);
        conn = createConnection();
        String select = "DELETE FROM Subject WHERE subjectId=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, subjectId);
        stmt.executeQuery();
        conn.close();
    }

    private Subject getSubjectValues(ResultSet rs) throws Exception {
        return (new Subject(rs.getInt("subjectId"), rs.getString("subjectName"), rs.getString("subjectShortcut")));

    }

    public Collection<TeacherSpecialization> getAllTeacherSpecializations() throws Exception {
        ArrayList<TeacherSpecialization> collTeacherSpecialization = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM TeacherSpecialization";
        PreparedStatement stmt = conn.prepareStatement(select);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collTeacherSpecialization.add(getTeacherSpecializationValues(rs));
        }
        conn.close();
        return collTeacherSpecialization;
    }

    public Collection<TeacherSpecialization> getTeacherSpecializationsByTeacher(String teacherUsername) throws Exception {
        ArrayList<TeacherSpecialization> collTeacherSpecialization = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM TeacherSpecialization WHERE teacherUN=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, teacherUsername);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collTeacherSpecialization.add(getTeacherSpecializationValues(rs));
        }
        conn.close();
        return collTeacherSpecialization;
    }

    public Collection<TeacherSpecialization> getTeacherSpecializationsBySubject(int subjectId) throws Exception {
        ArrayList<TeacherSpecialization> collTeacherSpecialization = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM TeacherSpecialization WHERE subjectId=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, subjectId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collTeacherSpecialization.add(getTeacherSpecializationValues(rs));
        }
        conn.close();
        return collTeacherSpecialization;
    }

    private TeacherSpecialization getTeacherSpecializationValues(ResultSet rs) throws Exception {
        return new TeacherSpecialization(getTeacher(rs.getString("teacherUN")), getSubject(rs.getInt("subjectId")));
    }

    public void addTeacherSpecialization(TeacherSpecialization newTeacherSpecialization) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO TeacherSpecialization VALUES(?,?)";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, newTeacherSpecialization.getTeacher().getUsername());
        stmt.setInt(2, newTeacherSpecialization.getSubject().getSubjectId());

        stmt.executeQuery();
        conn.close();
    }

    public void deleteTeacherSpecialization(String teacherUN, int subjectId) throws Exception {
        deleteLessonsByTeacherSpecialization(teacherUN, subjectId);
        conn = createConnection();
        String select = "DELETE FROM TeacherSpecialization WHERE teacherUN=? AND subjectId=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, teacherUN);
        stmt.setInt(2, subjectId);
        stmt.executeQuery();
        conn.close();
    }

    //helper
    private void deleteTeacherSpecializationsBySubject(int subjectId) throws Exception {
        deleteLessonsBySubject(subjectId);
        conn = createConnection();
        String select = "DELETE FROM TeacherSpecialization WHERE subjectId=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, subjectId);
        stmt.executeQuery();
        conn.close();
    }

    //helper
    private void deleteTeacherSpecializationsByTeacher(String username) throws Exception {
        deleteLessonsByTeacher(username);
        conn = createConnection();
        String select = "DELETE FROM TeacherSpecialization WHERE teacherUN=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        stmt.executeQuery();
        conn.close();
    }

    //helper
    private void deleteLessonsBySubject(int subjectId) throws Exception {
        conn = createConnection();
        String select = "DELETE FROM Lesson WHERE teachingSubject=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setInt(1, subjectId);
        stmt.executeQuery();
        conn.close();
    }

    private void deleteLessonsByTeacher(String username) throws Exception {
        conn = createConnection();
        String select = "DELETE FROM Lesson WHERE teacherUN=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, username);
        stmt.executeQuery();
        conn.close();
    }

    //helper
    private void deleteLessonsByTeacherSpecialization(String teacherUN, int subjectId) throws Exception {
        conn = createConnection();
        String select = "DELETE FROM Lesson WHERE teacherUN=? AND teachingSubject=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, teacherUN);
        stmt.setInt(2, subjectId);
        stmt.executeQuery();
        conn.close();
    }

    private void deleteLessonsBySchoolRoom(Room room) throws Exception {
        conn = createConnection();
        String select = "DELETE FROM Lesson WHERE schoolRoom=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, room.getRoomNr());
        stmt.executeQuery();
        conn.close();
    }

    private void deleteLessonsByTeachingRoom(Room room) throws Exception {  
        conn = createConnection();
        String select = "DELETE FROM Lesson WHERE teachingRoom=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, room.getRoomNr());
        stmt.executeQuery();
        conn.close();
    }

    public Collection<Lesson> getAllLessonsBySchoolRoom(String roomNr) throws Exception {
        ArrayList<Lesson> collLessons = new ArrayList<>();

        conn = createConnection();
        String select = "SELECT * FROM Lesson WHERE schoolRoom=? order by schoolHour";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, roomNr);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collLessons.add(getLessonValues(rs));
        }
        conn.close();
        return collLessons;
    }

    private Lesson getLessonValues(ResultSet rs) throws Exception {

        Lesson l = new Lesson(getRoom(rs.getString("schoolRoom")), getRoom(rs.getString("teachingRoom")),
                getTeacherSpecializations(rs.getString("teacherUN"), rs.getInt("teachingSubject")), WeekDay.valueOf(rs.getString("weekDay")), rs.getInt("schoolHour"));
        return l;
    }

    private TeacherSpecialization getTeacherSpecializations(String teacherUN, int teachingSubject) throws Exception {
        TeacherSpecialization teacherSpecialization = null;

        conn = createConnection();
        String select = "SELECT * FROM TeacherSpecialization WHERE teacherUN=? AND subjectId=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, teacherUN);
        stmt.setInt(2, teachingSubject);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            teacherSpecialization = getTeacherSpecializationValues(rs);
        }
        conn.close();
        return teacherSpecialization;
    }

    public Collection<Lesson> getAllLessonsByTeacher(String teacherUN) throws Exception {
        ArrayList<Lesson> collLessons = new ArrayList<>();
        conn = createConnection();
        String select = "SELECT * FROM Lesson WHERE teacherUN=? ";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, teacherUN);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collLessons.add(getLessonValues(rs));
        }
        conn.close();
        return collLessons;
    }
    
    public Collection<Lesson> getAllLessonsByTeacherAtCurrDay(String teacherUN,String wd) throws Exception {
        ArrayList<Lesson> collLessons = new ArrayList<>();
        conn = createConnection();
        String select = "SELECT * FROM Lesson WHERE teacherUN=? and weekDay=? order by schoolhour";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, teacherUN);
        stmt.setString(2, wd);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            collLessons.add(getLessonValues(rs));
        }
        conn.close();
        return collLessons;
    }

    public void addLesson(Lesson newLesson) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO Lesson VALUES(?,?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, newLesson.getSchoolRoom().getRoomNr());
        stmt.setString(2, newLesson.getTeachingRoom().getRoomNr());
        stmt.setString(3, newLesson.getTeachingInfo().getTeacher().getUsername());
        stmt.setInt(4, newLesson.getTeachingInfo().getSubject().getSubjectId());
        stmt.setString(5, newLesson.getWeekDay().name());
        stmt.setInt(6, newLesson.getSchoolHour());
        stmt.executeQuery();
        conn.close();

    }

    public boolean updateLesson(Lesson updateLesson) throws Exception {

        boolean res = false;
        conn = createConnection();

        String select = "UPDATE Lesson SET teachingRoom=?, teachingSubject=?,teacherUN=? WHERE schoolRoom = ? AND schoolHour=? AND "
                + " weekDay=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, updateLesson.getTeachingRoom().getRoomNr());
        stmt.setInt(2, updateLesson.getTeachingInfo().getSubject().getSubjectId());
        stmt.setString(3, updateLesson.getTeachingInfo().getTeacher().getUsername());
        stmt.setString(4, updateLesson.getSchoolRoom().getRoomNr());
        stmt.setInt(5, updateLesson.getSchoolHour());
        stmt.setString(6, updateLesson.getWeekDay().name());
        int numOfUpdatedRows = stmt.executeUpdate();
        if (numOfUpdatedRows == 1) {
            res = true;
        }
        conn.close();
        return res;
    }

    public void deleteLesson(String schoolRoom, String weekDay, int schoolHour) throws Exception {
        conn = createConnection();
        String select = "DELETE FROM Lesson WHERE schoolRoom=? AND weekDay=? AND schoolHour=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, schoolRoom);
        stmt.setString(2, weekDay);
        stmt.setInt(3, schoolHour);
        stmt.executeQuery();
        conn.close();
    }

    public SchoopyAdmin loginSchoopyAdmin(SchoopyAdmin sa) throws Exception {
        conn = createConnection();
        String select = "SELECT * FROM SchoopyAdmin WHERE username=? and password=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, sa.getUsername());
        stmt.setString(2, Encrypth.hashPW(sa.getPassword()));
        ResultSet rs = stmt.executeQuery();
        SchoopyAdmin foundSchoopyAdmin = null;
        while (rs.next()) {
            foundSchoopyAdmin = getSchoopyAdminValues(rs);
        }
        conn.close();
        return foundSchoopyAdmin;
    }

    public void addSchoopyAdmin(SchoopyAdmin newSA) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO SchoopyAdmin VALUES(?,?)";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, newSA.getUsername());
        stmt.setString(2, Encrypth.hashPW(newSA.getPassword()));
        stmt.executeQuery();
        conn.close();
    }

    private SchoopyAdmin getSchoopyAdminValues(ResultSet rs) throws Exception {
        return new SchoopyAdmin(rs.getString("username"), rs.getString("password"));
    }

}
