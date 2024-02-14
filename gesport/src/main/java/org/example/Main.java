package org.example;

import entities.Activite;
import entities.Reservation;
import entities.User;
import service.ActiviteService;
import service.ReservationService;
import service.UserService;
import utils.DataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {

    public static void main(String[] args) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");

        //user
        User u=new User();
        UserService us= new UserService();
        Activite a=new Activite();

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
        /*int idasupprimer=3;
        as.deleteById(idasupprimer);
        System.out.println("----------apres suppression--------");*/

        ////////////UPDATE///////////
        /*Activite a3updated=new Activite(3,"FootBall1", "football","non","terrain de football");
        as.update(a3updated);*/
       // as.readAll().forEach(System.out::println);

        ///////////AFFICHE PAR ID///////////
        /*System.out.println(as.readById(2));*/

        //**************** Test Reservation ******************/
        int numA=2;

        Activite actAddReserv=as.readById(numA);
        User userAddReserv=us.readById(2);

        Date dateDebut1 = sdf.parse("11/02/2024 09:00");
        Date dateFin1 = sdf.parse("11/02/2024 12:00");
        //Reservation r1=new Reservation(2, 2,dateDebut1,dateFin1,"en attente");
        Reservation r2=new Reservation(userAddReserv,actAddReserv,sdf.parse("13/02/2024 08:00"),sdf.parse("13/02/2024 09:30"),"confirmé");
       // Reservation r3=new Reservation(2, 2,sdf.parse("03/03/2024 08:00"),sdf.parse("03/03/2024 09:30"),"en attente");
        ReservationService rs=new ReservationService();
        //////////////AJOUT////////
        //rs.add(r1);
        //rs.add(r2);
        //rs.add(r3);
        //rs.readAll().forEach(System.out::println);

        //////////////SUPRESSION//////////
        //rs.delete(r1); //matemchich
        /*int idrsupprimer=6;
        rs.deleteById(idrsupprimer);*/

        //////////////UPDATE//////////
        Reservation r2updated=new Reservation(16, userAddReserv,actAddReserv,sdf.parse("26/06/2024 08:00"),sdf.parse("26/06/2024 09:30"),"en attente");
        Reservation r22=new Reservation(21,userAddReserv,actAddReserv,sdf.parse("13/02/2024 08:00"),sdf.parse("13/02/2024 09:30"),"confirmé");
        r22.setStatutR("annulée");
        rs.update(r22);

        rs.readAll().forEach(System.out::println);
        /////////////AFFICHER PAR ID////////////
        System.out.println(rs.readById(16));







    }
}