package thirdproject.groupchat.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import thirdproject.groupchat.Main;

import java.io.IOException;
import java.util.regex.Pattern;

public class SignUpController {
    private Stage signUpStage;
    private int verificationCode;
    private String code;
    @FXML
    private AnchorPane signUpPane;
    @FXML private TextField emailFLD;
    @FXML private Label emailLBL;
    @FXML private Label passwordLBL;
    @FXML private PasswordField passwordTXT;
    @FXML private Button signUpBTN;
    @FXML private Label signUpLBL;
    @FXML private Label userLBL;
    @FXML private TextField userTXT;
    @FXML private PasswordField conPassFLD;


    @FXML void signUpAction(ActionEvent event) throws IOException {
        if (Pattern.matches("^(.+)@(.+)$", emailFLD.getText()) &&
                Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", passwordTXT.getText()) &&
                Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", conPassFLD.getText()) &&
                Pattern.matches("^[a-zA-Z0-9._-]{3,}$", userTXT.getText()) &&
                passwordTXT.getText().equals(conPassFLD.getText())) {
            emailFLD.setStyle("-fx-border-color: green");
            userTXT.setStyle("-fx-border-color: green");
            passwordTXT.setStyle("-fx-border-color: green");
            conPassFLD.setStyle("-fx-border-color: green");
            DBUtils.signUp(event,userTXT.getText(),passwordTXT.getText(),emailFLD.getText());
            code = SendEMail.send(emailFLD.getText());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("View/VerificationPage.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            getSignUpStage().setScene(new Scene(loader.getRoot()));
            getSignUpStage().setResizable(false);
            VerificationControllerSignUp verificationController = loader.getController();
            verificationController.initFunction3(signUpStage);
            verificationController.setVerificationCode(code);
        }
    }

    @FXML void backBTNAction () {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/LoginPage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getSignUpStage().setScene(new Scene(loader.getRoot()));
        getSignUpStage().setResizable(false);
        LoginController loginController = loader.getController();
        loginController.initFunction(signUpStage);
    }

    public Stage getSignUpStage() {
        return signUpStage;
    }

    public int getVerificationCode() {
        return verificationCode;
    }

    public void initFunction2 (Stage signUpStage) {
        this.signUpStage = signUpStage;
    }

}