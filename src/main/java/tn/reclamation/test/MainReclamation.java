package tn.Gesport.test;

import tn.Gesport.models.Reclamation;
import tn.Gesport.services.ServiceReclamation;

import java.sql.Date;

public class MainReclamation {

    public static void main(String[] args) {
        Date date = Date.valueOf("2024-02-22");

        Reclamation reclamation = new Reclamation(1, 1,"Gesport",
                date , "test", "status");

        ServiceReclamation serviceReclamation = new ServiceReclamation();

        ServiceReclamation sr = new ServiceReclamation();
        sr.add(reclamation);
        System.out.println(sr.getAll());

        System.out.println();

        System.out.println(sr.delete(sr.getById(8)));

    }
}
