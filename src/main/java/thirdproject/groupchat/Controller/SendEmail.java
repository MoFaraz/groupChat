package thirdproject.groupchat.Controller;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {
    public static String send (String emailAddress) {
        // Sender's email ID needs to be mentioned
        String from = "mopharaz@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");


        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            public PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("mopharaz@gmail.com", "wsadf1@zyx");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);
        String temp = "";
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));

            // Set Subject: header field
            message.setSubject("Verification Code");

            // Now set the actual message
            temp = String.valueOf(generateRandomNum());
            message.setText("Please Enter " + temp + " In Verification Box");

            System.out.println("sending...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return temp;
    }
    private static int generateRandomNum() {
        int min = 1000;
        int max = 9999;
        return (int)Math.floor(Math.random()*(max-min+1)+min);
    }
}
