package im.codechat.client.core.ui;

import im.codechat.client.core.application.AppManager;
import im.codechat.client.core.application.WorkspaceManager;
import im.codechat.client.core.exception.ComponentViewNotFoundException;
import im.codechat.client.ui.components.ChatComponent;
import im.codechat.client.ui.main.AppViewController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import rocks.xmpp.im.roster.model.Contact;

/**
 * The description for ContactsListSelectionListener class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/28/2017
 */
public class ContactsListSelectionListener implements ChangeListener {
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        Contact selectedContact = (Contact)newValue;
        ChatComponent component = WorkspaceManager.getInstance().getChatComponent(selectedContact.getJid().toString());
        component.setJid(selectedContact.getJid().toString());
        try {
            AppViewController.getInstance().showChatPane((ChatPane) component.getPane());
        } catch (ComponentViewNotFoundException e) {
            e.printStackTrace();
        }

    }
}
