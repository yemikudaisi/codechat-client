package im.codechat.client.core.chat.message;

import im.codechat.client.core.application.AppManager;
import im.codechat.client.core.application.CodeChatManager;
import im.codechat.client.core.application.WorkspaceManager;
import im.codechat.client.core.chat.ChatManager;
import im.codechat.client.core.chat.extensions.codechat.CodeChatOffer;
import im.codechat.client.core.chat.extensions.codechat.CodeChatOfferResponse;
import im.codechat.client.core.chat.extensions.codechat.CodeChatSessionApprovals;
import im.codechat.client.core.chat.extensions.codechat.CodeChatSessionContainers;
import im.codechat.client.core.chat.extensions.codechat.exceptions.SessionNotFoundException;
import im.codechat.client.core.exception.ComponentViewNotFoundException;
import im.codechat.client.core.ui.control.ChatPane;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import rocks.xmpp.core.stanza.MessageEvent;
import rocks.xmpp.core.stanza.model.Message;
import rocks.xmpp.extensions.chatstates.model.ChatState;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * The description for InboundMessageListener class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/27/2017
 */
public class InboundMessageListener implements Consumer<MessageEvent> {
    @Override
    public void accept(MessageEvent messageEvent) {
        Platform.runLater(() ->{
            Message inMsg = messageEvent.getMessage();

            List extensions = inMsg.getExtensions();
            for (Object extension: extensions){
                String className = extension.getClass().getSimpleName();
                switch (className){
                    case "Active": case "Composing": case "Gone": case "Inactive": case "Paused":
                        String state = inMsg.getExtension(ChatState.class).getClass().getSimpleName().toLowerCase();
                        switch (state){
                            case "active":
                                // TODO Fix issue that arises when a chat does not show up unless a prior outbound message has been initiated
                                String from = ChatManager.getLocalDomainJid(inMsg.getFrom());
                                try {
                                    ChatPane pane = (ChatPane) WorkspaceManager.getInstance().getChatComponent(from).getPane();
                                    pane.getChatEntryArea().add(inMsg, MessageDirections.INBOUND);
                                } catch (ComponentViewNotFoundException e) {
                                    // TODO Handle exception
                                    e.printStackTrace();
                                }
                                //WorkspaceManager.getInstance().getChatComponent(from).addChatMessage(msg, MessageDirections.INBOUND);

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
                        break;

                    case "CodeChatOffer":
                        CodeChatOffer offer = inMsg.getExtension(CodeChatOffer.class);
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Folder Share Offer");
                        alert.setHeaderText(inMsg.getFrom().getLocal()+" is offering to share a project folder with you \n"+offer.getName());
                        alert.setContentText("Accept this offer?");

                        ButtonType yes = new ButtonType("Yes");
                        ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(yes, no);

                        Optional<ButtonType> result = alert.showAndWait();
                        Message msg = AppManager.getChatManager().buildMessage(inMsg.getFrom().toString());
                        if (result.get() == yes){
                            msg.addExtension(CodeChatManager.getInstance().approveHostOffer(offer, inMsg.getFrom()));
                        } else {
                            msg.addExtension(CodeChatManager.getInstance().denyHostOffer(offer));
                            //TODO add "You denied a CodeChat offer" to chat area"
                        }
                        AppManager.getChatManager().sendMessage(msg);
                        break;

                    case "CodeChatOfferResponse":
                        CodeChatOfferResponse response = inMsg.getExtension(CodeChatOfferResponse.class);
                        if(!response.isAccept()){
                            // delete session generated for initiating offer from pending
                            try {
                                CodeChatManager
                                        .getInstance()
                                        .removeSession(response.getKey(), CodeChatSessionContainers.PENDING);
                                //TODO add "Your CodeChat offer was denied" to chat area
                            } catch (SessionNotFoundException e) {
                                // TODO Handle exception
                                e.printStackTrace();
                            }
                        }else{
                            //TODO add "Your CodeChat offer was approved" to chat area
                            try {
                                CodeChatManager
                                        .getInstance()
                                        .approveGuestSession(response.getKey(), CodeChatSessionApprovals.GUEST);
                                String sessionRootPath = CodeChatManager
                                        .getInstance()
                                        .getSessionRootPaths()
                                        .get(response.getKey());
                                ChatPane pane = (ChatPane) WorkspaceManager.getInstance().getChatComponent(ChatManager.getLocalDomainJid(inMsg.getFrom())).getPane();


                            } catch (SessionNotFoundException e) {
                                // TODO Handle exception
                                e.printStackTrace();
                            } catch (ComponentViewNotFoundException e) {
                                // TODO Handle exception
                                e.printStackTrace();
                            }
                        }
                        break;

                    default:
                        break;
                }
            }
        });
    }
}
