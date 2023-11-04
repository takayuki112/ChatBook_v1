module com.example.chatbookui_v1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.chatbookui_v1 to javafx.fxml;
    exports com.example.chatbookui_v1;
}