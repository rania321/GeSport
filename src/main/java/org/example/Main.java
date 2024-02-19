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
        Date utilDate = new Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        Produit p1= new Produit("Riz", "Riz", 100f, 50, sqlDate);
        ProduitService ps= new ProduitService();
        //ps.add(p1);
        //ps.delete(p1);

        Vente v1= new Vente(2, 1, 70, sqlDate, 77.45f);
        VenteService vs= new VenteService();
        //vs.add(v1);
        //vs.delete(v1);
        //vs.update(v1,7);
        //vs.getIdVFromDatabase(v1);
        //vs.readAll();
    }
}
