package tn.Gesport.controllers;

import tn.Gesport.services.dmservices;
import tn.Gesport.models.User;
import tn.Gesport.models.dossiermedical;
import javafx.event.ActionEvent;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class AjoutDmController {
    public TextField PoidsDM;
    public TextField tailleDM;
    public TextField ageDM;
    public ListView<String> listDm;
    private dmservices dmServices = new dmservices();
    private int loggedInUserId;

    public void ModifierDm(ActionEvent event) {
    }

    public void AjoutDm(ActionEvent event) {
        String poids = PoidsDM.getText();
        String taille = tailleDM.getText();
        int age = Integer.parseInt(ageDM.getText());

        // Obtenez l'identifiant de l'utilisateur actuellement connecté
        int idUser = LoginUserControllers.getLoggedInUser().getIdU();

        // Créez un nouveau dossier médical
        dossiermedical dm = new dossiermedical();
        dm.setPoidsDM(poids);
        dm.setTailleDM(taille);
        dm.setAgeDM(age);

        // Obtenez l'utilisateur connecté et définissez son ID dans le dossier médical
        User loggedInUser = LoginUserControllers.getLoggedInUser();
        if (loggedInUser != null) {
            dm.setuse(loggedInUser);
        }

        // Ajouter le dossier médical à la base de données
        dmServices.add(dm);

        // Mettre à jour la liste des dossiers médicaux affichée dans la ListView
        updateListView();
    }

    public void setLoggedInUserId(int userId) {
        this.loggedInUserId = userId;
    }

    public void SupprimerDm(ActionEvent event) {
    }
    private void updateListView() {
        // Effacez d'abord la ListView pour éviter les doublons
        listDm.getItems().clear();

        // Ensuite, récupérez tous les dossiers médicaux depuis la base de données
        // et ajoutez-les à la ListView
        for (dossiermedical dm : dmServices.getAll()) {
            listDm.getItems().add("Poids: " + dm.getPoidsDM() + ", Taille: " + dm.getTailleDM() + ", Age: " + dm.getAgeDM());
        }
    }
}

