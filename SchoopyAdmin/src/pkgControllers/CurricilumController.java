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
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextArea;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import pkgData.ClassHour;
import pkgData.Database;
import pkgData.Lesson;
import pkgData.Room;
import pkgData.Subject;
import pkgData.Teacher;
import pkgData.TeacherSpecialization;
import pkgData.WeekDay;

/**
 * FXML Controller class
 *
 * @author schueler
 */
public class CurricilumController implements Initializable {

    @FXML
    private TableView<ClassHour> tableLessons;

    @FXML
    private JFXComboBox<Room> cbRooms;

    @FXML
    private StackPane mySP;

    Database db = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db = Database.newInstance();
            cbRooms.setItems(FXCollections.observableArrayList(db.getAllSchoolRooms())); //schulräume nur
        } catch (Exception ex) {
            Logger.getLogger(CurricilumController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    void onSelected(ActionEvent event) {
        try {
            if (event.getSource().equals(cbRooms)) {
                Room selectedItem = cbRooms.getSelectionModel().getSelectedItem();
                if (selectedItem == null) {
                    throw new Exception("please select room");
                }

                showLessons(selectedItem.getRoomNr());
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

    private void showLessons(String roomNr) throws Exception {
        try {
            ObservableList<ClassHour> classHours = db.convertToClassHour(db.getAllLessonsByRoomNr(roomNr));

            ObservableList<TableColumn<ClassHour, ?>> columns = tableLessons.getColumns();
            for (int i = 0; i < columns.size(); i++) {
                int day = i;
                TableColumn<ClassHour, Lesson> curC = (TableColumn<ClassHour, Lesson>) columns.get(i);
                curC.setSortable(false);
                curC.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getDayLessons()[day]));
                curC.setCellFactory((TableColumn<ClassHour, Lesson> param) -> new TableCell<ClassHour, Lesson>() {
                    @Override
                    protected void updateItem(Lesson item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(null);
                        setGraphic(null);
                        if (!empty) {
                            if (item != null) {
                                JFXButton btn = new JFXButton(item.toString());
                                btn.setOnAction(e -> {
                                    try {
                                        showDeleteDialog(item);
                                    } catch (Exception ex) {
                                        showResultDialog("An error has occured:", ex.getMessage());
                                    }
                                });
                                setGraphic(new StackPane(btn));
                            } else {
                                JFXButton btn = new JFXButton();
                                FontAwesomeIconView img = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
                                btn.setGraphic(img);
                                btn.setOnAction(e -> {
                                    try {

                                        showAddDialog(day, ((ClassHour) getTableRow().getItem()).getHour());
                                    } catch (Exception ex) {
                                        showResultDialog("An error has occured:", ex.getMessage());
                                    }
                                });
                                setGraphic(new StackPane(btn));
                            }
                        }
                    }

                });
                tableLessons.setItems(classHours);

            }

        } catch (Exception ex) {
            showResultDialog("An error has occured:", ex.getMessage());
        }
    }

    private void showDeleteDialog(Lesson curLesson) throws Exception {
        Room schoolRoom = cbRooms.getSelectionModel().getSelectedItem();

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Current Lesson:" + schoolRoom.getRoomDescription()));
        JFXComboBox<Room> cbTeachingRoom = new JFXComboBox(FXCollections.observableArrayList(db.getAllTeachingRooms(schoolRoom.getRoomNr())));
        JFXComboBox<TeacherSpecialization> cbTS = new JFXComboBox(FXCollections.observableArrayList(db.getAllTS()));
        cbTeachingRoom.getSelectionModel().select(schoolRoom);
        cbTS.getSelectionModel().select(curLesson.getTeachingInfo());
        JFXButton btnUpdate = new JFXButton("UPDATE");
        JFXButton btnDelete = new JFXButton("DELETE");
        JFXButton btnClose = new JFXButton("CANCEL");

        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());

        btnUpdate.setOnAction((ActionEvent event) -> {
            try {
                Room selectedTRoom = cbTeachingRoom.getSelectionModel().getSelectedItem();
                TeacherSpecialization selectedTS = cbTS.getSelectionModel().getSelectedItem();
                if (selectedTRoom == null || selectedTS == null) {
                    throw new Exception("please select teaching room and ts");
                }
                db.updateLesson(new Lesson(schoolRoom, selectedTRoom, selectedTS, curLesson.getWeekDay(), curLesson.getSchoolHour()));

                showLessons(schoolRoom.getRoomNr());
                dialog.close();
                showResultDialog("Success", "Lesson successfully updated!");
            } catch (Exception ex) {
                showResultDialog("An error has occured:", ex.getMessage());
            }
        });

        btnDelete.setOnAction((ActionEvent event) -> {
            try {
                db.deleteLesson(curLesson);
                showLessons(schoolRoom.getRoomNr());
                dialog.close();
                showResultDialog("Success", "Lesson successfully deleted!");
            } catch (Exception ex) {
                showResultDialog("An error has occured:", ex.getMessage());
            }
        });

        btnClose.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(cbTeachingRoom, cbTS, btnClose, btnUpdate, btnDelete);
        dialog.show();

    }

    private void showAddDialog(int day, int hour) throws Exception {
        Room schoolRoom = cbRooms.getSelectionModel().getSelectedItem();

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Add new Lesson for " + schoolRoom.getRoomDescription()));
        JFXComboBox<Room> cbTeachingRoom = new JFXComboBox(FXCollections.observableArrayList(db.getAllTeachingRooms(schoolRoom.getRoomNr())));
        JFXComboBox<TeacherSpecialization> cbTS = new JFXComboBox(FXCollections.observableArrayList(db.getAllTS()));

        JFXButton btnAdd = new JFXButton("ADD");
        JFXButton btnClose = new JFXButton("CANCEL");

        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());

        btnAdd.setOnAction((ActionEvent event) -> {
            try {
                Room selectedTRoom = cbTeachingRoom.getSelectionModel().getSelectedItem();
                TeacherSpecialization selectedTS = cbTS.getSelectionModel().getSelectedItem();
                if (selectedTRoom == null || selectedTS == null) {
                    throw new Exception("please select teaching room and ts");
                }
                db.addLesson(new Lesson(schoolRoom, selectedTRoom, selectedTS, WeekDay.values()[day], hour + 1));

                showLessons(schoolRoom.getRoomNr());
                dialog.close();
                showResultDialog("Success", "Lesson successfully added!");
            } catch (Exception ex) {
                showResultDialog("An error has occured:", ex.getMessage());
            }
        });

        btnClose.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(cbTeachingRoom, cbTS, btnClose, btnAdd);
        dialog.show();
    }

}
