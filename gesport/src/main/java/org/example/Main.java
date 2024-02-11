package org.example;

import entities.Activite;
import service.ActiviteService;
import utils.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) {

        //*************** Test Activite **************/
        Activite a1=new Activite("Natation1", "piscine","oui","faire la natation");
        Activite a2=new Activite("Equitation1", "equitation","oui","tout ce qui concerne le cheval");
        Activite a3=new Activite("FootBall1", "football","oui","terrain de football");
        ActiviteService as=new ActiviteService();
        /////////////AJOUT//////////
        //as.add(a1);
        //as.add(a2);
        //as.add(a3);
       // as.readAll().forEach(System.out::println);

        ////////////SURPRESSION///////////
        /*int idasupprimer=1;
        as.deleteById(idasupprimer);
        System.out.println("----------apres suppression--------");*/

        ////////////UPDATE///////////
        /*Activite a3updated=new Activite(3,"FootBall1", "football","non","terrain de football");
        as.update(a3updated);*/
       // as.readAll().forEach(System.out::println);

        ///////////AFFICHE PAR ID///////////
        System.out.println(as.readById(2));
    }
}