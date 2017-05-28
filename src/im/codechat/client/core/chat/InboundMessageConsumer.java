package im.codechat.client.core.chat;

import im.codechat.client.core.application.AppManager;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import rocks.xmpp.core.stanza.MessageEvent;
import rocks.xmpp.core.stanza.model.Message;
import rocks.xmpp.extensions.chatstates.model.ChatState;

import java.util.function.Consumer;

/**
 * The description for InboundMessageConsumer class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/27/2017
 */
public class InboundMessageConsumer implements Consumer<MessageEvent> {
    @Override
    public void accept(MessageEvent messageEvent) {
        Platform.runLater(() ->{
            Message msg = messageEvent.getMessage();
            String state = msg.getExtension(ChatState.class).getClass().getSimpleName().toLowerCase();
            switch (state){
                case "active":
                    String from = msg.getFrom().getLocal()+"@"+msg.getFrom().getDomain();
                    Node n =AppManager.getController().getChatPane(from).getChild("chatArea");
                    GridPane pane = (GridPane)n;
                    //ChatManager.getInstance().getChatComponent(from).addChatMessage(msg, MessageDirections.INBOUND);
                    break;
                case "composing":
                    break;
                case "gone":
                    break;
                case "inactive":
                    break;
                case "paused":
                    break;
            }
        });
    }
}
