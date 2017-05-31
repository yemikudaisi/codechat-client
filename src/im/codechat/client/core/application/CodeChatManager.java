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
    // Neccessary for keeping track absolute paths of base folder
    // for CodeChat session
    // Only neccessary for Hosts
    private HashMap<String, String> codeChatProjectPaths;

    public CodeChatManager(){
        pendingGuestSessionContainer = new ArrayList<>();
        approvedGuestSessionContainer = new ArrayList<>();
        approvedHostSessionContainer = new ArrayList<>();
        setCodeChatProjectPaths(new HashMap<>());
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
            addOfferStore(storeToApprove, CodeChatSessionContainers.APPROVED_GUESTS);
        } catch (DuplicateSessionException e) {
            // If it has previously been added, just ignore
        }
    }

    public CodeChatOfferResponse approveHostOffer(CodeChatOffer offerToApprove, Jid from){
        try {
            addOfferStore(new CodeChatSession(offerToApprove,from), CodeChatSessionContainers.APPROVED_GUESTS);
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
    public void addOfferStore(CodeChatSession offerStore, CodeChatSessionContainers containerType) throws DuplicateSessionException {
       switch (containerType){
           case PENDING:
               try {
                   findSession(offerStore.getKey(), CodeChatSessionContainers.PENDING);
                   throw new DuplicateSessionException();
               } catch (SessionNotFoundException e) {
                   getPendingGuestSessionContainer().add(offerStore);
               }
               break;
           case APPROVED_HOSTS:
               try {
                   findSession(offerStore.getKey(), CodeChatSessionContainers.APPROVED_HOSTS);
                   throw new DuplicateSessionException();
               } catch (SessionNotFoundException e) {
                   getPendingGuestSessionContainer().add(offerStore);
               }
               break;
           case APPROVED_GUESTS:
               try {
                   findSession(offerStore.getKey(), CodeChatSessionContainers.APPROVED_GUESTS);
                   throw new DuplicateSessionException();
               } catch (SessionNotFoundException e) {
                   getPendingGuestSessionContainer().add(offerStore);
               }
               break;
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
    public List<CodeChatSession> getApprovedGuestOfferStoreContainer() {
        return approvedGuestSessionContainer;
    }

    /**
     * {@inheritDoc}
     */
    public List<CodeChatSession> getApprovedHostOfferStoreContainer() {
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

    public HashMap<String, String> getCodeChatProjectPaths() {
        return codeChatProjectPaths;
    }

    public void setCodeChatProjectPaths(HashMap<String, String> codeChatProjectPaths) {
        this.codeChatProjectPaths = codeChatProjectPaths;
    }
}
