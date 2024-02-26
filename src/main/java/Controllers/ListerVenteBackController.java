package Controllers;
import entities.Vente;
import entities.Produit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.Service.ProduitService;
import org.example.Service.VenteService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ListerVenteBackController {

    @FXML
    private Button ButtonAccueil;

    @FXML
    private Button ButtonAjouterP;

    @FXML
    private Button ButtonListeP;

    @FXML
    private Button ButtonListeV;

    @FXML
    private Button ButtonModifierV;

    @FXML
    private TableView<?> TableVente;

    @FXML
    private TableColumn<?, ?> DateV;

    @FXML
    private TableColumn<?, ?> MV;

    @FXML
    private TableColumn<?, ?> QV;

    @FXML
    private TableColumn<Vente, Void> SuppressionV;

    @FXML
    private TableColumn<?, ?> nomUser;

    @FXML
    private TableColumn<?, ?> refPV;
    @FXML
    private TableColumn<Vente, Void> ModificationV;

    @FXML
    public void Home(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DashboardBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    public void ajP(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterProduit.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Dashboard");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

@FXML
    public void listV(ActionEvent actionEvent) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListerVenteBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Vente");

        // Afficher la nouvelle fenêtre
        stage.show();
    }
@FXML
    public void listP(ActionEvent actionEvent)  throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListerProduitBack.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Produit");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    VenteService vs = new VenteService();
    List<Vente> VList;

    public void initialize() throws IOException {
        // Initialiser le ComboBox avec des données
        ObservableList<String> options = FXCollections.observableArrayList(
                "confirmée",
                "annulée"
        );
        ShowVente();
        configureSuppressionColumn();
    }

    private void configureSuppressionColumn() {
        SuppressionV.setCellFactory(param -> new TableCell<>() {
            private final Button boutonSupprimer = new Button("Supprimer");
            {
                boutonSupprimer.setOnAction(event -> {
                    Vente vente = getTableView().getItems().get(getIndex());
                    vs.delete(vente);
                    System.out.println("Supprimer : " + vente.getIdV());
                    TableVente.refresh();
                });
            }

            private void configureModificationColumn() {
                ModificationV.setCellFactory(param -> new TableCell<>() {
                    private final Button boutonModifier = new Button("Modifier");
                    {
                        boutonModifier.setOnAction(event -> {
                            Vente vente = getTableView().getItems().get(getIndex());
                            System.out.println("Modifier : " + vente.getIdV());

                            // Créer une nouvelle fenêtre (Stage)
                            Stage nouvelleFenetre = new Stage();

                            // Créer un FXMLLoader pour charger la nouvelle scène depuis le fichier FXML
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierVente.fxml"));

                            try {
                                // Charger la racine de la nouvelle scène
                                Parent root = loader.load();

                                // Obtenir le contrôleur de la nouvelle scène
                                ModifierVenteController modifierVenteController = loader.getController();

                                // Transmettre l'id de la vente au contrôleur de la nouvelle scène
                                modifierVenteController.setVenteId(vente.getIdV());

                                Scene nouvelleScene = new Scene(root);
                                nouvelleFenetre.setScene(nouvelleScene);

                                // Afficher la nouvelle fenêtre
                                nouvelleFenetre.show();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            // Rafraîchir la TableView si nécessaire
                            TableVente.refresh();
                        });
                    }
                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        setGraphic(empty ? null : boutonModifier);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : boutonSupprimer);
            }
        });
    }

    public void ShowVente() throws IOException {

        VList = vs.readAll();
        List<Vente> filtredVlist= new ArrayList<>();

        refPV.setCellValueFactory(new PropertyValueFactory<>("idP"));
        nomUser.setCellValueFactory(new PropertyValueFactory<>("idU"));
        MV.setCellValueFactory(new PropertyValueFactory<>("MontantV"));
        QV.setCellValueFactory(new PropertyValueFactory<>("QuantitéV"));
        DateV.setCellValueFactory(new PropertyValueFactory<>("DateV"));

        if (TableVente != null && TableVente instanceof TableView) {
            // Cast ticket_tableview to TableView<Ticket> and set its items
            ((TableView<Vente>) TableVente).setItems(FXCollections.observableArrayList(VList));
        }
    }


}
