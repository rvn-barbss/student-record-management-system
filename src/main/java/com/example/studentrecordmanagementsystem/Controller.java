package com.example.studentrecordmanagementsystem;

import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class Controller {
    @FXML private TextField txtName;
    @FXML private ChoiceBox<String> cbCourse; // Updated to ChoiceBox
    @FXML private ChoiceBox<YearLevel> cbYear;
    @FXML private TableView<Student> table;
    @FXML private TableColumn<Student, Integer> colId;
    @FXML private TableColumn<Student, String> colName;
    @FXML private TableColumn<Student, String> colCourse;
    @FXML private TableColumn<Student, String> colYear;

    private ObservableList<Student> list = FXCollections.observableArrayList();
    private Connection conn;
    private int selectedId = -1;

    // Array of course acronyms
    private final String[] courses = {
            "BSIT", "BSA", "BSBA-MM", "BSBA-HRM",
            "BSCpE", "BSIE", "BSECE", "BSED-MT", "BSED-EN"
    };

    @FXML
    public void initialize() {
        conn = DBConnection.connect();

        cbYear.getItems().setAll(YearLevel.values());
        cbCourse.getItems().addAll(courses); // Populate the Course ChoiceBox

        colId.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        colName.setCellValueFactory(data -> data.getValue().nameProperty());
        colCourse.setCellValueFactory(data -> data.getValue().courseProperty());
        colYear.setCellValueFactory(data -> data.getValue().yearLevelProperty());

        loadData();

        table.setOnMouseClicked(e -> {
            Student s = table.getSelectionModel().getSelectedItem();
            if (s != null) {
                selectedId = s.getId();
                txtName.setText(s.getName());
                cbCourse.setValue(s.getCourse()); // Map table data back to ChoiceBox

                for (YearLevel y: YearLevel.values()) {
                    if (y.toString().equals(s.getYearLevel())) {
                        cbYear.setValue(y);
                    }
                }
            }
        });
    }

    private void loadData() {
        list.clear();
        try {
            String query = "SELECT * FROM students ORDER BY id ASC";
            ResultSet rs = conn.createStatement().executeQuery(query);
            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("course"),
                        rs.getString("year_level")
                ));
            }
            table.setItems(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Validation Method: Checks for empty fields
    private boolean isInputValid() {
        if (txtName.getText() == null || txtName.getText().trim().isEmpty() ||
                cbCourse.getValue() == null ||
                cbYear.getValue() == null) {

            showAlert("Validation Error", "Please fill in all fields (Name, Course, and Year Level) before proceeding.");
            return false;
        }
        return true;
    }

    @FXML
    private void addStudent() {
        if (!isInputValid()) return; // Stop execution if fields are empty

        try {
            String query = "INSERT INTO students(name, course, year_level) VALUES (?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, txtName.getText().trim());
            pst.setString(2, cbCourse.getValue()); // Retrieve from ChoiceBox
            pst.setString(3, cbYear.getValue().toString());
            pst.executeUpdate();

            loadData();
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateStudent() {
        if (selectedId == -1) {
            showAlert("Selection Error", "Please select a student from the table to update.");
            return;
        }

        if (!isInputValid()) return; // Stop execution if fields are empty

        try {
            String query = "UPDATE students SET name=?, course=?, year_level=? WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, txtName.getText().trim());
            pst.setString(2, cbCourse.getValue()); // Retrieve from ChoiceBox
            pst.setString(3, cbYear.getValue().toString());
            pst.setInt(4, selectedId);
            pst.executeUpdate();

            loadData();
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteStudent() {
        if (selectedId == -1) {
            showAlert("Selection Error", "Please select a student from the table to delete.");
            return;
        }

        try {
            String query = "DELETE FROM students WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, selectedId);
            pst.executeUpdate();

            loadData();
            clearFields();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clearFields() {
        txtName.clear();
        cbCourse.setValue(null); // Clear ChoiceBox
        cbYear.setValue(null);
        selectedId = -1;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}