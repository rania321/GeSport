package org.example;
//package java.sql;

import entities.Produit;
import org.example.Service.ProduitService;

import java.util.Calendar;
import java.util.Date;

public class Main {
    public static void main(String[] args) {

        Produit p1= new Produit("Tomate", "Fraiche", 2f, 2000, new Date(2024, Calendar.JANUARY,8));
        ProduitService ps= new ProduitService();
        ps.add(p1);

    }



}
