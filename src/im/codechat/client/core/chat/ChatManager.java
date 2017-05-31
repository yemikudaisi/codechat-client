package im.codechat.client.core.chat;

import im.codechat.client.core.chat.extensions.codechat.*;
import im.codechat.client.core.chat.presence.PresenceChangeEvent;
import im.codechat.client.core.chat.presence.PresenceChangeListener;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.sasl.AuthenticationException;
import rocks.xmpp.core.session.*;
import rocks.xmpp.core.stanza.model.*;
import rocks.xmpp.addr.Jid;
import rocks.xmpp.extensions.chatstates.ChatStateManager;

import javax.swing.event.EventListenerList;
import java.util.HashMap;

/**
 *
 * Unified handler for XMPP functions
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 15/5/17
 */
public class ChatManager {

    private static ChatManager instance;
    private static Object LOCK = new Object();

    private final String loginServer = "192.168.138.128";
    TcpConnectionConfiguration config;
    XmppSessionConfiguration sessionConfiguration;
    private XmppClient client;
    private String domain = "xchoc-debian";
    private Jid sessionJid;
    private HashMap<String, Presence> presences;
    private EventListenerList presenceChangeListeners = new EventListenerList();

    public ChatManager() throws XmppException {
        this.config = TcpConnectionConfiguration.builder()
                .hostname(loginServer)
                .port(5222)
                .secure(false)
                .build();

        // Configure extension
        XmppSessionConfiguration xmppConfig = XmppSessionConfiguration.builder()
                .extensions(Extension.of(CodeChatOffer.class))
                .extensions(Extension.of(CodeChatOfferResponse.class))
                .extensions(Extension.of(CodeChatFileOffer.class))
                .extensions(Extension.of(CodeChatFileOfferResponse.class))
                .extensions(Extension.of(CodeChatFileRequest.class))
                .build();
        client  = XmppClient.create(loginServer,xmppConfig,config);
        getClient().enableFeature(ChatStateManager.class);
        presences = new HashMap<>();
    }

    public boolean login(String username, String password) throws XmppException {
        try {
            Jid jid = Jid.of(username,domain,"codechat");
            getClient().connect(jid);
            setSessionJid(jid);
            getClient().login(username,password);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    public static ChatManager instance() throws XmppException {
        // Synchronize on LOCK to ensure that we don't end up creating
        // two singletons.
        synchronized (LOCK){
            if(instance == null) {
                ChatManager xmpp = new ChatManager();
                instance = xmpp;
                return xmpp;
            }
            return instance;
        }
    }

    public Message buildMessage(String to){
        Message msg = new Message(Jid.of(to), Message.Type.NORMAL);
        msg.setFrom(getSessionJid());
        return msg;
    }

    public Message buildMessage(String to, String txt){
        Message msg = buildMessage(to);
        msg.setBody(txt);
        return msg;
    }

    public void sendMessage(Message msg){
        getClient().send(msg);
    }

    public void changePresence(Presence.Show status){
        if(status != null) {
            getClient().send(new Presence(status));
        }
        else
            getClient().send(new Presence());
    }

    public void dispose() throws XmppException {
        getClient().close();
        this.setClient(null);
    }

    public XmppClient getClient() {
        // TODO check if client is null
        if (client == null)
            setClient(XmppClient.create(domain,this.config));

        return client;
    }

    public void setClient(XmppClient client) {
        this.client = client;
    }

    public static String getLocalDomainJid(Jid jid){
        return jid.getLocal()+"@"+jid.getDomain();
    }

    public Jid getSessionJid() {
        return sessionJid;
    }

    public void setSessionJid(Jid sessionJid) {
        this.sessionJid = sessionJid;
    }

    public HashMap<String, Presence> getPresences() {
        return presences;
    }

    public void setPresences(HashMap<String, Presence> presences) {
        this.presences = presences;
    }

    public void addPresence(Presence presence){
        if (getPresences().containsKey(getLocalDomainJid(presence.getFrom()))) {
            getPresences().replace(getLocalDomainJid(presence.getFrom()), getPresences().get(getLocalDomainJid(presence.getFrom())), presence);
        }
        getPresences().put(getLocalDomainJid(presence.getFrom()),presence);
        firePresenceChange(new PresenceChangeEvent());
    }

    public void addPresenceChangeListener(PresenceChangeListener listener)
    {
        presenceChangeListeners.add(PresenceChangeListener.class, listener);
    }
    public void removePresenceChangeListener(PresenceChangeListener listener)
    {
        presenceChangeListeners.remove(PresenceChangeListener.class, listener);
    }
    protected void firePresenceChange(PresenceChangeEvent presenceChangeEvent)
    {
        Object[] listeners = presenceChangeListeners.getListenerList();
        // loop through each listener and pass on the event if needed
        int numListeners = listeners.length;
        for (int i = 0; i<numListeners; i+=2)
        {
            if (listeners[i]==PresenceChangeListener.class)
            {
                // pass the event to the presence event dispatch method
                ((PresenceChangeListener)listeners[i+1]).dispatchPresenceChange(presenceChangeEvent);
            }
        }
    }
}
