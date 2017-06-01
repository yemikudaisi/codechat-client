package im.codechat.client.core.ui.control;

import im.codechat.client.core.chat.message.MessageDirections;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import rocks.xmpp.core.stanza.model.Message;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The description for ChatEntryContainer class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/28/2017
 */
public class ChatEntryContainer extends GridPane {

    public ChatEntryContainer(){
        this.getStyleClass().add("chat-area");
    }

    public void add(Message msg, MessageDirections type){
        Label chatMessage = new Label(msg.getBody());
        Label time =  new Label(new SimpleDateFormat("HH:mm:ss, d MMM yy").format(Calendar.getInstance().getTime()));
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
        this.addRow( this.getChildren().size(), chatMessage);
        this.addRow( this.getChildren().size(), time);
    }
}
