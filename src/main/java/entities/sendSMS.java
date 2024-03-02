package entities;
import entities.Reservation;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
public class sendSMS {


    public static final String ACCOUNT_SID = "AC3b0d87cdc9d609e43c102058c7a60285";
    public static final String AUTH_TOKEN = "b060f29bb3b6280b404cda04ba18846c";

    public static void sendSMS(Reservation r) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21699646424"),
                        new com.twilio.type.PhoneNumber("+17814304946"),
                        "Votre réservation pour l'activité "+ r.getActiviteNom()+ " est confirmée pour la date "+ r.getDateDebutR() + " à "+r.getHeureR())
                .create();

        System.out.println(message.getSid());
    }
}
