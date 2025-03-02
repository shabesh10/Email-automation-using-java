import java.util.ArrayList;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class Automation {

    public static void main(String[] args) {
        // Email ID of Recipients stored in an ArrayList
        ArrayList<String> recipients = new ArrayList<>();

        //adding email ids manually
        recipients.add("");

        // Email ID of the sender
        String sender = "";
        String senderPassword = "";

        // using google's smtp as the host
        String host = "smtp.gmail.com";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");

        // creating session object to get properties
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, senderPassword);
            }
        });

        try {
            // MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            // Set To Field: adding recipient's email to from field.
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            }

            // setting the subject of the email
            message.setSubject("Email Automation using java testing - 1");

            // setting the body of the email
            message.setText("This is a test mail");

            // Send email
            Transport.send(message);
            System.out.println("Mail successfully sent");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}