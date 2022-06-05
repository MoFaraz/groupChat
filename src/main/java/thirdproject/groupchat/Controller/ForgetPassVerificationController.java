package thirdproject.groupchat.Controller;

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

public class ForgetPassVerificationController implements Initializable {

    private Stage forgetVeriStage;
    private String verificationCode;
    @FXML
    private Button resendBTN;
    @FXML private TextField txtFLD1;
    @FXML private TextField txtFLD2;
    @FXML private TextField txtFLD3;
    @FXML private TextField txtFLD4;
    @FXML private Button veriBTN;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getOneChar(txtFLD1);
        getOneChar(txtFLD2);
        getOneChar(txtFLD3);
        getOneChar(txtFLD4);
        veriBTN.setOnAction(event -> {
            if (RegEx.oneCharRegEx(txtFLD1.getText()) &&
                    RegEx.oneCharRegEx(txtFLD2.getText()) &&
                    RegEx.oneCharRegEx(txtFLD3.getText()) &&
                    RegEx.oneCharRegEx(txtFLD4.getText())) {
                String code = txtFLD1.getText() + txtFLD2.getText() + txtFLD3.getText() + txtFLD4.getText();
                if (verificationCode.equals(code)) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("View/UpdatePass.fxml"));
                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    getForgetVeriStage().setScene(new Scene(loader.getRoot()));
                    getForgetVeriStage().setResizable(false);

                    UpdatePassController controller = loader.getController();
                    controller.initFunction20(forgetVeriStage);
                }
            }
        });
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Stage getForgetVeriStage() {
        return forgetVeriStage;
    }

    public void initFunction7 (Stage forgetVeriStage) {
        this.forgetVeriStage = forgetVeriStage;
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

}
