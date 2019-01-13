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
import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.BubbleChart;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import pkgData.Database;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import pkgData.Department;
import pkgData.Room;
import pkgData.Subject;
import pkgData.TeacherSpecialization;

/**
 * FXML Controller class
 *
 * @author schueler
 */
public class AnalysisController implements Initializable {

    @FXML
    private StackPane mySP;

    @FXML
    private PieChart pieChartSubjects;

    @FXML
    private PieChart pieChartDepartment;

    @FXML
    private BarChart<String, Number> barChartRoomLessons;

    @FXML
    private CategoryAxis catAxisBar;

    @FXML
    private NumberAxis numAxisBar;

    Database db = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            db = Database.newInstance();
            showPieChart();
            showBarChart();
            showPieChartSubjects();
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

    private void showPieChart() throws Exception {

        int countNONE = 0, countIT = 0, countBC = 0, countCE = 0, countNT = 0, countMT = 0;
        ArrayList<Room> allRooms = db.getAllRooms();
        for (Room r : allRooms) {
            Department department = r.getDepartment();
            switch (department) {
                case NONE:
                    countNONE++;
                    break;
                case NETWORKTECHNOLOGY:
                    countNT++;
                    break;
                case MEDIATECHNOLOGY:
                    countMT++;
                    break;
                case IT:
                    countIT++;
                    break;
                case CIVILENGINEERING:
                    countCE++;
                    break;
                case BUILDINGCONSTRUCTION:
                    countBC++;
                    break;
                default:
                    break;
            }
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("NONE", countNONE),
                new PieChart.Data("NETWORKTECHNOLOGY", countNT),
                new PieChart.Data("MEDIATECHNOLOGY", countMT),
                new PieChart.Data("IT", countIT),
                new PieChart.Data("CIVILENGINEERING", countCE),
                new PieChart.Data("BUILDINGCONSTRUCTION", countBC));

        pieChartDepartment.setTitle("Num. of Rooms by Department");
        pieChartDepartment.setClockwise(true);
        pieChartDepartment.setLabelLineLength(50);
        pieChartDepartment.setLabelsVisible(true);
        pieChartDepartment.setStartAngle(180);
        pieChartDepartment.getData().addAll(pieChartData);

    }

    private void showBarChart() throws Exception {
        catAxisBar.setLabel("school room");
        numAxisBar.setLabel("number of lessons");
        barChartRoomLessons.setTitle("Number of lessons by school room");
        ArrayList<Room> allSchoolRooms = db.getAllSchoolRooms();
        for (Room r : allSchoolRooms) {
            XYChart.Series<String, Number> curSeries = new XYChart.Series<>();
            curSeries.setName(r.getRoomDescription());
            curSeries.getData().add(new XYChart.Data<>("", db.getAllLessonsByRoomNr(r.getRoomNr()).size()));
            barChartRoomLessons.getData().add(curSeries);
        }
    }

    private void showPieChartSubjects() throws Exception {

        ArrayList<Subject> allSubjects = db.getAllSubjects();
        for (Subject s : allSubjects) {
            pieChartSubjects.getData().add(new PieChart.Data(s.getSubjectName(), db.getTSBySubject(s).size()));

        }
        pieChartSubjects.setTitle("how many teachers teach spec. subject");
        pieChartSubjects.setClockwise(true);
        pieChartSubjects.setLabelLineLength(50);
        pieChartSubjects.setLabelsVisible(true);
        pieChartSubjects.setStartAngle(180);
    }

}
