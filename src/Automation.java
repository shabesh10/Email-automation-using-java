import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

public class Automation {

    public static void main(String[] args) {
        try {
            // Email ID of Recipients stored in an ArrayList
            ArrayList<String> recipients = new ArrayList<>();

            // adding email ids manually
            // recipients.add("shaheethsm@gmail.com");
            recipients.add("shabeshvaran@gmail.com");
            // recipients.add("sasi.d9864@gmail.com");
            // recipients.add("srivisnu25@gmail.com");
            recipients.add("sivammfsl@gmail.com");
            recipients.add("jayareddy0609@gmail.com");

            // Email ID of the sender
            String sender = System.getenv("EMAIL_USERNAME");
            String senderPassword = System.getenv("EMAIL_PASSWORD");


            // using google's smtp as the host
            String host = "smtp.gmail.com";

            Properties properties = new Properties(); // subclass of hashtable(String, String)
            properties.put("mail.smtp.auth", "true");
            /*
             * tells JavaMail that the SMTP server requires authentication (i.e., a valid
             * Gmail username and password)
             */
            properties.put("mail.smtp.starttls.enable", "true");
            /*
             * enables TLS (Transport Layer Security), which encrypts the communication
             * between your Java application and Gmail's SMTP server.
             */
            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", "587");

            // creating session object to get properties
            /*
             * The Session class in Java is a key component of the JavaMail API,
             * representing a mail session and providing access to protocol providers that
             * implement the Store, Transport, and related classes.
             * It collects together properties and defaults used by the mail API's, allowing
             * a single default session to be shared by multiple applications on the
             * desktop.
             */

            Session session = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sender, senderPassword);
                }
            });

            // MimeMessage object, since it supports various objects
            MimeMessage message = new MimeMessage(session);

            // Set From Field: adding senders email to from field.
            message.setFrom(new InternetAddress(sender));

            try {
                String imageUrl = "https://zenquotes.io/api/image";
                String destinationFile = "image.jpg";
                saveImage(imageUrl, destinationFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

            // setting the subject and adding text to the email
            message.setSubject("Check out this quote");
            message.setText("Go chase your goals, you've go this!");

            // adding image to the content

            MimeMultipart multipart = new MimeMultipart();

            // Create a MimeBodyPart for the image attachment
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource("image.jpg");
            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setFileName("image.jpg");

            // Add the image part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Set the multipart as the content of the MimeMessage
            message.setContent(multipart);

            // Set To Field: adding recipient's email to from field.
            for (String recipient : recipients) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
                // Sending email
            }
            Transport.send(message);


            System.out.println("Mails successfully sent");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }
}