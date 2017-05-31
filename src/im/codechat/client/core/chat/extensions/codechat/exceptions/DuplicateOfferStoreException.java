package im.codechat.client.core.chat.extensions.codechat.exceptions;

/**
 * The description for DuplicateOfferStoreException class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */
public class DuplicateOfferStoreException extends Exception {

    public DuplicateOfferStoreException(){
        super("A store with the same key already exists in the container");
    }
}
