module Client {
    requires javafx.fxml;
    requires javafx.controls;


    opens com.gnida to javafx.fxml;
    exports com.gnida;
}