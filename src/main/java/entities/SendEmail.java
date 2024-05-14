package entities;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class SendEmail {

    public static void send(String toEmail ,byte[] attachmentData, Reservation reservation, User user ) {
        String from = "dawser.ghzel@esprit.tn";
        String host = "smtp.gmail.com";

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        Session session = Session.getInstance(properties, new javax.mail.Authenticator(){
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("dawser.ghzel@esprit.tn", "xofh iywn bqlu ytqy");
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            try {
                message.setFrom(new InternetAddress(from, "GeSport", "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("Confirmation de réservation");
            // Create Multipart
            Multipart multipart = new MimeMultipart();

            // Add Text Part
            BodyPart textBodyPart = new MimeBodyPart();
            textBodyPart.setText("Cher(e) " + user.getNomU() + " " + user.getPrenomU() + ",\n\n" +
                    "Merci d'avoir réservé une activité dans notre centre sportif. Nous sommes impatients de vous accueillir et de vous aider à atteindre vos objectifs de remise en forme.\n\n" +
                    "Ci-dessous, vous trouverez les détails de votre réservation :\n\n" +
                    "Nom de l'activité : " + reservation.getActiviteNom() + "\n" +
                    "Date de l'activité : " + reservation.getDateDebutR() + "\n" +
                    "Heure de l'activité : " + reservation.getHeureR() + "\n");
            multipart.addBodyPart(textBodyPart);

            // Add PDF Part
            BodyPart pdfBodyPart = new MimeBodyPart();
            pdfBodyPart.setDataHandler(new DataHandler(new ByteArrayDataSource(attachmentData, "application/pdf")));
            pdfBodyPart.setFileName("Confirmation_Reservation.pdf");
            multipart.addBodyPart(pdfBodyPart);

            // Set Content
            message.setContent(multipart);

            // Send the message
            Transport.send(message);
            System.out.println("Email sent successfully to " + toEmail);
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}