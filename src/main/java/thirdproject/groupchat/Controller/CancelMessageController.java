package thirdproject.groupchat.Controller;


import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

public class CancelMessageController implements Runnable{

    private KeyEvent event;
    private VBox messageBox;

    @Override
    public void run() {

        for (int i = 0 ; i < 10 ; i ++) {

            if (event.getCode() == KeyCode.B) {
                messageBox.getChildren().clear();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void setEvent(KeyEvent event) {
        this.event = event;
    }

    public void setMessageBox(VBox messageBox) {
        this.messageBox = messageBox;
    }


}
