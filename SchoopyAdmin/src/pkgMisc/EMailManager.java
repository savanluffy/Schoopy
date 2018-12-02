/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgMisc;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import pkgData.Teacher;

/**
 *
 * @author schueler
 */
public class EMailManager {

    public static void sendEmailToTeacher(Teacher t) throws Exception {
        String to = t.getSchoolemail();//change accordingly
        String from = "savanluffy@gmail.com";//change accordingly
        String host = "localhost";//or IP address

        //Get the session object
        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);
  

        Session session = Session.getDefaultInstance(properties);//this is without authentification

        //compose the message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Login Daten für die Teacher App");

        String msg = readEmailHTMLTemplate();
        msg = msg.replace("{lastName}", t.getLastName());
        msg = msg.replace("{userName}", t.getUsername());
        msg = msg.replace("{passWord}", t.getPassword());

        message.setContent(msg, "text/html");

        Transport.send(message);// Send message
        System.out.println("email has been send");
    }

    private static String readEmailHTMLTemplate() throws Exception {
        InputStream d = EMailManager.class.getResourceAsStream("/pkgResources/emailTemplate.html");

        Scanner s = new Scanner(d).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static boolean crunchifyEmailValidator(String email) {
        boolean isValid = false;
        try {
            //
            // Create InternetAddress object and validated the supplied
            // address which is this case is an email address.
            InternetAddress internetAddress = new InternetAddress(email);
            internetAddress.validate();
            isValid = true;
        } catch (AddressException e) {
            isValid = false;
        }
        return isValid;
    }
}
