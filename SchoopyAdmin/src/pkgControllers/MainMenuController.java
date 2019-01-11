/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author schueler
 */
public class MainMenuController implements Initializable {

    @FXML
    private Pane pane_main;

    @FXML
    private JFXButton bttn_Teachers;
    private JFXButton lastPressedBttn;
    final private double noClick_Opacity = 0.45;
    final private double onClick_Opacity = 1.00;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            bttn_Teachers.setOpacity(onClick_Opacity);
            lastPressedBttn = bttn_Teachers;
            pane_main.getChildren().clear();
            pane_main.getChildren().add(FXMLLoader.load(MainMenuController.this.getClass().getResource("/pkgViews/Teacher.fxml")));
        } catch (Exception ex) {
            System.out.println("error:" + ex.getMessage());
        }

    }

    @FXML
    void onButtonClick(ActionEvent event) {
        JFXButton clickedBttn = (JFXButton) event.getSource();
        if (!clickedBttn.equals(lastPressedBttn)) {
            try {
                lastPressedBttn.setOpacity(noClick_Opacity);
                lastPressedBttn = clickedBttn;
                clickedBttn.setOpacity(onClick_Opacity);
                pane_main.getChildren().clear();
                pane_main.getChildren().add(getSelectedPane(clickedBttn));
            } catch (Exception ex) {
                System.out.println("error:" + ex.getMessage());
            }
        }
    }

    private Pane getSelectedPane(JFXButton selBttn) throws Exception {
        Pane newPane = null;
        String bttnId = selBttn.getId();
        if (bttnId.equals("bttn_Teachers")) {
            newPane = FXMLLoader.load(MainMenuController.this.getClass().getResource("/pkgViews/Teacher.fxml"));
        } else if (bttnId.equals("bttn_Subjects")) {
            newPane = FXMLLoader.load(MainMenuController.this.getClass().getResource("/pkgViews/Subject.fxml"));
        } else if (bttnId.equals("bttn_Rooms")) {
            newPane = FXMLLoader.load(MainMenuController.this.getClass().getResource("/pkgViews/Room.fxml"));
        } else if (bttnId.equals("bttn_Curricilum")) {
            newPane = FXMLLoader.load(MainMenuController.this.getClass().getResource("/pkgViews/Curricilum.fxml"));
        } else if (bttnId.equals("bttn_Analysis")) {
            newPane = FXMLLoader.load(MainMenuController.this.getClass().getResource("/pkgViews/Analysis.fxml"));
        }

        return newPane;
    }
}
