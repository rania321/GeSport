package tn.Gesport.test;

import tn.Gesport.models.Reclamation;
import tn.Gesport.services.ServiceReclamation;

import java.sql.Date;

public class MainReclamation {

    public static void main(String[] args) {

        ServiceReclamation serviceReclamation = new ServiceReclamation();

        ServiceReclamation sr = new ServiceReclamation();
        Reclamation reclamation = new Reclamation();
        sr.add(reclamation);
        System.out.println(sr.getAll());

        System.out.println();

      //  System.out.println(sr.delete(sr.getById(1)));

    }
}
