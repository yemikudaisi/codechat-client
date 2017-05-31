package im.codechat.client.ui.main;

import im.codechat.client.core.chat.message.InboundMessageListener;
import im.codechat.client.core.chat.presence.InboundPresenceListener;
import im.codechat.client.core.chat.presence.OutboundPresenceListener;
import im.codechat.client.core.chat.presence.PresenceChangeEvent;
import im.codechat.client.core.chat.presence.PresenceChangeListener;
import im.codechat.client.core.ui.ContactsListViewCellFactory;
import im.codechat.client.core.application.AppManager;
import im.codechat.client.core.application.WorkspaceManager;
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
import rocks.xmpp.im.roster.RosterManager;
import rocks.xmpp.im.roster.model.Contact;
import rocks.xmpp.im.subscription.PresenceManager;

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
            AppManager.getChatManager().dispose();
            this.tryClose();
            new LoginViewController().showView();
        } catch (XmppException e) {
            // TODO Handle exception
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
            // TODO handle exception
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loggedInUserName.setText(AppManager.getSessionUsername());
        RosterManager rosterManager = AppManager.getChatManager().getClient().getManager(RosterManager.class);

        Platform.runLater(() -> {
            // Get all contacts on initalization
            this.contactList = FXCollections.observableList(new ArrayList<Contact>(rosterManager.getContacts()));

            // Get presence for all contacts and add it to ChatManager presence queue
            PresenceManager manager = AppManager.getChatManager().getClient().getManager(PresenceManager.class);
            for (Contact c:this.contactList){
                AppManager.getChatManager().addPresence(manager.getPresence(c.getJid()));
            }
            this.refreshContactsListView();
        });

        rosterManager.addRosterListener( e -> {
            Platform.runLater(() -> {
                new Alert(Alert.AlertType.ERROR, "roster").showAndWait();
                this.contactList = FXCollections.observableArrayList(new ArrayList<Contact>(e.getUpdatedContacts()));
                refreshContactsListView();
            });

        });

        AppManager.getChatManager().getClient().addInboundPresenceListener(new InboundPresenceListener());
        AppManager.getChatManager().getClient().addInboundMessageListener(new InboundMessageListener());

        // add listener to presence combo
        comboPresence.valueProperty().addListener(new OutboundPresenceListener());

        initializeUIComponents();
    }

    private void initializeUIComponents(){
        contactsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                masterPane.getChildren().clear();
                Contact selectedContact = (Contact)newValue;
                ChatComponent component = WorkspaceManager.getInstance().getChatComponent(selectedContact.getJid().toString());
                component.setJid(AppManager.getChatManager().getLocalDomainJid(selectedContact.getJid()));
                try {
                    showChatPane((ChatPane) component.getPane());
                } catch (ComponentViewNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
        AppManager.getChatManager().addPresenceChangeListener(new PresenceChangeListener() {
            @Override
            public void dispatchPresenceChange(PresenceChangeEvent e) {
                refreshContactsListView();
            }
        });
    }

    public void showChatPane(ChatPane pane){
        WorkspaceManager.setSelectedChatComponent(pane.getJid());
        for (int i = 0; i < masterPane.getChildren().size(); i++) {
            ChatPane cP = (ChatPane) masterPane.getChildren().get(i);
            if(cP.getJid() == pane.getJid()){
                cP.setVisible(true);
                return;
            }else{
                cP.setVisible(false);
                return;
            }
        }
        masterPane.getChildren().add(pane);
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

    private void refreshContactsListView(){

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
