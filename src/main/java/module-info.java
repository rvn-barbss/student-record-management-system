module com.example.studentrecordmanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql; // YOU MUST ADD THIS LINE

    opens com.example.studentrecordmanagementsystem to javafx.fxml;
    exports com.example.studentrecordmanagementsystem;
}