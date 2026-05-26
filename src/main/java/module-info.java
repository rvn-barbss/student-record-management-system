module com.example.studentrecordmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.studentrecordmanagementsystem to javafx.fxml;
    exports com.example.studentrecordmanagementsystem;
}