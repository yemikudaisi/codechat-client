package im.codechat.client.core.application;

import im.codechat.client.core.chat.extensions.codechat.*;
import im.codechat.client.core.chat.extensions.codechat.exceptions.DuplicateSessionException;
import im.codechat.client.core.chat.extensions.codechat.exceptions.SessionNotFoundException;
import rocks.xmpp.addr.Jid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * The description for CodeChatManager class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/31/2017
 */
public class CodeChatManager implements ICodeChatManager {
    private static CodeChatManager instance;
    private static final Object LOCK = new Object();

    private List<CodeChatSession> pendingGuestSessionContainer;
    private List<CodeChatSession> approvedGuestSessionContainer;
    private List<CodeChatSession> approvedHostSessionContainer;
    // Necessary for keeping track absolute paths of base folder
    // for CodeChat session
    // Only necessary for Hosts
    private HashMap<String, String> sessionRootPaths;

    public CodeChatManager(){
        pendingGuestSessionContainer = new ArrayList<>();
        approvedGuestSessionContainer = new ArrayList<>();
        approvedHostSessionContainer = new ArrayList<>();
        setSessionRootPaths(new HashMap<>());
    }

    public void initiateSession(Jid to, CodeChatOffer offer, String rootPath) throws DuplicateSessionException {
        sessionRootPaths.put(offer.getKey(), rootPath);
        addSession(new CodeChatSession(to,offer), CodeChatSessionContainers.PENDING);

    }

    /**
     * {@inheritDoc}
     */
    public CodeChatSession findSession(String keyToFind, CodeChatSessionContainers searchType) throws SessionNotFoundException {
        switch (searchType){
            case PENDING:
                return findSession(keyToFind, pendingGuestSessionContainer);
            case APPROVED_GUESTS:
                return findSession(keyToFind, approvedGuestSessionContainer);
            case APPROVED_HOSTS:
                return findSession(keyToFind, approvedHostSessionContainer);
            default:
                throw new SessionNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     */
    public CodeChatSession findSession(String keyToFind, List<CodeChatSession> containerToSearch) throws SessionNotFoundException {
        for(CodeChatSession s: containerToSearch){
            if(keyToFind.equals(s.getKey())){
                return s;
            }
        }
        throw new SessionNotFoundException();
    }

    /**
     * {@inheritDoc}
     */
    public void approveGuestSession(String keyToApprove, CodeChatSessionApprovals approvalType) throws SessionNotFoundException {
        CodeChatSession storeToApprove = findSession(keyToApprove, pendingGuestSessionContainer);

        // search for the store from the pending store container and remove it
        for(Iterator<CodeChatSession> iter = pendingGuestSessionContainer.listIterator(); iter.hasNext();){
            CodeChatSession toRemove = iter.next();
            if(storeToApprove.getOffer().equals(toRemove.getKey())){
                iter.remove();
            }
        }

        // Now add it to the approved store
        try {
            addSession(storeToApprove, CodeChatSessionContainers.APPROVED_GUESTS);
        } catch (DuplicateSessionException e) {
            // If it has previously been added, just ignore
        }
    }

    /**
     * {@inheritDoc}
     */
    public CodeChatOfferResponse approveHostOffer(CodeChatOffer offerToApprove, Jid from){
        try {
            addSession(new CodeChatSession(offerToApprove,from), CodeChatSessionContainers.APPROVED_GUESTS);
        } catch (DuplicateSessionException e) {
            // If it has previously been added, just ignore
        }
        return new CodeChatOfferResponse(true,offerToApprove);
    }


    public CodeChatOfferResponse denyHostOffer(CodeChatOffer offerToDeny){
        return new CodeChatOfferResponse(true,offerToDeny);
    }


    /**
     * {@inheritDoc}
     */
    public void addSession(CodeChatSession session, CodeChatSessionContainers containerType) throws DuplicateSessionException {
       switch (containerType){
           case PENDING:
               try {
                   findSession(session.getKey(), CodeChatSessionContainers.PENDING);
                   throw new DuplicateSessionException();
               } catch (SessionNotFoundException e) {
                   getPendingGuestSessionContainer().add(session);
               }
               break;
           case APPROVED_HOSTS:
               try {
                   findSession(session.getKey(), CodeChatSessionContainers.APPROVED_HOSTS);
                   throw new DuplicateSessionException();
               } catch (SessionNotFoundException e) {
                   getPendingGuestSessionContainer().add(session);
               }
               break;
           case APPROVED_GUESTS:
               try {
                   findSession(session.getKey(), CodeChatSessionContainers.APPROVED_GUESTS);
                   throw new DuplicateSessionException();
               } catch (SessionNotFoundException e) {
                   getPendingGuestSessionContainer().add(session);
               }
               break;
       }
    }

    /**
     * {@inheritDoc}
     */
    public void removeSession(String key, CodeChatSessionContainers containers) throws SessionNotFoundException {
        switch (containers){
            case PENDING:
                for(Iterator<CodeChatSession> iter = pendingGuestSessionContainer.listIterator(); iter.hasNext();){
                    CodeChatSession toRemove = iter.next();
                    if(key.equals(toRemove.getKey())){
                        iter.remove();
                        return;
                    }
                }
                throw new SessionNotFoundException();
            case APPROVED_HOSTS:
                for(Iterator<CodeChatSession> iter = approvedHostSessionContainer.listIterator(); iter.hasNext();){
                    CodeChatSession toRemove = iter.next();
                    if(key.equals(toRemove.getKey())){
                        iter.remove();
                        return;
                    }
                }
                throw new SessionNotFoundException();
            case APPROVED_GUESTS:
                for(Iterator<CodeChatSession> iter = approvedGuestSessionContainer.listIterator(); iter.hasNext();){
                    CodeChatSession toRemove = iter.next();
                    if(key.equals(toRemove.getKey())){
                        iter.remove();
                        return;
                    }
                }
                throw new SessionNotFoundException();
        }
    }

    /**
     * {@inheritDoc}
     */
    public List<CodeChatSession> getPendingGuestSessionContainer() {
        return pendingGuestSessionContainer;
    }

    /**
     * {@inheritDoc}
     */
    public List<CodeChatSession> getApprovedGuestSessionContainer() {
        return approvedGuestSessionContainer;
    }

    /**
     * {@inheritDoc}
     */
    public List<CodeChatSession> getApprovedHostSessionContainer() {
        return approvedHostSessionContainer;
    }

    public static CodeChatManager getInstance() {
        // Synchronize on LOCK to ensure that we don't end up creating
        // two singletons.
        synchronized (LOCK){
            if(instance == null) {
                CodeChatManager manager = new CodeChatManager();
                instance = manager;
                return manager;
            }
            return instance;
        }
    }

    /**
     * {@inheritDoc}
     */
    public HashMap<String, String> getSessionRootPaths() {
        return sessionRootPaths;
    }

    /**
     * {@inheritDoc}
     */
    public void setSessionRootPaths(HashMap<String, String> sessionRootPaths) {
        this.sessionRootPaths = sessionRootPaths;
    }
}
