package im.codechat.client.xmpp;

import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.sasl.AuthenticationException;
import rocks.xmpp.core.session.*;
import rocks.xmpp.core.stanza.model.*;
import rocks.xmpp.addr.Jid;

/**
 *
 * Unified handler for XMPP functions
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 15/5/17
 */
public class XMPPHandler {
    private final String loginServer = "192.168.138.128";
    TcpConnectionConfiguration config;
    private XmppClient client;

    public XMPPHandler() throws XmppException {
        this.config = TcpConnectionConfiguration.builder()
                .hostname(loginServer)
                .port(5222)
                .secure(false)
                .build();
        setClient(XmppClient.create("xchoc-debian",this.config));
        getClient().connect();
    }

    public boolean login(String username, String password) throws XmppException {
        try {
            getClient().login(username,password);
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    static XMPPHandler instance;
    public static  XMPPHandler instance() throws XmppException {
        if(instance == null) {
            instance = new XMPPHandler();
        }
        return instance;
    }

    public void sendChat(String to, String txt){
        Message msg = new Message(Jid.of(to), Message.Type.CHAT);
        msg.setBody(txt);
        getClient().send(msg);
    }

    public void changePresence(Presence.Show status){
        if(status != null)
            getClient().send(new Presence(status));
        else
            getClient().send(new Presence());
    }

    public void dispose() throws XmppException {
        getClient().close();
    }

    public XmppClient getClient() {
        return client;
    }

    public void setClient(XmppClient client) {
        this.client = client;
    }
}
