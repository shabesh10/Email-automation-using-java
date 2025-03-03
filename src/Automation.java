import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Automation {

    public static void main(String[] args) {
        // Email ID of Recipients stored in an ArrayList
        ArrayList<String> recipients = new ArrayList<>();

        // adding email ids manually
        recipients.add("shaheethsm@gmail.com");
        recipients.add("shabeshvaran@gmail.com");
        recipients.add("sasi.d9864@gmail.com");
        recipients.add("srivisnu25@gmail.com");

        // Email ID of the sender
        String sender = "sshabesh10@gmail.com";
        String senderPassword = "";

        // using google's smtp as the host
        String host = "smtp.gmail.com";

        Properties properties = new Properties(); //subclass of hashtable(String, String)
        properties.put("mail.smtp.auth", "true"); 
        /*tells JavaMail that the SMTP server requires authentication (i.e., a valid Gmail username and password)*/
        properties.put("mail.smtp.starttls.enable", "true"); 
        /*enables TLS (Transport Layer Security), which encrypts the communication between your Java application and Gmail's SMTP server.*/
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        // creating session object to get properties
        /* The Session class in Java is a key component of the JavaMail API, representing a mail session and providing access to protocol providers that implement the Store, Transport, and related classes.
        It collects together properties and defaults used by the mail API's, allowing a single default session to be shared by multiple applications on the desktop. */

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, senderPassword);
            }
        });

        try {
            // MimeMessage object, since it supports various objects
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                // setting the subject of the email
                message.setSubject("Email Automation using java testing - 2");

                // setting the body of the email
                message.setText("This is a group test mail");

                // Sending email
                Transport.send(message);
            }

        System.out.println("Mails successfully sent");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}