package org.example;

import Services.userservice;
import Services.dmservices;
import Utils.DataSource;
import entities.User;
import entities.dossiermedical;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args){

        User u1=new User("Ghzel","dawser","dawser.ghzel@esprit.tn","123456","admin");
        userservice us=new userservice();
        ///////////////addddddddddd//////////////////
        //us.add(u1);
        ////////////reaaaaaaaaaaaaad////////////////////
        //us.readall().forEach(System.out::println);
        ////////////////dellllllletttte/////////
        //int idrsupprimer=1;
       //us.delete(idrsupprimer);
        //***********Upppdate**********
        User updatedUser = new User("gharbi", "slim", "slim.gharb@gmail.com", "789456", "user");

        // ID of the user you want to update
        int userIdToUpdate = 2; // Assuming the ID of the user you want to update is 1

        // Creating an instance of userservice
        //userservice us2 = new userservice();

        // Executing the update method
       // us2.update(updatedUser, userIdToUpdate);
       /* userservice us3 = new userservice();

        // Reading user information by ID
        int userIdToRead = 3; // Specify the ID of the user you want to retrieve
        User user = us.readByid(userIdToRead);

        if (user != null) {
            // If user information is retrieved successfully, print it
            System.out.println("User information:");
            System.out.println("ID: " + userIdToRead);
            System.out.println("NomU: " + user.getNomU());
            System.out.println("PrenomU: " + user.getPrenomU());
            System.out.println("EmailU: " + user.getEmailU());
            System.out.println("mdpU: " + user.getMdpU());
            System.out.println("RoleU: " + user.getRoleU());

        } else {
            // If user information is not found
            System.out.println("User not found with ID: " + userIdToRead);
        }*/
        /////////////adddddddddddddd////////////
        dossiermedical d1=new dossiermedical(2,"aaa","aaaa",12);
        dmservices dm=new dmservices();
        //dm.add(d1);
        ////////////delllllettte/////////
       /* int iddr=2;
        dm.delete(iddr);*/
        ///////updae dossier////////////
        /*dossiermedical update=new dossiermedical(3,"bbbbb","cccccc",13);
        int dossierid=3;
        dmservices d2=new dmservices();
        d2.update(update,dossierid);*/
        //////read alll////////
        dm.readall().forEach(System.out::println);



    }
}