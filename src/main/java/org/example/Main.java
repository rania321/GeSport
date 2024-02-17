package org.example;
import entities.Equipe;
import entities.Tournoi;
import entities.User;
import service.EquipeService;
import service.TournoiService;
import service.UserService;
import utils.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {

//*************************TOURNOI*****************************
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {

            // Convertir les chaînes de caractères en objets Date
            Date dateDebut = sdf.parse("06/05/2024");
            Date dateFin = sdf.parse("09/10/2024");

            // Créer un objet Tournoi avec les dates
            Tournoi t1 = new Tournoi("T", dateDebut, dateFin, "tttt", "terminé");

            // Créer un objet TournoiService
            TournoiService ts = new TournoiService();
//---------------------------------------------------------------------------------------------------------
            // Ajouter  tournoi******************************
            //ts.add(t1);
            //ts.addPst(t1);
//-----------------------------------------------------------------------------------------------------------
            //afficher*****************************************
            //ts.readAll().forEach(System.out::println);
//------------------------------------------------------------------------------------------------------
            // afficher tournoi by ID********************************

           /* Tournoi tournoiLu = ts.readById(1);
            // Vérifier si le tournoi a été trouvé
            if (tournoiLu != null) {
                System.out.println("Tournoi trouvé : " + tournoiLu);
            } else {
                System.out.println("Aucun tournoi trouvé avec cet ID.");
            }*/

//-----------------------------------------------------------------------------------------------------------
            //supprimer tournoi*******************************
           /* int idTournoiASupprimer = 1;
            Tournoi tournoiASupprimer = ts.readById(idTournoiASupprimer);
            if (tournoiASupprimer != null) {
                ts.delete(tournoiASupprimer);
                System.out.println("Tournoi supprimé avec succès!");
            } else {
                System.out.println("Aucun tournoi trouvé avec l'identifiant spécifié.");
            }*/

//-----------------------------------------------------------------------------------------------------
            //update tournoi******************************************
          /*  Tournoi tournoiAMettreAJour = new Tournoi();
            tournoiAMettreAJour.setIdT(3); // ID du tournoi à mettre à jour
            tournoiAMettreAJour.setNomT("NouveauNom");
            tournoiAMettreAJour.setDateDebutT(new java.sql.Date(2023, 4, 2));
            tournoiAMettreAJour.setDateFinT(new java.sql.Date(2025, 11, 8));
            tournoiAMettreAJour.setDescriT("NouvelleDescription");
            tournoiAMettreAJour.setStatutT("NouveauStatut");

            ts.update(tournoiAMettreAJour);

            System.out.println("Tournoi mis à jour avec succès!");

*/

        } catch (ParseException e) {
            e.printStackTrace();
        }


//*****************************EQUIPE********************************


        EquipeService E = new EquipeService();

        EquipeService Es = new EquipeService();
        TournoiService Ts = new TournoiService();
        User u=new User();
        UserService us= new UserService();
//------------------------------------------------------------------------------------



        //ajouter equipe***********************
        /*

        // Récupérez l'ID du tournoi existant
        int idTournoi = 2;
        int idUser = 3;

        // Récupérez l'objet  correspondant à l'ID
        Tournoi tournoi = Ts.readById(idTournoi);
        User user= us.readById(idUser);

        // Créez une nouvelle équipe et associez-lui le tournoi choisi
        Equipe nouvelleEquipe = new Equipe("ALPHAS", tournoi,user, "Statut de l'équipe");

        // Ajoutez la nouvelle équipe avec le tournoi existant
        Es.add(nouvelleEquipe);

        System.out.println("Nouvelle équipe ajoutée avec succès !");

*/
//____________________________________________________________________________________________________________________
        //SUPPRIMER equipe **********************************************************************

       /* Equipe equipeToDelete = new Equipe();
        equipeToDelete.setIdE((12)); // Supposons que vous souhaitez supprimer l'équipe avec l'ID 1
        E.delete(equipeToDelete);
        System.out.println("Équipe supprimée avec succès !");
*/
        //_____________________________________________________________________________________________________________

        //UPDATE equipe ***********************************

        // Récupérer l'équipe à mettre à jour
       /* Equipe equipeToUpdate = E.readById(6);

        if (equipeToUpdate != null) {
            // Modifier les attributs de l'équipe, y compris l'identifiant du tournoi
            equipeToUpdate.setNomE("mmmmmm");
            equipeToUpdate.getTournoi().setIdT(4); // Modification de l'id du tournoi
            equipeToUpdate.getUser().setIdU(1);//Modification de l'id user
            equipeToUpdate.setStatutE("sssssss");

            // Mettre à jour l'équipe dans la base de données
            E.update(equipeToUpdate);

            System.out.println("L'équipe a été mise à jour avec succès !");
        } else {
            System.out.println("L'équipe avec l'ID spécifié n'a pas été trouvée.");
        }*/






//-----------------------------------------------------------------------------------------------

        //afficher equipe ******************************************



      // Lire toutes les équipes
        List<Equipe> equipes = E.readAll();

        // Afficher les équipes
        for (Equipe equipe : equipes) {
            System.out.println(equipe);

        }


//---------------------------------------------------------------------------------------

        //afficher equipe  by ID **********************************


        // ID de l'équipe à lire
      /* int idEquipe = 9;

        Equipe equipe = E.readById(idEquipe);

        // Vérifier si l'équipe a été trouvée
        if (equipe != null) {
            System.out.println("Équipe trouvée : " + equipe);
        } else {
            System.out.println("Aucune équipe trouvée avec cet ID.");
        }
          */




    }
}

