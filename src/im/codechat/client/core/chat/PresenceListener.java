package im.codechat.client.core.chat;

import im.codechat.client.core.application.AppManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import rocks.xmpp.core.stanza.model.Presence;

/**
 * The description for PresenceListener class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/27/2017
 */
public class PresenceListener implements ChangeListener<String> {
    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, java.lang.String newValue) {
        switch(newValue){
            case "Available":
                AppManager.getXmppManager().changePresence(null);
                break;
            case "Free to chat":
                AppManager.getXmppManager().changePresence(Presence.Show.CHAT);
                break;
            case "Busy":
                AppManager.getXmppManager().changePresence(Presence.Show.DND);
                break;
            case "Away":
                AppManager.getXmppManager().changePresence(Presence.Show.AWAY);
                break;
            case "Extended away":
                AppManager.getXmppManager().changePresence(Presence.Show.XA);
                break;
        }
    }
}
