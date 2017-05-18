package im.codechat.client.application;

import im.codechat.client.xmpp.XMPPHandler;
import rocks.xmpp.core.session.XmppClient;

/**
 * The description for Globals class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/18/2017
 */
public class Globals {
    private static XMPPHandler xmppHandler;

    public static XMPPHandler getXmppHandler() {
        return xmppHandler;
    }

    public static void setXmppHandler(XMPPHandler xmppHandler) {
        Globals.xmppHandler = xmppHandler;
    }
}
