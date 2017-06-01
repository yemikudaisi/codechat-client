package im.codechat.client.core.xmpp.extensions.codechat.exceptions;

/**
 * The description for SessionNotFoundException class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */
public class SessionNotFoundException extends Exception {
    public SessionNotFoundException(){
        super("The store with supplied key was not found in the store container.");
    }
}
