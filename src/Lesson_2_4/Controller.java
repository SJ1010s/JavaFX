package Lesson_2_4;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class Controller {
    @FXML
    public TextArea textArea;
    @FXML
    public TextField textField;
    @FXML
    public Button message;

    public void onClickBtn(ActionEvent actionEvent) {
        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER)  {
                    String text;
                    textArea.appendText(textField.getText() + "\n");
                    textField.setText("");
                }
            }
        });
        textArea.appendText(textField.getText() + "\n");
    }

}
