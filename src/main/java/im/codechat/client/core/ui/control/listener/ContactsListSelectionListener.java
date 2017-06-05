package im.codechat.client.core.ui.control.listener;

import im.codechat.client.core.application.WorkspaceManager;
import im.codechat.client.core.exception.ComponentViewNotFoundException;
import im.codechat.client.core.ui.control.ChatPane;
import im.codechat.client.view.components.ChatComponent;
import im.codechat.client.view.main.MainViewController;
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
            MainViewController.getInstance().showChatPane((ChatPane) component.getPane());
        } catch (ComponentViewNotFoundException e) {
            e.printStackTrace();
        }

    }
}
