package im.codechat.client.core.xmpp.extensions.codechat;

import im.codechat.client.core.xmpp.extensions.codechat.exceptions.DuplicateSessionException;
import im.codechat.client.core.xmpp.extensions.codechat.exceptions.SessionNotFoundException;
import rocks.xmpp.addr.Jid;

import java.util.HashMap;
import java.util.List;

/**
 * The description for ICodeChatManager class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */
public interface ICodeChatManager {

    /**
     * Searches for a session whose offer's instance has the specified key from
     * a session container determine by the search type
     *
     *
     * @param keyToFind The key to session to find
     * @param searchType The type of container to conduct the search on
     * @return The session with an offer that has the key
     * @throws SessionNotFoundException
     */
    CodeChatSession findSession(String keyToFind, CodeChatSessionContainers searchType) throws SessionNotFoundException;

    /**
     * Searches for an session whose offer's instance has the specified key from
     * a session container determine by the search type
     *
     *
     * @param keyToFind The key to match
     * @param containerToSearch The session container to search in
     * @return The session that contains an offer that matches the key
     * @throws SessionNotFoundException
     */
    CodeChatSession findSession(String keyToFind, List<CodeChatSession> containerToSearch) throws SessionNotFoundException;

    /**
     * Initiates a session when a CodeChatOffer is to be sent to acontact
     * @param to
     * @param offer
     * @param rootPath
     * @throws DuplicateSessionException
     */
    void initiateSession(Jid to, CodeChatOffer offer, String rootPath) throws DuplicateSessionException;

    /**
     * Remove a session from the pending session container
     * and add it to approved guest session container if the CodeChat client is the Host,
     * otherwise simply add the session it to approved hosts session container
     *
     * @param keyToApprove The key of the session to approve
     * @throws SessionNotFoundException
     */
    void approveGuestSession(String keyToApprove, CodeChatSessionApprovals approvalType) throws SessionNotFoundException;

    /**
     * Approve an offer from a host and create
     * a response of approval
     *
     * @return
     */
    CodeChatOfferResponse approveHostOffer(CodeChatOffer offerToApprove, Jid from);

    /**
     * Deny an offer from a host and create
     * a response for denial
     *
     * @return
     */
    CodeChatOfferResponse denyHostOffer(CodeChatOffer offerToDeny);

    /**
     * Add a CodeChatSession from a session container based on the container specified type.
     * It ensures that duplicate offers do not exist in a container
     * session prior to adding a new session to a container.
     *
     * @param session The session to add
     * @param containerType The type of container to add the container to
     * @throws DuplicateSessionException
     */
    void addSession(CodeChatSession session, CodeChatSessionContainers containerType) throws DuplicateSessionException;

    /**
     * Removes a CodeChatSession from a session container based on the container type specified.
     * It is responsible for ensuring that duplicate offers does not exist in a container
     * session while adding new session to a container.
     *
     * @param key The session key
     * @param containerType The type of container to add the container to
     * @throws DuplicateSessionException
     */
    void removeSession(String key, CodeChatSessionContainers containerType) throws SessionNotFoundException;

    /**
     * Get container for pending sessions
     * that was send out by a host (originator)
     *
     * @return List containing pending host offers
     */
    List<CodeChatSession> getPendingGuestSessionContainer();


    /**
     * Get container for approved sessions sent out by
     * by a CodeChat (you) host that yielded a guest (other) approval
     *
     * @return List containing offers sent by host (you) approved by a guest (other)
     */
    List<CodeChatSession> getApprovedGuestSessionContainer();

    /**
     * Get container for host (other) offers session who response was approved
     * by the CodeChat (you) guest
     *
     * @return List containing offers sent by host (other) approved by a guest (you)
     */
    List<CodeChatSession> getApprovedHostSessionContainer();

    /**
     * Gets the list of root paths to session sessiond agains thier keys
     *
     * @return
     */
    HashMap<String, String> getSessionRootPaths();

    /**
     * Sets the list of session root paths against their keys
     *
     * @param sessionRootPaths The list of root paths
     */
    void setSessionRootPaths(HashMap<String, String> sessionRootPaths);

}
