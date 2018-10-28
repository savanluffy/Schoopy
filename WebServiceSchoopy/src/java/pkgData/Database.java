/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author schueler
 */
public class Database {
    // private static final String CONNECTSTRING = "jdbc:oracle:thin:@localhost:1521:orcl";

    //    private static final String CONNECTSTRING = "jdbc:oracle:thin:@212.152.179.117:1521:ora11g";
    private static final String CONNECTSTRING = "jdbc:oracle:thin:@192.168.128.152:1521:ora11g";
    private static final String USER = "d4a10";
    private static final String PASSWD = "d4a";
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

    private String hashPW(String pw) throws Exception {
        byte[] salt = new byte[16];
        KeySpec spec = new PBEKeySpec(pw.toCharArray(), salt, 65536, 128);
        SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = f.generateSecret(spec).getEncoded();

        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(hash);
    }

    public void registerStudent(Student s) throws Exception {
        conn = createConnection();
        String select = "INSERT INTO Student VALUES(?,?,?,?,?,?)";

        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, s.getFirstName());
        s.setPassword(hashPW(s.getPassword()));
        stmt.setString(2, s.getPassword());
        stmt.setString(3, s.getLastName());
        stmt.setString(4, s.getSchoolemail());
        stmt.setString(5, s.getUsername());
        stmt.setString(6, s.getVisitedClass().getRoomId());
        stmt.executeQuery();
        conn.close();
    }

    public boolean updateStudentPW(Student student, String newPW) throws Exception {
        boolean res = false;
        conn = createConnection();
        String select = "UPDATE Student SET password=? where username=? and password=?";
        PreparedStatement stmt = conn.prepareStatement(select);
        stmt.setString(1, hashPW(newPW));
        stmt.setString(2, student.getUsername());
        stmt.setString(3, hashPW(student.getPassword()));
        int numOfUpdatedRows = stmt.executeUpdate();
        if(numOfUpdatedRows==1)
            res=true;
        conn.close();

        return res;
    }
}
