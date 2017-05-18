package im.codechat.client.ui.main;

import im.codechat.client.application.Globals;
import im.codechat.client.core.ui.BaseController;
import im.codechat.client.ui.components.ChatComponent;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import rocks.xmpp.core.stanza.model.Presence;
import rocks.xmpp.im.roster.RosterManager;
import rocks.xmpp.im.roster.model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * The description for MainViewController class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/18/2017
 */
public class MainViewController extends BaseController {


    @FXML
    MenuItem menuExitApplication;
    @FXML
    TreeItem<String> onlineContactsTree;
    @FXML
    TreeItem<String> offlineeContactsTree;
    @FXML
    ComboBox<String>  comboPresence;
    @FXML
    Pane masterPane;

    public MainViewController(){
        contactList = new ArrayList<>();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeUIComponents();;

        RosterManager rosterManager = Globals.getXmppHandler().getClient().getManager(RosterManager.class);

        // Get all contacts
        Platform.runLater(() -> {
            this.contactList = rosterManager.getContacts();
            this.updateContactsUI();
        });

        // listen for incoming rosters

        rosterManager.addRosterListener( e -> {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR, "roster").showAndWait();
                this.contactList = rosterManager.getContacts();
                updateContactsUI();
            });

        });

        Globals.getXmppHandler().getClient().addInboundPresenceListener(e -> {
            Platform.runLater(() -> {
                Presence presence = e.getPresence();
                Contact contact = Globals.getXmppHandler().getClient().getManager(RosterManager.class).getContact(presence.getFrom());
                if (contact != null) {
                    new Alert(Alert.AlertType.ERROR, "presence").showAndWait();
                }
            });
        });

        // add listener to presence combo
        comboPresence.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                switch(newValue){
                    case "Available":
                        Globals.getXmppHandler().changePresence(null);
                        break;
                    case "Free to chat":
                        Globals.getXmppHandler().changePresence(Presence.Show.CHAT);
                        break;
                    case "Busy":
                        Globals.getXmppHandler().changePresence(Presence.Show.DND);
                        break;
                    case "Away":
                        Globals.getXmppHandler().changePresence(Presence.Show.AWAY);
                        break;
                    case "Extended away":
                        Globals.getXmppHandler().changePresence(Presence.Show.XA);
                        break;
                }
            }
        });
    }

    private void initializeUIComponents(){
        try {
            this.masterPane.getChildren().add(new ChatComponent().getPane());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateContactsUI(){
        for (Contact contact : this.contactList) {
            onlineContactsTree.getChildren().add(new TreeItem<String>(contact.getName()));
        }
    }

    private Collection<Contact> contactList;
}
