package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicInteger;


public class ChatScreenController {
    @FXML
    private ListView<Message> chatList;
    @FXML
    private TextArea inputTextArea;

    private Timeline timeline;

    public void executeSend() {
        if (timeline != null) timeline.stop();
        String message = inputTextArea.getText();
        Message userMessage = new Message(message, true);
        chatList.getItems().add(userMessage);
        chatList.scrollTo(chatList.getItems().size());
        inputTextArea.clear();
        autoChat();
    }

    public void run() {
        chatList.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Message item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Label messageLabel = new Label(item.getContent());
                    messageLabel.getStyleClass().add("message");
                    setGraphic(messageLabel);

                    if (item.isUserMessage()) {
                        getStyleClass().add("send-message");
                    } else {
                        getStyleClass().add("receive-message");
                    }
                }
            }
        });

        inputTextArea.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                executeSend();
            }
        });
    }

    public void executeAudioCall() {
        showFunctionInDevelopmentAlert();
    }

    public void executeVideoCall() {
        showFunctionInDevelopmentAlert();
    }

    public void executeVoiceChat() {
        showFunctionInDevelopmentAlert();
    }

    public void autoChat() {
        AtomicInteger botMessageCount = new AtomicInteger();

        timeline = new Timeline(new KeyFrame(Duration.seconds(10), event -> {
            String message = "Test test 123";
            Message userMessage = new Message(message, false);
            chatList.getItems().add(userMessage);

            chatList.scrollTo(chatList.getItems().size());

            botMessageCount.getAndIncrement();

            if (botMessageCount.get() >= 3) {
                timeline.stop();
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void showFunctionInDevelopmentAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Function in development");
        alert.setHeaderText("This function is currently in development");
        alert.setContentText("This function is currently in development. Please try again later.");
        alert.showAndWait();
    }
}