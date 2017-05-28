package im.codechat.client.ui.main;

import im.codechat.client.core.ContactsListViewCellFactory;
import im.codechat.client.core.application.AppManager;
import im.codechat.client.core.chat.ChatManager;
import im.codechat.client.core.chat.InboundMessageConsumer;
import im.codechat.client.core.chat.PresenceListener;
import im.codechat.client.core.exception.ComponentViewNotFoundException;
import im.codechat.client.core.ui.BaseViewController;
import im.codechat.client.core.ui.ChatPane;
import im.codechat.client.ui.components.ChatComponent;
import im.codechat.client.ui.login.LoginViewController;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.stanza.model.Presence;
import rocks.xmpp.im.roster.RosterManager;
import rocks.xmpp.im.roster.model.Contact;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * The description for AppViewController class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/18/2017
 */
public class AppViewController extends BaseViewController {

    private static AppViewController instance;
    private static final Object LOCK = new Object();

    @FXML MenuItem menuExitApplication;
    @FXML ComboBox<String>  comboPresence;
    @FXML Pane masterPane;
    @FXML BorderPane containerPane;
    @FXML Label loggedInUserName;
    @FXML ListView contactsListView;

    public AppViewController(){
    }

    @FXML
    public void logoutAction(){
        try {
            AppManager.getXmppManager().dispose();
            this.tryClose();
            new LoginViewController().showView();
        } catch (XmppException e) {
            // TODO
            e.printStackTrace();
        } catch (IOException e) {
            // TODO1
            e.printStackTrace();
        }
    }

    @FXML
    public void exitAction(){
        try {
            AppManager.exitApplication();
        } catch (XmppException e) {
            // TODO
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedInUserName.setText(AppManager.getSessionUsername());
        RosterManager rosterManager = AppManager.getXmppManager().getClient().getManager(RosterManager.class);

        // Get all contacts
        Platform.runLater(() -> {
            this.contactList = FXCollections.observableList(new ArrayList<Contact>(rosterManager.getContacts()));
            this.updateContactsUI();
        });

        // listen for incoming rosters

        rosterManager.addRosterListener( e -> {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR, "roster").showAndWait();
                this.contactList = FXCollections.observableArrayList(new ArrayList<Contact>(rosterManager.getContacts()));
                updateContactsUI();
            });

        });

        AppManager.getXmppManager().getClient().addInboundPresenceListener(e -> {
            Platform.runLater(() -> {
                Presence presence = e.getPresence();
                Contact contact = AppManager.getXmppManager().getClient().getManager(RosterManager.class).getContact(presence.getFrom());
                if (contact != null) {
                    new Alert(Alert.AlertType.ERROR, "presence").showAndWait();
                }
            });
        });

        AppManager.getXmppManager().getClient().addInboundMessageListener(new InboundMessageConsumer());

        // add listener to presence combo
        comboPresence.valueProperty().addListener(new PresenceListener());

        initializeUIComponents();
    }

    private void initializeUIComponents(){
        //containerGrid
        //try {
            //this.masterPane.getChildren().add(new ChatComponent().getPane());
        //} catch (IOException e) {
            //e.printStackTrace();
        //}
        contactsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                masterPane.getChildren().clear();
                Contact selectedContact = (Contact)newValue;
                try {
                    ChatComponent component = ChatManager.getInstance().getChatComponent(selectedContact.getJid().toString());
                    component.setJid(selectedContact.getJid().toString());
                    //masterPane.getChildren().clear();
                    //masterPane.getChildren().add(component.getPane());
                    showChatPane(component);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void showChatPane(ChatComponent component){
        for (int i = 0; i < masterPane.getChildren().size(); i++) {
            ChatPane cP = (ChatPane) masterPane.getChildren().get(i);
            if(cP.getJid() == component.getJid()){
                cP.setVisible(true);
                return;
            }else{
                cP.setVisible(false);
                return;
            }
        }
        try {
            masterPane.getChildren().add(component.getPane());
        } catch (ComponentViewNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ChatPane getChatPane(String jid){
        for (int i = 0; i < masterPane.getChildren().size(); i++) {
            ChatPane cP = (ChatPane) masterPane.getChildren().get(i);
            if(cP.getJid() == jid){
                return cP;
            }
        }
        return null;
    }

    private void updateContactsUI(){
        contactsListView.setCellFactory(new ContactsListViewCellFactory());
        contactsListView.setItems(this.contactList);
    }

    public static AppViewController getInstance() {
        // Synchronize on LOCK to ensure that we don't end up creating
        // two singletons.
        synchronized (LOCK){
            if(instance == null) {
                AppViewController main= new AppViewController();
                instance = main;
                return main;
            }
            return instance;
        }

    }

    private ObservableList<Contact> contactList;
}
