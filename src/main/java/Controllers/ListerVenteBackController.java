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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.Service.ProduitService;
import org.example.Service.VenteService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class ListerVenteBackController {

    @FXML
    private ComboBox<String> CBStatuteP;

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
    private TableColumn<?, ?> SuppressionV;

    @FXML
    private TableColumn<?, ?> nomUser;

    @FXML
    private TableColumn<?, ?> refPV;
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
        CBStatuteP.setItems(options);
        ShowVente();
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
}
