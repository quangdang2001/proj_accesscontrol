module com.accesscontroll.proj_cntt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.accesscontroll.proj_cntt to javafx.fxml;
    exports com.accesscontroll.proj_cntt;


}