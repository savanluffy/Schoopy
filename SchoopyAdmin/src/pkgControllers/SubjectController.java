/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgControllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import pkgData.Database;
import pkgData.Subject;
import pkgData.Teacher;
import pkgData.TeacherSpecialization;
import pkgMisc.EMailManager;
import pkgMisc.StringConverter;

/**
 * FXML Controller class
 *
 * @author schueler
 */
public class SubjectController implements Initializable {

    @FXML
    private StackPane mySP;

    @FXML
    private JFXListView<Subject> listSubjects;

    @FXML
    private JFXButton btnAddSubject;

    @FXML
    private JFXButton btnDeleteSubject;

    @FXML
    private JFXListView<Teacher> listTeachers;

    @FXML
    private TableView<TeacherSpecialization> tableTS;

    @FXML
    private TableColumn<TeacherSpecialization, Teacher> columnTeacher;

    @FXML
    private TableColumn<TeacherSpecialization, Teacher> columnSubject;

    @FXML
    private JFXTextField txtFilterSubjects;

    @FXML
    private JFXTextField txtFilterTeachers;

    @FXML
    private JFXButton btnAddTS;

    @FXML
    private JFXButton btnDeleteTS;

    @FXML
    private JFXButton btnUpdateSubject;

    @FXML
    private JFXButton btnSeeTeacherTS;

    @FXML
    private JFXButton btnSeeSubjectTS;

    @FXML
    private JFXButton btnSeeAllTS;

