package thirdproject.groupchat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import thirdproject.groupchat.Controller.LoginController;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/LoginPage.fxml"));
        try{
            loader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        LoginController controller = loader.getController();
        controller.initFunction(stage);

        stage.setScene(new Scene(loader.getRoot()));
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}