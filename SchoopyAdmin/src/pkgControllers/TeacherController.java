/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import pkgData.Database;
import pkgData.Teacher;
import pkgMisc.EMailManager;
import pkgMisc.StringConverter;

/**
 * FXML Controller class
 *
 * @author schueler
 */
public class TeacherController implements Initializable {

    @FXML
    private StackPane mySP;

    @FXML
    private TableView<Teacher> tableTeachers;

    @FXML
    private TableColumn<Teacher, String> tc_firstName;

    @FXML
    private TableColumn<Teacher, String> tc_lastName;

    @FXML
    private TableColumn<Teacher, String> tc_username;

    @FXML
    private TableColumn<Teacher, String> tc_schoolemail;

    @FXML
    private JFXButton btnAddTeacher;

    @FXML
    private JFXButton btnUpdateTeacher;

    @FXML
    private JFXButton btnDeleteTeacher;

    @FXML
    private JFXTextField txtFilter;

    Database db = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db = Database.newInstance();
            tc_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tc_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tc_username.setCellValueFactory(new PropertyValueFactory<>("username"));
            tc_schoolemail.setCellValueFactory(new PropertyValueFactory<>("schoolemail"));
            tableTeachers.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    this.btnUpdateTeacher.setDisable(false);
                    this.btnDeleteTeacher.setDisable(false);
                } else {
                    this.btnUpdateTeacher.setDisable(true);
                    this.btnDeleteTeacher.setDisable(true);
                }
            });
            this.tableTeachers.getItems().setAll(db.getAllTeachers());
        } catch (Exception ex) {
            showResultDialog("An error has occured:", ex.getMessage());
        }
    }

    @FXML
    void onPress(KeyEvent event) {
        try {
            if (event.getCode() == KeyCode.ENTER && event.getSource().equals(txtFilter)) {
                filterTeachers();
            }
        } catch (Exception ex) {
            showResultDialog("An error has occured:", ex.getMessage());
        }
    }

    @FXML
    void onBtnClick(ActionEvent event) {
        try {
            if (event.getSource().equals(btnAddTeacher)) {
                addTeacher();
            } else if (event.getSource().equals(btnUpdateTeacher)) {
                updateTeacher();
            }
            if (event.getSource().equals(btnDeleteTeacher)) {
                deleteTeacher();
            }
        } catch (Exception ex) {
            showResultDialog("An error has occured:", ex.getMessage());
        }
    }

    public void showResultDialog(String header, String message) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(header));
        content.setBody(new Text(message));
        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());
        JFXButton button = new JFXButton("OK");
        button.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(button);
        dialog.show();
    }

    private void filterTeachers() throws Exception {
        ArrayList<Teacher> filteredTeachers;
        String filterVal = txtFilter.getText();
        if (filterVal.isEmpty()) {
            filteredTeachers = db.getAllTeachers();
        } else {
            filteredTeachers = db.filterTeachers(filterVal);
        }
        tableTeachers.getItems().setAll(filteredTeachers);

    }

    private void addTeacher() {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Add new Teacher"));
        content.setBody(new Text("enter data:"));
        JFXTextField fn = new JFXTextField(), ln = new JFXTextField(), semail = new JFXTextField(), un = new JFXTextField();

        fn.setPromptText("first name");
        ln.setPromptText("last name");
        semail.setPromptText("school email");
        un.setPromptText("username");

        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());
        JFXButton btnAdd = new JFXButton("ADD");
        JFXButton btnClose = new JFXButton("CANCEL");

        btnAdd.setOnAction((ActionEvent event) -> {
            try {
                String fnT = fn.getText(), lnT = ln.getText(), semailT = semail.getText(), unT = un.getText();
                if (fnT.isEmpty() || lnT.isEmpty() || semailT.isEmpty() || unT.isEmpty()) {
                    throw new Exception("data can't be empty");
                }
                if (!EMailManager.crunchifyEmailValidator(semailT)) {
                    throw new Exception("email pattern wrong!");
                }

                Teacher addTeacher = new Teacher(fnT, lnT, semailT, unT, StringConverter.generatePW());
                db.addTeacher(addTeacher);
                EMailManager.sendEmailToTeacher(addTeacher);
                tableTeachers.getItems().setAll(db.getAllTeachers());
                dialog.close();
                showResultDialog("Success", "Teacher successfully added and an email has been send!");
            } catch (Exception ex) {
                showResultDialog("An error has occured:", ex.getMessage());
            }
        });

        btnClose.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(fn, ln, semail, un, btnClose, btnAdd);
        dialog.show();
    }

    private void updateTeacher() throws Exception {
        Teacher t = tableTeachers.getSelectionModel().getSelectedItem();
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Update Teacher"));
        content.setBody(new Text("enter data:"));
        JFXTextField fn = new JFXTextField(t.getFirstName()), ln = new JFXTextField(t.getLastName()), semail = new JFXTextField(t.getSchoolemail()), un = new JFXTextField(t.getUsername());
        fn.setPromptText("first name");
        ln.setPromptText("last name");
        semail.setPromptText("school email");
        un.setPromptText("username");
        un.setEditable(false);
        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());
        JFXButton btnUpdate = new JFXButton("UPDATE");
        JFXButton btnClose = new JFXButton("CANCEL");

        btnUpdate.setOnAction((ActionEvent event) -> {
            try {
                String fnT = fn.getText(), lnT = ln.getText(), semailT = semail.getText(), unT = un.getText();

                if (fnT.isEmpty() || lnT.isEmpty() || semailT.isEmpty() || unT.isEmpty()) {
                    throw new Exception("data can't be empty");
                }
                if (!EMailManager.crunchifyEmailValidator(semailT)) {
                    throw new Exception("email pattern wrong!");
                }

                Teacher updateTeacher = new Teacher(fnT, lnT, semailT, unT, t.getPassword());
                db.updateTeacher(updateTeacher);
                tableTeachers.getItems().setAll(db.getAllTeachers());
                dialog.close();
                showResultDialog("Success", "Teacher successfully updated!");
            } catch (Exception ex) {
                showResultDialog("An error has occured:", ex.getMessage());
            }
        });

        btnClose.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(fn, ln, semail, un, btnClose, btnUpdate);
        dialog.show();
    }

    private void deleteTeacher() throws Exception {
        Teacher t = tableTeachers.getSelectionModel().getSelectedItem();

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Delete " + t.getLastName() + "?"));

        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());
        JFXButton btnUpdate = new JFXButton("DELETE");
        JFXButton btnClose = new JFXButton("CANCEL");

        btnUpdate.setOnAction((ActionEvent event) -> {
            try {
                db.deleteTeacher(t.getUsername());
                tableTeachers.getItems().setAll(db.getAllTeachers());
                dialog.close();
                showResultDialog("Success", "Teacher successfully deleted!");
            } catch (Exception ex) {
                showResultDialog("An error has occured:", ex.getMessage());
            }
        });

        btnClose.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(btnClose, btnUpdate);
        dialog.show();
    }
}
