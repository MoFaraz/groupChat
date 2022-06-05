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

public class AddRemoveBlockController {

    private Stage blockStage;

    @FXML private Button backBTN;
    @FXML private Button blockBTN;
    @FXML private Button unBlockBTN;
    @FXML private TextField searchUserFLD;

    @FXML void backBTNAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/ChatPage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getBlockStage().setScene(new Scene(loader.getRoot()));
        getBlockStage().setResizable(false);
        ChatController controller = loader.getController();
        controller.initFunction4(blockStage);
    }

    @FXML void blockBTNAction(ActionEvent event) {
        if (DBUtils.findUser(searchUserFLD.getText())){
            DBUtils.addBlockUser(searchUserFLD.getText());
            DBUtils.removeFromFriends(searchUserFLD.getText());
        } else {
            try {
                throw new UserNotFound();
            } catch (UserNotFound e) {
                e.getMessage();
            }
        }
    }

    @FXML void unBlockBTNAction(ActionEvent event) {
        if (DBUtils.findUser(searchUserFLD.getText()))
            DBUtils.removeFromBlocklist(searchUserFLD.getText());
        else
            try {
                throw new UserNotFound();
            }catch (UserNotFound e){
                e.getMessage();
            }
    }

    public void initFunction9 (Stage blockStage) {
        this.blockStage = blockStage;
    }


    public Stage getBlockStage() {
        return blockStage;
    }

}
