package im.codechat.client.core.chat;

import im.codechat.client.ui.components.ChatComponent;

import java.util.HashMap;

/**
 * The description for ChatManager class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/27/2017
 */
public class ChatManager {

    private HashMap<String, ChatComponent> chatComponents;

    private static ChatManager instance;
    private static final Object LOCK = new Object();

    public ChatManager(){
        chatComponents = new HashMap<>();
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

    public static ChatManager getInstance() {
        // Synchronize on LOCK to ensure that we don't end up creating
        // two singletons.
        synchronized (LOCK){
            if(instance == null) {
                ChatManager manager = new ChatManager();
                instance = manager;
                return manager;
            }
            return instance;
        }

    }

}
