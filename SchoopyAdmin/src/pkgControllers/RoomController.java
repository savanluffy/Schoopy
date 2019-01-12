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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import pkgData.Database;
import pkgData.Department;
import pkgData.Room;
import pkgData.TeacherSpecialization;

/**
 * FXML Controller class
 *
 * @author schueler
 */
public class RoomController implements Initializable {

    @FXML
    private JFXComboBox<Room> cbRooms;

    @FXML
    private JFXButton btnAddRoom;

    @FXML
    private JFXButton btnEditRoom;

    @FXML
    private StackPane mySP;

    @FXML
    private Canvas roomCanvas;

    Database db = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db = Database.newInstance();
            cbRooms.getItems().setAll(db.getAllRooms());
        } catch (Exception ex) {
            showResultDialog("An error has occured:", ex.getMessage());
        }
    }

    @FXML
    void onSelect(ActionEvent event) {
        try {
            if (event.getSource().equals(btnAddRoom)) {
                addRoom();
            } else if (event.getSource().equals(btnEditRoom)) {
                updateRoom();
            } else if (event.getSource().equals(cbRooms)) {
                Room selectedItem = cbRooms.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    btnEditRoom.setDisable(false);
                    showRoomCoord(selectedItem);
                } else {
                    btnEditRoom.setDisable(true);
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

    private void showRoomCoord(Room selectedItem) {
        GraphicsContext gc = roomCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, roomCanvas.getWidth(), roomCanvas.getHeight()); //clear canvas
        double[] roomCoordinates = selectedItem.getRoomCoordinates();
        int i;
        for (i = 0; i < roomCoordinates.length - 2; i += 2) {
            gc.strokeLine(roomCoordinates[i], roomCoordinates[i + 1], roomCoordinates[i + 2], roomCoordinates[i + 3]);
        }
        gc.strokeLine(roomCoordinates[i], roomCoordinates[i + 1], roomCoordinates[0], roomCoordinates[1]); //connect last line with first to close the room
    }

    private void addRoom() throws Exception {

        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Add new Room"));
        content.setBody(new Text("enter data:"));
        JFXTextField roomNr = new JFXTextField(), roomDesc = new JFXTextField(), roomCoord = new JFXTextField();
        JFXComboBox<Department> cbDep = new JFXComboBox(FXCollections.observableArrayList(Department.values()));
        roomCoord.setPromptText("enter coord in form x,y,x,y...");
        roomNr.setPromptText("room nr");
        roomDesc.setPromptText("room description");

        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());
        JFXButton btnAdd = new JFXButton("ADD");
        JFXButton btnClose = new JFXButton("CANCEL");

        btnAdd.setOnAction((ActionEvent event) -> {
            try {
                String roomNR = roomNr.getText();
                String roomDESC = roomDesc.getText();
                Department roomDep = cbDep.getSelectionModel().getSelectedItem();
                String coord = roomCoord.getText();
                String[] coordAsString = coord.split(",");
                if (roomNR.isEmpty() || roomDESC.isEmpty() || roomDep == null || coord.isEmpty() || (coordAsString.length % 2 == 1) || coordAsString.length < 6) {
                    throw new Exception("data must be valid");
                }
                double[] roomCOORD = Arrays.stream(coordAsString).mapToDouble(Double::parseDouble).toArray(); //convert string array to double array
                Room r = new Room(roomNR, roomDESC, roomDep, roomCOORD);
                db.addRoom(r);
                cbRooms.getItems().setAll(db.getAllRooms());
                dialog.close();
                showResultDialog("Success", "Room successfully added!");
            } catch (Exception ex) {
                showResultDialog("An error has occured:", ex.getMessage());
            }
        });

        btnClose.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(roomNr, roomDesc, cbDep, roomCoord, btnClose, btnAdd);
        dialog.show();
    }

    private void updateRoom() throws Exception {
        Room selR = cbRooms.getSelectionModel().getSelectedItem();
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Update room:" + selR.getRoomNr()));
        JFXTextField roomDesc = new JFXTextField(selR.getRoomDescription()), roomCoord = new JFXTextField(Arrays.toString(selR.getRoomCoordinates()).substring(1).replaceAll("]", ""));
        JFXComboBox<Department> cbDep = new JFXComboBox(FXCollections.observableArrayList(Department.values()));
        cbDep.getSelectionModel().select(selR.getDepartment());
        roomCoord.setPromptText("enter coord in form x,y,x,y...");
        roomDesc.setPromptText("room description");

        JFXDialog dialog = new JFXDialog(mySP, content, JFXDialog.DialogTransition.CENTER);
        dialog.setMinHeight(mySP.getPrefHeight());
        dialog.setMinWidth(mySP.getPrefWidth());
        JFXButton btnUpdate = new JFXButton("UPDATE");
        JFXButton btnClose = new JFXButton("CANCEL");

        btnUpdate.setOnAction((ActionEvent event) -> {
            try {
                String roomDESC = roomDesc.getText();
                Department roomDep = cbDep.getSelectionModel().getSelectedItem();
                String coord = roomCoord.getText();
                String[] coordAsString = coord.split(",");
                if (roomDESC.isEmpty() || roomDep == null || coord.isEmpty() || (coordAsString.length % 2 == 1) || coordAsString.length < 6) {
                    throw new Exception("data must be valid");
                }
                double[] roomCOORD = Arrays.stream(coordAsString).mapToDouble(Double::parseDouble).toArray(); 
                Room r = new Room(selR.getRoomNr(), roomDESC, roomDep, roomCOORD);
                db.updateRoom(r);
                cbRooms.getItems().setAll(db.getAllRooms());
                cbRooms.getSelectionModel().select(r);
                showRoomCoord(r);
                dialog.close();
                showResultDialog("Success", "Room successfully updated!");
            } catch (Exception ex) {
                showResultDialog("An error has occured:", ex.getMessage());
            }
        });

        btnClose.setOnAction((ActionEvent event) -> {
            dialog.close();
        });
        content.setActions(roomDesc, cbDep, roomCoord, btnClose, btnUpdate);
        dialog.show();
    }

}
