package im.codechat.client.core.application;

import im.codechat.client.ui.components.ChatComponent;

import java.util.HashMap;

/**
 * The description for WorkspaceManager class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/27/2017
 */
public class WorkspaceManager {

    private HashMap<String, ChatComponent> chatComponents;

    private static WorkspaceManager instance;
    private static final Object LOCK = new Object();
    private static String selectedChatComponent;

    public WorkspaceManager(){
        chatComponents = new HashMap<>();
    }

    public static String getSelectedChatComponent() {
        return selectedChatComponent;
    }

    public static void setSelectedChatComponent(String selectedChatComponent) {
        WorkspaceManager.selectedChatComponent = selectedChatComponent;
    }

    public ChatComponent getChatComponent(String userName){
        if(!chatComponents.containsKey(userName))
            chatComponents.put(userName, new ChatComponent());

        return chatComponents.get(userName);
    }

    public void updateChatComponent(String jid, ChatComponent component){
        if (chatComponents.containsKey(jid))
            chatComponents.replace(jid, chatComponents.get(jid), component);
    }

    public static WorkspaceManager getInstance() {
        // Synchronize on LOCK to ensure that we don't end up creating
        // two singletons.
        synchronized (LOCK){
            if(instance == null) {
                WorkspaceManager manager = new WorkspaceManager();
                instance = manager;
                return manager;
            }
            return instance;
        }
    }

}
