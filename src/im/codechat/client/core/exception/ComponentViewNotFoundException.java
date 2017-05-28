package im.codechat.client.core.exception;

/**
 * The description for ComponentViewNotFoundException class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/28/2017
 */
public class ComponentViewNotFoundException extends Exception {

    public  ComponentViewNotFoundException(){
        super("Component view was not found. Ensure naming convention is followed");
    }
}
