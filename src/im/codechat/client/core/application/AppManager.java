package im.codechat.client.core.application;

import im.codechat.client.Main;
import im.codechat.client.core.chat.ChatManager;
import im.codechat.client.core.xmpp.XmppManager;
import im.codechat.client.ui.main.AppViewController;
import javafx.application.Platform;
import rocks.xmpp.core.XmppException;

import java.util.prefs.Preferences;

/**
 * The description for AppManager class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/18/2017
 */
public class AppManager {
    private static XmppManager xmppManager;
    private static ChatManager chatManager;
    private static  String sessionUsername =  "";

    public static XmppManager getXmppManager() {
        return xmppManager;
    }

    public static void setXmppManager(XmppManager xmppManager) {
        AppManager.xmppManager = xmppManager;
    }

    public static Preferences getPreferences(){
        return Preferences.userRoot().node(Main.class.getName());
    }

    public static void exitApplication() throws XmppException {
        getXmppManager().dispose();
        Platform.exit();
        System.exit(0);
        // TODO Persist user preferences
    }

    public static String getSessionUsername() {
        return sessionUsername;
    }

    public static void setSessionUsername(String sessionUsername) {
        AppManager.sessionUsername = sessionUsername;
    }

    public static ChatManager getChatManager() {
        return chatManager;
    }

    public static void setChatManager(ChatManager chatManager) {
        AppManager.chatManager = chatManager;
    }

    public static AppViewController getController(){
        return AppViewController.getInstance();
    }
}
