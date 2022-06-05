package thirdproject.groupchat.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import thirdproject.groupchat.Exception.UserNotFound;
import thirdproject.groupchat.Main;

import java.io.IOException;

public class AddRemoveFriendsController {

    private Stage userStage;

    @FXML private Button backBTN;
    @FXML private TextField searchFLD;

    @FXML void addBTNAction(ActionEvent event) {
        if (DBUtils.findUser(searchFLD.getText())) {
            searchFLD.setStyle("-fx-border-color: green");
            DBUtils.addRequestToOther(searchFLD.getText());
        } else
            try {
                throw new UserNotFound();
            } catch (UserNotFound e) {
                e.getMessage();
            }
    }

    @FXML void backBTNAction(ActionEvent event) {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/ChatPage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getUserStage().setScene(new Scene(loader.getRoot()));
        getUserStage().setResizable(false);
        ChatController controller = loader.getController();
        controller.initFunction4(userStage);

    }

    public void initFunction5 (Stage userStage) {
        this.userStage = userStage;
    }

    public Stage getUserStage() {
        return userStage;
    }

}
