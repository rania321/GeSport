package tn.Gesport.test;

import tn.Gesport.services.UserService;
import tn.Gesport.services.dmservices;
import tn.Gesport.models.User;
import tn.Gesport.models.dossiermedical;
import tn.Gesport.models.role;

public class MainUser {

    public static void main(String[] args){

        User u1=new User("Ghzel","dawser","dawser.ghzel@esprit.tn","123456");
        u1.setRoleU(role.utulisateur);

        User u2=new User("Ghzel","slim","slim.ghzel@esprit.tn","1234567");
        User u3=new User("Ghzel","lamis","lamis.ghzel@esprit.tn","123456",role.visiteur);
        UserService us=new UserService();
        ///////////////addddddddddd//////////////////

        //us.add(u2);
        ////////////reaaaaaaaaaaaaad////////////////////
        //us.readall().forEach(System.out::println);
        ////////////////dellllllletttte/////////
        //int idrsupprimer=97;
       //us.deleteById(idrsupprimer);
        //***********Upppdate**********
        User updatedUser = new User(5,"gharbi", "slim", "slim.gharb@gmail.com", "789456", role.utulisateur);


        // Creating an instance of userservice
        UserService us2 = new UserService();

        // Executing the update method
        us2.update(updatedUser);
        us2.getAll().forEach(System.out::println);
       /* userservice us3 = new userservice();

        */
        User u9=us.readById(5);


        /////////////adddddddddddddd////////////
       /* dossiermedical d1 = new dossiermedical(u9, "aaa", "aaaa", 12);
        dmservices dm=new dmservices();
        dm.add(d1);*/
        ////////////delllllettte/////////
       /*int iddr=3;
        dm.deleteById(iddr);*/
        ///////updae dossier////////////
        /*dossiermedical update=new dossiermedical(21,u9,"bbbbccb","cccccc",13);


        dmservices d2=new dmservices();
        d2.update(update);

        //////read alll////////
        d2.readall().forEach(System.out::println);*/





    }
}