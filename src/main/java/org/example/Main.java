package org.example;
//package java.sql;

import entities.Produit;
import entities.Vente;
import org.example.Service.ProduitService;
import org.example.Service.VenteService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Pour avoir la date actuelle
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        Produit p1= new Produit("Salade", "Cesar", 25.4f, 5, sqlDate);
        ProduitService ps= new ProduitService();

       /* if (p1.getPrixP() != null) {
            float prix = p1.getPrixP().floatValue();
            ProduitService ps= new ProduitService();
            ps.add(p1);
        } else {
            System.out.println("Le prix du produit est null.");
        }*/
        //ps.delete(p1);



        Vente v1= new Vente(2, 1, 10, sqlDate, 72.55f);
        VenteService vs= new VenteService();
        //vs.add(v1);
        //vs.delete(v1);
        vs.readAll();


    }
}
