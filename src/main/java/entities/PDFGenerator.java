package entities;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import javafx.scene.control.Alert;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

public class PDFGenerator {
    public static byte[] generatePDF(Reservation reservation, User u) {
        try {
            Document document = new Document();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, outputStream);
            document.open();

            // Ajouter un cadre autour de la page
            Rectangle rect = new Rectangle(577, 825, 18, 15);
            rect.enableBorderSide(1);
            rect.enableBorderSide(2);
            rect.enableBorderSide(4);
            rect.enableBorderSide(8);
            rect.setBorderColor(new BaseColor(23, 68, 122));
            rect.setBorderWidth(5);
            document.add(rect);

            LineSeparator ls = new LineSeparator();
            ls.setLineColor(new BaseColor(135, 206, 235)); // Couleur bleu ciel

            document.add(new Chunk(ls));

            Font font = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, new BaseColor(27, 117, 187));
            Chunk chunk = new Chunk("Confirmation de la Reservation", font);
            Paragraph p = new Paragraph(chunk);
            p.setAlignment(Element.ALIGN_CENTER); // Centrer le texte
            document.add(p);
            // Ajouter la date actuelle
            String date = new SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date());
            Font dateFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD, BaseColor.BLACK); // Date en noir

            Chunk dateChunk = new Chunk(date, dateFont);
            Paragraph dateParagraph = new Paragraph(dateChunk);
            dateParagraph.setAlignment(Element.ALIGN_CENTER); // Centrer la date
            document.add(new Chunk(ls)); // Ajouter un autre séparateur de ligne
            document.add(dateParagraph);
            // Ajouter une autre ligne horizontale de couleur bleu ciel
            document.add(new Chunk(ls));
            // Paragraphe dédié au client
            Font clientFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
            Paragraph clientParagraph = new Paragraph();
            clientParagraph.add(new Phrase("Cher(e) " + u.getNomU() + " " + u.getPrenomU() + ",\n\n", clientFont));
            clientParagraph.add(new Phrase("Merci d'avoir réservé une activité dans notre centre sportif. Nous sommes impatients de vous accueillir et de vous aider à atteindre vos objectifs de remise en forme.\n\n", clientFont));
            clientParagraph.add(new Phrase("Ci-dessous, vous trouverez les détails de votre réservation :\n\n", clientFont));
            document.add(clientParagraph);
            // Ajouter des espaces vides avant le tableau
            document.add(new Paragraph("\n\n\n\n\n"));

            // Ajouter une image à droite du texte
            Image img1 = Image.getInstance("C:\\Users\\Rania\\Desktop\\gesport\\gesport\\src\\main\\resources\\img\\reservation (1).png");
            img1.scaleToFit(40f, 40f); // Redimensionner l'image
            img1.setAbsolutePosition(500f, 760); // Positionner l'image
            document.add(img1);
            PdfPTable table = new PdfPTable(1);

            Font fonte = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL, BaseColor.BLACK);

            PdfPCell cell;
            p = new Paragraph("Nom de l'activité' : " + reservation.getActiviteNom());
            cell = new PdfPCell(p);
            cell.setBorder(Rectangle.BOX);
            cell.setPadding(10);
            cell.setBackgroundColor(BaseColor.WHITE); // Gris clair
            table.addCell(cell);

            p = new Paragraph("Nom et prénom du client : " + u.getNomU() + " " + u.getPrenomU(), fonte);
            cell = new PdfPCell(p);
            cell.setBorder(Rectangle.BOX);
            cell.setPadding(10);
            cell.setBackgroundColor(new BaseColor(224, 224, 224)); // Gris clair
            table.addCell(cell);

            p = new Paragraph("Date de l'activité: " + reservation.getDateDebutR(), fonte);
            cell = new PdfPCell(p);
            cell.setBorder(Rectangle.BOX);
            cell.setPadding(10);
            cell.setBackgroundColor(BaseColor.WHITE);
            table.addCell(cell);

            p = new Paragraph("Heure de l'activité: " + reservation.getHeureR(), fonte);
            cell = new PdfPCell(p);
            cell.setBorder(Rectangle.BOX);
            cell.setPadding(10);
            cell.setBackgroundColor(new BaseColor(224, 224, 224)); // Gris clair
            table.addCell(cell);


            document.add(table);

            // Ajouter des espaces vides après le tableau
            document.add(new Paragraph("\n\n\n\n\n\n\n\n\n\n"));

            // Ajouter une autre photo en haut à gauche
            Image img2 = Image.getInstance("C:\\Users\\Rania\\Desktop\\gesport\\gesport\\src\\main\\resources\\img\\logo.png");
            img2.scaleToFit(80f, 40f); // Redimensionner l'image
            img2.setAbsolutePosition(50f, 760f); // Positionner l'image
            document.add(img2);

            // Générer le QR code pour le texte de réservation
            String reservationText = "Activite : " + reservation.getActiviteNom();
            byte[] qrCodeBytes = QRCodeGenerator.generateQRCodeImage(reservationText, 200, 200);

            // Ajouter le QR code à votre document PDF
            Image qrCodeImage = Image.getInstance(qrCodeBytes);
            qrCodeImage.setAbsolutePosition(200f, 120f); // Positionnez l'image comme vous le souhaitez
            qrCodeImage.setAlignment(Element.ALIGN_CENTER);
            document.add(qrCodeImage);
            // Ajouter une autre photo à droite tout en bas
                   /* Image img3 = Image.getInstance("C:\\gestionPlanningNew\\src\\main\\resources\\imgs\\.jpg");
                    img3.scaleAbsolute(100f, 70); // Redimensionner l'image
                    img3.setAbsolutePosition(450f, 50f); // Positionner l'image
                    document.add(img3);*/
            document.add(new Paragraph("\n\n\n\n\n\n"));

            Font footerFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
            Paragraph footer = new Paragraph("Centre Sportif, 123 Rue de la Paix, 75000 Ariana,nTel : 99646424 ,Email : info@centresportif.com", footerFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("PDF Generated");
            alert.setContentText("Reservation information has been saved to reservations.pdf");
            alert.showAndWait();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
