
module ifrs.edu.com {
    requires io.github.cdimascio.dotenv.java;

    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;

    opens ifrs.edu.com.controllers to javafx.fxml;

    exports ifrs.edu.com;
}
