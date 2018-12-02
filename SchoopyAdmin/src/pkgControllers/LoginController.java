package pkgControllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Color;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pkgData.Database;
import pkgData.SchoopyAdmin;

/**
 *
 * @author oXCToo
 */
public class LoginController implements Initializable {

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSignin;

    Database db = null;

    @FXML
    public void handleButtonAction(MouseEvent event) {
        try {
            if (event.getSource() == btnSignin) {
                String un = txtUsername.getText();
                String pw = txtPassword.getText();
                if (un.isEmpty() || pw.isEmpty()) {
                    throw new Exception("empty username/password");
                }

                db.login(new SchoopyAdmin(un, pw));

                Stage stage;
                Parent root;
                stage = (Stage) ((Button) (event.getSource())).getScene().getWindow();
                root = FXMLLoader.load(getClass().getResource("/pkgViews/MainMenu.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            }

        } catch (Exception ex) {
            lblErrors.setText("error:" + ex.getMessage());
            lblErrors.setStyle("-fx-text-fill: red;");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db = Database.newInstance();
        } catch (Exception ex) {
            System.out.println("error:" + ex.getMessage());
        }
    }

}
