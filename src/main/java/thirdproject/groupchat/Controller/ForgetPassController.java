package thirdproject.groupchat.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thirdproject.groupchat.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ForgetPassController implements Initializable {

    private Stage forgetPassStage;
    @FXML private Button backBTN;
    @FXML private TextField emailFLD;
    @FXML private Button sendBTN;
    private String verificationCode;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        backBTN.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("View/LoginPage.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            getForgetPassStage().setScene(new Scene(loader.getRoot()));
            getForgetPassStage().setResizable(false);
            LoginController controller = loader.getController();
            controller.initFunction(forgetPassStage);
        });
        sendBTN.setOnAction(event -> {
            if (RegEx.emailRegEx(emailFLD.getText())){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("View/ForgetPassVerification.fxml"));
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                getForgetPassStage().setScene(new Scene(loader.getRoot()));
                getForgetPassStage().setResizable(false);
                ForgetPassVerificationController controller = loader.getController();
                controller.initFunction7(forgetPassStage);
                controller.setVerificationCode(SendEmail.send(emailFLD.getText()));
            }
        });

    }

    public void initFunction6 (Stage forgetPassStage) {
        this.forgetPassStage = forgetPassStage;
    }

    public Stage getForgetPassStage() {
        return forgetPassStage;
    }

    public TextField getEmailFLD() {
        return emailFLD;
    }


}
