package thirdproject.groupchat.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import thirdproject.groupchat.Main;
import thirdproject.groupchat.Model.MyMessage;
import thirdproject.groupchat.Model.User;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatController implements Initializable {
    private final ArrayList<User> allUser = DBUtils.showUser();
    private final ArrayList<MyMessage> messages = DBUtils.showMessage();
    private Stage chatStage;
    private ImageView imageView;

    @FXML private VBox rightVBOX;
    @FXML private VBox personVBOX;
    @FXML private Button logoutBTN;
    @FXML private Button friendsBTN;
    @FXML private Button groupBTN;
    @FXML private Button requestBTN;
    @FXML private Button blockBTN;

    @FXML void friendsBTNAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/Users.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        getChatStage().setScene(new Scene(loader.getRoot()));
        getChatStage().setResizable(false);
        AddRemoveFriendsController controller = loader.getController();
        controller.initFunction5(chatStage);
    }

    @FXML void blockBTNAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/Block.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getChatStage().setScene(new Scene(loader.getRoot()));
        getChatStage().setResizable(false);
        AddRemoveBlockController controller = loader.getController();
        controller.initFunction9(chatStage);

    }

    @FXML void groupBTNAction(ActionEvent event) {
       /* FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/Company.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getChatStage().setScene(new Scene(loader.getRoot()));
        getChatStage().setResizable(false);
        CompanyController controller = loader.getController();
        controller.initFunction8(chatStage);*/
    }

    @FXML void logoutAction(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("View/LoginPage.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getChatStage().setScene(loader.getRoot());
        getChatStage().setResizable(false);
        LoginController loginController = loader.getController();
        loginController.initFunction(chatStage);
        DBUtils.logout();
    }

    @FXML void requestBTNAction(ActionEvent event) {
        /*FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("View/Request.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        getChatStage().setScene(new Scene(loader.getRoot()));
        getChatStage().setResizable(false);
        RequestController controller = loader.getController();
        controller.initFunction11(chatStage);*/
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (allUser != null) {
            for (User user : allUser) {
                if (user.getArrayID() == DBUtils.getOnlineID())
                    continue;
                imageView = new ImageView();
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                Button userBTN = new Button(user.getUsername());
                userBTN.setStyle("-fx-background-color:  #636566;");
                HBox.setMargin(userBTN , new Insets(5 , 0 , 0 , 6));
                File file = new File("src/main/resources/image/person.png");
                Image image = new Image(file.toURI().toString());
                imageView.setImage(image);
                imageView.setFitWidth(38);
                imageView.setFitHeight(37);
                HBox.setMargin(imageView , new Insets(8 , 0 ,0 , 5));

                userBTN.setOnAction(event -> {
                    HBox pageHbox = new HBox();
                    VBox pageVbox = new VBox();
                    HBox sendBox = new HBox();
                    TextField sendFLD = new TextField();

                    sendFLD.setPromptText("Type");
                    sendFLD.setStyle("-fx-background-color: rgba(0,0,0,0);");
                    sendFLD.setPrefWidth(468);
                    sendFLD.setPrefHeight(26);
                    sendBox.setMargin(sendFLD,new Insets(6 , 0 , 6 , 12));
                    sendBox.setPrefHeight(35);
                    sendBox.setPrefWidth(491);
                    sendBox.getChildren().add(sendFLD);
                    sendBox.setStyle("-fx-background-color: #636566;" +
                            "-fx-background-radius: 100px;");
                    rightVBOX = new VBox();
                    rightVBOX.setPrefWidth(504);
                    rightVBOX.setPrefHeight(472);

                    Text usernameTXT = new Text(user.getUsername());
                    Text userLastSeen = new Text("Last Seen Recently");
                    imageView.setImage(image);
                    imageView.setFitWidth(38);
                    imageView.setFitHeight(37);
                    pageHbox.setMargin(imageView , new Insets(8 , 0 , 0 , 5));

                    pageVbox.getChildren().addAll(usernameTXT,userLastSeen);

                    pageVbox.setMargin(usernameTXT , new Insets(10 , 0 , 0 , 7));
                    pageVbox.setMargin(userLastSeen , new Insets(8 , 0 , 0 , 7));

                    pageHbox.getChildren().addAll(imageView,pageVbox);
                    Line line = new Line();
                    line.setStartX(-100);
                    line.setStartY(0);
                    line.setEndX(390);
                    line.setEndY(0);

                    VBox messageBox = new VBox();
                    messageBox.setPrefHeight(380);
                    messageBox.setPrefWidth(491);
                    String sender = String.valueOf(DBUtils.getOnlineID()) + " " + String.valueOf(user.getArrayID());
                    printMessage(messageBox,sender);

                    rightVBOX.setOnKeyPressed(event1 -> {
                        if (event1.getCode() == KeyCode.ENTER) {
                            String messageToSend = sendFLD.getText();
                            if (!messageToSend.isEmpty()) {
                                HBox message_hBox = new HBox();
                                message_hBox.setAlignment(Pos.CENTER_RIGHT);
                                message_hBox.setPadding(new Insets(5, 5, 5, 10));

                                Text text = new Text(messageToSend);
                                TextFlow textFlow = new TextFlow(text);

                                DBUtils.addMessage(messageToSend,sender, GetDate.getCurrentDate(), GetDate.getCurrentTime());

                                textFlow.setStyle("-fx-color : white; -fx-background-color: #46474A;" +
                                        "-fx-background-radius: 20px;");

                                textFlow.setPadding(new Insets(5, 10, 5, 10));

                                message_hBox.getChildren().add(textFlow);
                                messageBox.getChildren().add(message_hBox);

                                sendFLD.clear();
                            }
                        }
                    });
                    rightVBOX.getChildren().addAll(pageHbox,line,messageBox,sendBox);
                });
                hBox.getChildren().addAll(imageView, userBTN);
                personVBOX.getChildren().add(hBox);
            }
        }

        /*

        showGroups(DBUtils.getGroup());
        if (DBUtils.getFriends().charAt(0) != '0') {
            for (int i = 0; i < DBUtils.getFriends().length(); i++) {
                if (DBUtils.getFriends().charAt(i) == ' ')
                    continue;
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5, 5, 5, 10));
                Button userBTN = new Button(DBUtils.getUsername(Integer.parseInt(String.valueOf(DBUtils.getFriends().charAt(i)))));

                userBTN.setOnAction(event -> {
                    vbox_message = new VBox();

                    sender = String.valueOf(DBUtils.getOnlineID());
                    String username = String.valueOf(DBUtils.getUserID(userBTN.getText()));
                    sender += " " + username;
                    printMessage(DBUtils.showMessage());
                });
                hBox.getChildren().add(userBTN);
                vbox_friends.getChildren().add(hBox);
            }
        }

        if (DBUtils.getBlockList().charAt(0) != '0') {
            for (int i = 0; i < DBUtils.getBlockList().length(); i++) {
                if (DBUtils.getBlockList().charAt(i) == ' ')
                    continue;
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5, 5, 5, 10));
                Button userBTN = new Button(DBUtils.getUsername(Integer.parseInt(String.valueOf(DBUtils.getBlockList().charAt(i)))));

                hBox.getChildren().add(userBTN);
                vbox_block.getChildren().add(hBox);
            }
        }

        if (DBUtils.getRequest().charAt(0) != '0') {
            for (int i = 0; i < DBUtils.getRequest().length(); i++) {
                if (DBUtils.getRequest().charAt(i) == ' ')
                    continue;
                HBox hBox = new HBox();
                hBox.setAlignment(Pos.CENTER_LEFT);
                hBox.setPadding(new Insets(5, 5, 5, 10));
                Button userBTN = new Button(DBUtils.getUsername(Integer.parseInt(String.valueOf(DBUtils.getBlockList().charAt(i)))));

                hBox.getChildren().add(userBTN);
                vbox_block.getChildren().add(hBox);
            }
        }


       */
    }

    public void initFunction4 (Stage chatStage) {
        this.chatStage = chatStage;
    }

    public Stage getChatStage() {
        return chatStage;
    }


    /*private void showGroups (ArrayList<Groups> groups) {
        for (Groups group : groups) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setPadding(new Insets(5, 5, 5, 10));
            Button userBTN = new Button(group.getGroupID().replace("@" , ""));

            userBTN.setOnAction(event -> {
                groupName = userBTN.getText();
            });
            hBox.getChildren().add(userBTN);
            vbox_groups.getChildren().add(hBox);
        }
    }*/
    public void printMessage(VBox vbox_message, String sender) {
        String reversSender = ReversString.reverseString(sender);
        for (MyMessage message : messages) {
            if (message.getSender().equals(sender) ||
                    message.getSender().equals(reversSender)) {
                HBox hBox = new HBox();
                Text text = new Text(message.getContent());
                TextFlow textFlow = new TextFlow(text);
                hBox.setPadding(new Insets(5, 5, 5, 10));

                if (message.getSender().equals(sender)) {
                    hBox.setAlignment(Pos.CENTER_RIGHT);
                    textFlow.setStyle("-fx-color : white; -fx-background-color: #46474A;" +
                            "-fx-background-radius: 20px;");
                }
                else if (message.getSender().equals(reversSender)) {
                    hBox.setAlignment(Pos.CENTER_LEFT);
                    textFlow.setStyle("-fx-color : white; -fx-background-color: #636566;" +
                            "-fx-background-radius: 20px;");
                }

                textFlow.setPadding(new Insets(5, 10, 5, 10));

                hBox.getChildren().add(textFlow);
                vbox_message.getChildren().add(hBox);

            }
        }
    }

}
