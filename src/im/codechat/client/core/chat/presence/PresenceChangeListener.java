package im.codechat.client.core.chat.presence;

import java.util.EventListener;

/**
 * The description for PresenceChangeListener class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/28/2017
 */
public interface PresenceChangeListener extends EventListener {

    void dispatchPresenceChange(PresenceChangeEvent e);
}
