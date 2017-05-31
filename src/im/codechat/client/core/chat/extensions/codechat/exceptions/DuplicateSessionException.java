package im.codechat.client.core.chat.extensions.codechat.exceptions;

/**
 * The description for DuplicateSessionException class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */
public class DuplicateSessionException extends Exception {

    public DuplicateSessionException(){
        super("A store with the same key already exists in the store container");
    }
}
