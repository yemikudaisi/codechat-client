package im.codechat.client.ui.components;

import im.codechat.client.core.chat.ChatManager;
import im.codechat.client.core.chat.MessageDirections;
import im.codechat.client.core.chat.MessageQueue;
import im.codechat.client.core.exception.ComponentViewNotFoundException;
import im.codechat.client.core.ui.BaseComponentController;
import im.codechat.client.core.ui.ChatPane;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import rocks.xmpp.core.stanza.model.Message;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

/**
 * A reusable component for rendering sent and received message
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/18/2017
 */
public class ChatComponent extends BaseComponentController {

    @FXML AnchorPane rootPane;
    @FXML GridPane chatArea;
    @FXML TextArea outboundMessageText;
    @FXML Label paneContactLabel;
    List<MessageQueue> messageQueues;

    private String jid;

    public ChatComponent(){
        messageQueues = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location,resources);
        this.processMessageQueue();

        paneContactLabel.setText(this.getJid());
        //this.setPane(rootPane);
    }

    public void addChatMessage(Message msg, MessageDirections type){
        if(!this.isInitialized()){
            messageQueues.add(new MessageQueue(msg,type));
            return;
        }

        Label chatMessage = new Label(msg.getBody());
        Label time =  new Label(new SimpleDateFormat("HH:mm:ss dd/MM/yyy").format(Calendar.getInstance().getTime()));
        time.getStyleClass().add("chat-bubble-time");
        switch(type){
            case INBOUND :
                chatMessage.getStyleClass().add("chat-bubble-inbound");
                GridPane.setHalignment(chatMessage, HPos.LEFT);
                GridPane.setHalignment(time, HPos.LEFT);
                break;
            case OUTBOUND:
                chatMessage.getStyleClass().add("chat-bubble-outbound");
                GridPane.setHalignment(chatMessage, HPos.RIGHT);
                GridPane.setHalignment(time, HPos.RIGHT);
                break;
            default:
                chatMessage.getStyleClass().add("chat-bubble-default");
                GridPane.setHalignment(chatMessage, HPos.CENTER);
                GridPane.setHalignment(time, HPos.CENTER);
                break;
        }
        chatArea.addRow( chatArea.getChildren().size(), chatMessage);
    }

    private void processMessageQueue(){
        for (MessageQueue m: this.messageQueues) {
            addChatMessage(m.getMessage(),m.getDirection());
        }
    }

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {

        this.jid = jid;
        try {
            ChatPane pane = (ChatPane) this.getPane();
            pane.setJid(jid);
        } catch (ComponentViewNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pane getPane(
    }
}
