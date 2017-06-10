package im.codechat.client.core.application;

import im.codechat.client.core.xmpp.extensions.codechat.CodeChatOffer;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatSession;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatSessionApprovals;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatSessionContainers;
import im.codechat.client.core.xmpp.extensions.codechat.exceptions.DuplicateSessionException;
import im.codechat.client.core.xmpp.extensions.codechat.exceptions.SessionNotFoundException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rocks.xmpp.addr.Jid;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

/**
 * The description for CodeChatManagerTest class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/9/2017
 */
public class CodeChatManagerTest {

    CodeChatSession session;

    @Before
    public void initializeSession(){
        CodeChatOffer offer = new CodeChatOffer("", "kiuhiuhiuh232b");
        Jid jid = Jid.of("a@b");
        session = new CodeChatSession(jid,offer);
        CodeChatManager.getInstance().getPendingGuestSessionContainer().clear();
        CodeChatManager.getInstance().getApprovedGuestSessionContainer().clear();
        CodeChatManager.getInstance().getApprovedHostSessionContainer().clear();
    }

    @Test(expected = SessionNotFoundException.class)
    public void findingNonExistingSessionShouldThrowError() throws Exception{
        CodeChatManager.getInstance().findSession("false-key", CodeChatSessionContainers.APPROVED_HOSTS);
    }

    @Test
    public void addingOfSessionShouldIncreasePendingContainerSize() throws DuplicateSessionException {
        initializeSession();
        CodeChatManager.getInstance().addSession(session, CodeChatSessionContainers.PENDING);
        String msg = "Expected 1, got: ";
        assertEquals( msg+CodeChatManager.getInstance().getPendingGuestSessionContainer().size(), 1, CodeChatManager.getInstance().getPendingGuestSessionContainer().size());
    }

    @Test
    public void addingOfSessionShouldIncreaseGuestContainerSize() throws DuplicateSessionException {
        initializeSession();
        CodeChatManager.getInstance().addSession(session, CodeChatSessionContainers.APPROVED_GUESTS);
        String msg = "Expected 1, got: ";
        assertEquals( msg+CodeChatManager.getInstance().getApprovedGuestSessionContainer().size(), 1, CodeChatManager.getInstance().getApprovedGuestSessionContainer().size());
    }

    @Test
    public void addingOfSessionShouldIncreaseHostContainerSize() throws DuplicateSessionException {
        initializeSession();
        CodeChatManager.getInstance().addSession(session, CodeChatSessionContainers.APPROVED_HOSTS);
        String msg = "Expected 1, got: ";
        assertEquals( msg+CodeChatManager.getInstance().getApprovedHostSessionContainer().size(), 1, CodeChatManager.getInstance().getApprovedHostSessionContainer().size());
    }

    @Test
    public void approvingOfGuestSessionShouldDecreasePendingSize() throws DuplicateSessionException, SessionNotFoundException {
        initializeSession();
        CodeChatManager.getInstance().addSession(session, CodeChatSessionContainers.PENDING);
        CodeChatManager.getInstance().approveGuestSession(session.getKey(), CodeChatSessionApprovals.GUEST);
        assertEquals( "The quest session container count should be 0", 1, CodeChatManager.getInstance().getPendingGuestSessionContainer().size());
    }

    @Test(expected = DuplicateSessionException.class)
    public void duplicateKeyShouldThrowException() throws Exception{
        CodeChatManager.getInstance().addSession(session, CodeChatSessionContainers.PENDING);
        CodeChatManager.getInstance().addSession(session, CodeChatSessionContainers.PENDING);
    }

    @After
    public void result(){
        System.out.println("Pending Count: "+CodeChatManager.getInstance().getPendingGuestSessionContainer().size());
    }
}
