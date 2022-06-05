package thirdproject.groupchat.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thirdproject.groupchat.Exception.UserNotFound;
import thirdproject.groupchat.Main;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddRemoveGroup implements Initializable {

    private Stage companyStage;
    @FXML private Button backBTN;
    @FXML private Button createGroupBTN;
    @FXML private TextField groupIDFLD;
    @FXML private TextField searchUserFLD;

    @FXML
    void createGroupBTNAction(ActionEvent event) {
        if (RegEx.groupRegEx(groupIDFLD.getText())) {
            groupIDFLD.setStyle("-fx-border-color: green");
            DBUtils.createCompany(groupIDFLD.getText());
        }
    }

    @FXML
    void addUserAction(ActionEvent event) {
        if (DBUtils.findUser(searchUserFLD.getText())) {
            searchUserFLD.setStyle("-fx-border-color: green");
            DBUtils.addCompanyUser(groupIDFLD.getText(),searchUserFLD.getText());
        } else
            try {
                throw new UserNotFound();
            } catch (UserNotFound e) {
                e.getMessage();
            }
    }

    @FXML
    void backBTNAction(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/ChatPage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getCompanyStage().setScene(new Scene(loader.getRoot()));
        getCompanyStage().setResizable(false);
        ChatController controller = loader.getController();
        controller.initFunction4(companyStage);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
     /*   vbox_group.heightProperty().addListener((observable, oldValue, newValue) -> sp_main.setVvalue((Double) newValue));

        backBTN.setOnAction(event -> {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("View/Messanger.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            getCompanyStage().setScene(new Scene(loader.getRoot()));
            getCompanyStage().setResizable(false);
            ChatPageController controller = loader.getController();
            controller.initFunction4(companyStage);

        });

        if (DBUtils.getFriends().charAt(0) != '0') {
            for (int i = 0; i < DBUtils.getFriends().length(); i++) {
                if (DBUtils.getFriends().charAt(i) == ' ')
                    continue;
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5, 5, 5, 10));
                Button userBTN = new Button(DBUtils.getUsername(Integer.parseInt(String.valueOf(DBUtils.getFriends().charAt(i)))));

                hBox.getChildren().add(userBTN);
                vbox_group.getChildren().add(hBox);
            }
        }

    }*/
    }

    public void initFunction8(Stage companyStage) {
        this.companyStage = companyStage;
    }

    public Stage getCompanyStage() {
        return companyStage;
    }
}
