/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author schueler
 */
public class TeacherController implements Initializable {

    @FXML
    private JFXTreeTableView<?> tableTeachers;

    @FXML
    private JFXButton btnAddTeacher;

    @FXML
    void handleButtonAction(ActionEvent event) {
        try{
            
        }catch(Exception ex){
            
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
