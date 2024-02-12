package org.example;



import enitites.Reclamation;
import enitites.commentaire;
import service.ReclamationService;
import service.commentaireService;
import utils.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
/*public class Main {


    public static  void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        Date DateRec= null;
        try {
            DateRec = sdf.parse("11/02/2024");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Reclamation p1=new Reclamation(2,"dawseraza",DateRec,"aaaaa","aaaeazee");
        ReclamationService rs=new ReclamationService();
        rs.add(p1);


        }
    }*/

public class Main {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date DateRec = null;
        Date DateC=null;
        try {
            DateRec = sdf.parse("11/02/2024");
            DateC=sdf.parse("11/03/2000");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        // Convert java.util.Date to java.sql.Date
        java.sql.Date sqlDate = new java.sql.Date(DateRec.getTime());

        Reclamation p1 = new Reclamation(1, "dawseraza", sqlDate, "aaaaa", "aaaeazee");
        ReclamationService rs = new ReclamationService();
        //rs.add(p1);
        java.sql.Date sqlDate1 = new java.sql.Date(DateC.getTime());
        commentaire p2 = new commentaire(16,sqlDate1,"ABBCBCB");
        commentaireService cs=new commentaireService();
        //cs.add(p2);
        ////////supprimer reclamation/////////
        //int idRecsupprime=6;
       // rs.deleteById(idRecsupprime);
        /////supprimer commentaire////
        //int idCsupprime=4;
        //cs.deleteById(idCsupprime);
        try {
            Reclamation r4updated=new Reclamation(6,1,"oui",sdf.parse("26/06/2024"),"erty","erty");
            rs.update(r4updated);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}