    Database db = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db = Database.newInstance();
            columnSubject.setCellValueFactory(new PropertyValueFactory<>("subject"));
            columnTeacher.setCellValueFactory(new PropertyValueFactory<>("teacher"));
            listSubjects.getItems().setAll(db.getAllSubjects());
            listTeachers.getItems().setAll(db.getAllTeachers());
            tableTS.getItems().setAll(db.getAllTS());
        } catch (Exception ex) {
            showResultDialog("An error has occured:", ex.getMessage());

        }
    }

    @FXML
    void onBtnClick(ActionEvent event) {
        try {
            if (event.getSource().equals(btnAddSubject)) {
                addSubject();
            } else if (event.getSource().equals(btnUpdateSubject)) {
                updateSubject();
            } else if (event.getSource().equals(btnDeleteSubject)) {
                deleteSubject();
            } else if (event.getSource().equals(btnAddTS)) {
                addTS();
            } else if (event.getSource().equals(btnDeleteTS)) {
                deleteTS();
            } else if (event.getSource().equals(btnSeeAllTS)) {
                tableTS.getItems().setAll(db.getAllTS());
            } else if (event.getSource().equals(btnSeeSubjectTS)) {
                tableTS.getItems().setAll(db.getTSBySubject(listSubjects.getSelectionModel().getSelectedItem()));
            } else if (event.getSource().equals(btnSeeTeacherTS)) {
                tableTS.getItems().setAll(db.getTSByTeacher(listTeachers.getSelectionModel().getSelectedItem()));
            }

        } catch (Exception ex) {
            showResultDialog("An error has occured:", ex.getMessage());
        }
    }

    @FXML
    void onItemSelected(MouseEvent event) {
        try {
            if (event.getSource().equals(listSubjects)) {
                Subject selectedItem = listSubjects.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    disableSubjectButtons();
                } else {
                    enableSubjectButtons();
                }
            } else if (event.getSource().equals(listTeachers)) {
                Teacher selectedItem = listTeachers.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    disableTeacherButtons();

                } else {
                    if (listSubjects.getSelectionModel().getSelectedItem() != null) {
                        btnAddTS.setDisable(false);
                    }
                    btnSeeTeacherTS.setDisable(false);
                }

            } else if (event.getSource().equals(tableTS)) {
                TeacherSpecialization selectedItem = tableTS.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    btnDeleteTS.setDisable(true);
                } else {
                    btnDeleteTS.setDisable(false);

                }
            }

        } catch (Exception ex) {
            showResultDialog("An error has occured:", ex.getMessage());
        }
    }

    @FXML
    void onKeyPressed(KeyEvent event) {
        try {
            if (event.getCode() == KeyCode.ENTER) {
                if (event.getSource().equals(txtFilterSubjects)) {
                    filterSubjects(txtFilterSubjects.getText());
                } else if (event.getSource().equals(txtFilterTeachers)) {
                    filterTeachers(txtFilterTeachers.getText());
                }
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

    private void filterSubjects(String text) throws Exception {
        ArrayList<Subject> filteredSubjects;
        if (text.isEmpty()) {
            filteredSubjects = db.getAllSubjects();
        } else {
            filteredSubjects = db.filterSubjects(text);
        }
        listSubjects.getItems().setAll(filteredSubjects);
        disableSubjectButtons();
    }

    private void filterTeachers(String filterVal) throws Exception {
        ArrayList<Teacher> filteredTeachers;
        if (filterVal.isEmpty()) {
            filteredTeachers = db.getAllTeachers();
        } else {
            filteredTeachers = db.filterTeachers(filterVal);
        }
        listTeachers.getItems().setAll(filteredTeachers);
        disableTeacherButtons();
    }

    private void addSubject() throws Exception {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Add new Subject"));
        content.setBody(new Text("enter data:"));
        JFXTextField sName = new JFXTextField(), sShortCut = new JFXTextField();

        sName.setPromptText("subject name");
        sShortCut.setPromptText("subject shortcut");

        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());
        JFXButton btnAdd = new JFXButton("ADD");
        JFXButton btnClose = new JFXButton("CANCEL");

        btnAdd.setOnAction((ActionEvent event) -> {
            try {
                String sNameText = sName.getText(), sShortCutText = sShortCut.getText();
                if (sNameText.isEmpty() || sShortCutText.isEmpty()) {
                    throw new Exception("data can't be empty");
                }
                Subject newSubject = new Subject(sNameText, sShortCutText);
                db.addSubject(newSubject);
                listSubjects.getItems().setAll(db.getAllSubjects());
                disableSubjectButtons();

                dialog.close();
                showResultDialog("Success", "Subject successfully added!");
            } catch (Exception ex) {
                showResultDialog("An error has occured:", ex.getMessage());
            }
        });

        btnClose.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(sName, sShortCut, btnClose, btnAdd);
        dialog.show();
    }

    private void updateSubject() throws Exception {
        Subject selected = this.listSubjects.getSelectionModel().getSelectedItem();
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Update Subject"));
        content.setBody(new Text("enter data:"));
        JFXTextField sName = new JFXTextField(selected.getSubjectName()), sShortCut = new JFXTextField(selected.getSubjectShortcut());

        sName.setPromptText("subject name");
        sShortCut.setPromptText("subject shortcut");

        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());
        JFXButton btnAdd = new JFXButton("UPDATE");
        JFXButton btnClose = new JFXButton("CANCEL");

        btnAdd.setOnAction((ActionEvent event) -> {
            try {
                String sNameText = sName.getText(), sShortCutText = sShortCut.getText();
                if (sNameText.isEmpty() || sShortCutText.isEmpty()) {
                    throw new Exception("data can't be empty");
                }
                Subject updateSubject = new Subject(selected.getSubjectId(), sNameText, sShortCutText);
                db.updateSubject(updateSubject);
                listSubjects.getItems().setAll(db.getAllSubjects());
                tableTS.getItems().setAll(db.getAllTS());
                disableSubjectButtons();
                dialog.close();
                showResultDialog("Success", "Subject successfully updated!");
            } catch (Exception ex) {
                showResultDialog("An error has occured:", ex.getMessage());
            }
        });

        btnClose.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(sName, sShortCut, btnClose, btnAdd);
        dialog.show();
    }

    private void deleteSubject() throws Exception {
        Subject selected = this.listSubjects.getSelectionModel().getSelectedItem();

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Delete " + selected.getSubjectName() + "?"));

        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());
        JFXButton btnUpdate = new JFXButton("DELETE");
        JFXButton btnClose = new JFXButton("CANCEL");

        btnUpdate.setOnAction((ActionEvent event) -> {
            try {
                db.deleteSubject(selected);
                listSubjects.getItems().setAll(db.getAllSubjects());
                tableTS.getItems().setAll(db.getAllTS()); //if you delete subject you need to refresh the ts table
                disableSubjectButtons();
                dialog.close();
                showResultDialog("Success", "Subject successfully deleted!");
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

    private void addTS() throws Exception {
        Teacher t = listTeachers.getSelectionModel().getSelectedItem();
        Subject s = listSubjects.getSelectionModel().getSelectedItem();
        TeacherSpecialization ts = new TeacherSpecialization(t, s);
        db.addTeacherSpecialization(ts);
        tableTS.getItems().setAll(db.getAllTS());
    }

    private void deleteTS() throws Exception {
        TeacherSpecialization ts = tableTS.getSelectionModel().getSelectedItem();
        db.deleteTeacherSpecialization(ts);
        tableTS.getItems().setAll(db.getAllTS());
    }

    private void disableSubjectButtons() {
        btnDeleteSubject.setDisable(true);
        btnUpdateSubject.setDisable(true);
        btnSeeSubjectTS.setDisable(true);
        btnAddTS.setDisable(true);
    }

    private void enableSubjectButtons() {
        btnDeleteSubject.setDisable(false);
        btnUpdateSubject.setDisable(false);
        btnSeeSubjectTS.setDisable(false);
        if (listTeachers.getSelectionModel().getSelectedItem() != null) {
            btnAddTS.setDisable(false);
        }
    }

    private void disableTeacherButtons() {
        btnAddTS.setDisable(true);
        btnSeeTeacherTS.setDisable(true);
    }

}
