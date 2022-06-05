package thirdproject.groupchat.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import thirdproject.groupchat.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VerificationController implements Initializable {

    private Stage verStage;
    private String verificationCode;
    @FXML private TextField txtFLD1;
    @FXML private TextField txtFLD2;
    @FXML private TextField txtFLD3;
    @FXML private TextField txtFLD4;
    @FXML private Button veriBTN;
    @FXML private Button BackBTN;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getOneChar(txtFLD1);
        getOneChar(txtFLD2);
        getOneChar(txtFLD3);
        getOneChar(txtFLD4);
        veriBTN.setOnAction(event -> {
            if (RegEx.oneCharFLD(txtFLD1.getText()) &&
                    RegEx.oneCharFLD(txtFLD2.getText()) &&
                    RegEx.oneCharFLD(txtFLD3.getText()) &&
                    RegEx.oneCharFLD(txtFLD4.getText())) {
                String code = txtFLD1.getText() + txtFLD2.getText() + txtFLD3.getText() + txtFLD4.getText();
                if (verificationCode.equals(code)) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("View/ChatPage.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    getVerStage().setScene(new Scene(loader.getRoot()));
                    getVerStage().setResizable(false);
                    ChatController chatController = loader.getController();
                    chatController.initFunction4(verStage);
                }

            }
        });
    }

    @FXML void BackBTNAction (ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/SignUpPage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getVerStage().setScene(new Scene(loader.getRoot()));
        getVerStage().setResizable(false);
        SignUpController signUpController = loader.getController();
        signUpController.initFunction2(verStage);
    }

    public void initFunction3(Stage verStage) {
        this.verStage = verStage;
    }

    private void getOneChar(TextField textField) {
        textField.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String temp = change.getControlNewText();
            if (temp.length() > 1)
                return null;
            else
                return change;
        }));
    }

    public Stage getVerStage() {
        return verStage;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }


}
