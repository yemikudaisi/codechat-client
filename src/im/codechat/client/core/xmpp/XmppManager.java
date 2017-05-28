package im.codechat.client.core.xmpp;

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
public class XmppManager {
    private final String loginServer = "192.168.138.128";
    TcpConnectionConfiguration config;
    private XmppClient client;
    static XmppManager instance;

    public XmppManager() throws XmppException {
        this.config = TcpConnectionConfiguration.builder()
                .hostname(loginServer)
                .port(5222)
                .secure(false)
                .build();
        initClient();
    }

    private void initClient() throws XmppException {

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

    public static XmppManager instance() throws XmppException {
        if(instance == null) {
            instance = new XmppManager();
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
        this.setClient(null);
    }

    public XmppClient getClient() {
        // TODO check if client is null
        if (client == null)
            try {
                initClient();
            } catch (XmppException e) {
                // TODO catch
                e.printStackTrace();
            }
        return client;
    }

    public void setClient(XmppClient client) {
        this.client = client;
    }
}
