package im.codechat.client.core.chat.extensions.codechat.exceptions;

/**
 * The description for OfferStoreNotFoundException class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */
public class OfferStoreNotFoundException extends Exception {
    public OfferStoreNotFoundException(){
        super("The store with supplied key was not found in the store container.");
    }
}