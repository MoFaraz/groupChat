package thirdproject.groupchat.Controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdatePassController implements Initializable {
    private Stage updatePassController;
    private String email;

    @FXML private Button backBTN;
    @FXML private PasswordField conPassFLD;
    @FXML private Button confirmBTN;
    @FXML private PasswordField newPassFLD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initFunction20(Stage updatePassController) {
        this.updatePassController = updatePassController;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Stage getUpdatePassController() {
        return updatePassController;
    }

}
