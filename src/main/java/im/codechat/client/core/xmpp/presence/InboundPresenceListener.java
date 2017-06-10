package im.codechat.client.core.xmpp.presence;

import im.codechat.client.core.application.AppManager;
import im.codechat.client.core.application.WorkspaceManager;
import im.codechat.client.core.xmpp.message.MessageDirections;
import im.codechat.client.core.exception.ComponentViewNotFoundException;
import im.codechat.client.core.ui.controls.ChatPane;
import javafx.application.Platform;
import rocks.xmpp.core.stanza.PresenceEvent;
import rocks.xmpp.core.stanza.model.Message;
import rocks.xmpp.core.stanza.model.Presence;
import rocks.xmpp.im.roster.RosterManager;
import rocks.xmpp.im.roster.model.Contact;

import java.util.function.Consumer;

/**
 * The description for InboundPresenceListener class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/28/2017
 */
public class InboundPresenceListener implements Consumer<PresenceEvent> {
    @Override
    public void accept(PresenceEvent presenceEvent) {
        Platform.runLater(() ->{
            Presence presence = presenceEvent.getPresence();
            AppManager.getChatManager().addPresence(presence);
            Contact contact = AppManager.getChatManager().getClient().getManager(RosterManager.class).getContact(presence.getFrom());

            if (contact != null) {
                Message msg = new Message(contact.getJid(), Message.Type.CHAT);
                msg.setBody(contact.getName()+"'s status is now "+(presence.getStatus() != null || presence.getStatus() != "" ? presence.getStatus(): "Offline"));
                ChatPane pane = null;
                try {
                    pane = (ChatPane) WorkspaceManager.getInstance().getChatComponent(contact.getJid().toString()).getPane();
                } catch (ComponentViewNotFoundException e) {
                    e.printStackTrace();
                }
                pane.getChatEntryArea().add(msg, MessageDirections.APPBOUND);
            }

        });
    }
}
