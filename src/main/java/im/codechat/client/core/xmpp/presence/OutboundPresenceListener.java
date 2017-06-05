package im.codechat.client.core.xmpp.presence;

import im.codechat.client.core.application.AppManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import rocks.xmpp.core.stanza.model.Presence;

/**
 * The description for OutboundPresenceListener class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/27/2017
 */
public class OutboundPresenceListener implements ChangeListener<String> {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, java.lang.String newValue) {
        switch(newValue){
            case "Available":
                AppManager.getChatManager().changePresence(null);
                break;
            case "Free to xmpp":
                AppManager.getChatManager().changePresence(Presence.Show.CHAT);
                break;
            case "Busy":
                AppManager.getChatManager().changePresence(Presence.Show.DND);
                break;
            case "Away":
                AppManager.getChatManager().changePresence(Presence.Show.AWAY);
                break;
            case "Extended away":
                AppManager.getChatManager().changePresence(Presence.Show.XA);
                break;
        }
    }
}
