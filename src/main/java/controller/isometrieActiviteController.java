package controller;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Node;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.stage.Stage;

        import java.io.IOException;

public class isometrieActiviteController {

    @FXML
    private ImageView equitation;

    @FXML
    private ImageView minigolf;

    @FXML
    private ImageView natation;

    @FXML
    private ImageView paddle;

    @FXML
    private ImageView tiralarc;

    public void initialize() {
        equitation.setVisible(false);
        natation.setVisible(false);
        tiralarc.setVisible(false);
        minigolf.setVisible(false);
        paddle.setVisible(false);
    }

    @FXML
    void btnEquitationEntered(MouseEvent event) {
        equitation.setVisible(true);
    }

    @FXML
    void btnEquitationExited(MouseEvent event) {
        equitation.setVisible(false);
    }

    @FXML
    void btnMinigolfEntered(MouseEvent event) {
        minigolf.setVisible(true);
    }

    @FXML
    void btnMinigolfExited(MouseEvent event) {
        minigolf.setVisible(false);
    }

    @FXML
    void btnNatationEntered(MouseEvent event) {
        natation.setVisible(true);
    }

    @FXML
    void btnNatationExited(MouseEvent event) {
        natation.setVisible(false);
    }

    @FXML
    void btnPaddleEntered(MouseEvent event) {
        paddle.setVisible(true);
    }

    @FXML
    void btnPaddleExited(MouseEvent event) {
        paddle.setVisible(false);
    }

    @FXML
    void btnTiralarcEntered(MouseEvent event) {
        tiralarc.setVisible(true);
    }

    @FXML
    void btnTiralarcExited(MouseEvent event) {
        tiralarc.setVisible(false);
    }

    @FXML
    void accueil(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/dashboardFront.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Activités");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void activite(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/show_activite.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène
        Scene scene = new Scene(root);

        // Configurer la nouvelle scène dans une nouvelle fenêtre
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.setTitle("Activités");

        // Afficher la nouvelle fenêtre
        stage.show();
    }

    @FXML
    void compte(ActionEvent event) {

    }

    @FXML
    void reclamation(ActionEvent event) {

    }

    @FXML
    void restaurant(ActionEvent event) {

    }

    @FXML
    void tournois(ActionEvent event) {

    }

}
