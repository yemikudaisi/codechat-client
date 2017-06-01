package im.codechat.client.core.ui.control;

import im.codechat.client.core.xmpp.extensions.codechat.CodeChatFile;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatSession;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatState;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

/**
 * The description for CodeChatTab class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/1/2017
 */
public class CodeChatTab extends Tab {

    CodeChatSession session;
    CodeChatState state;
    TreeView<CodeChatFile> fileExplorer;

    MenuBar menuBar;
    Menu fileMenu;
    Menu editMenu;
    Menu helpMenu;
    MenuItem newFileMenuItem;
    MenuItem saveFileMenuItem;
    MenuItem closeFileMenuItem;


    public CodeChatTab(CodeChatSession session){
        this(session, CodeChatState.MASTER);
    }
    public CodeChatTab(CodeChatSession session, CodeChatState state){
        this.session = session;
        initializeUI();
    }



    public void initializeUI(){
        initializeMenu();
        initializeFileExplorer();
    }

    private void initializeFileExplorer() {
        fileExplorer = new TreeView<CodeChatFile>();
    }

    private void initializeMenu() {
        menuBar = new MenuBar();
        fileMenu = new Menu();
        editMenu = new Menu();
        helpMenu = new Menu();
        newFileMenuItem = new MenuItem();
        saveFileMenuItem = new MenuItem();
        closeFileMenuItem = new MenuItem();
        
        fileMenu.getItems().add(newFileMenuItem);
        fileMenu.getItems().add(saveFileMenuItem);
        fileMenu.getItems().add(closeFileMenuItem);
        menuBar.getMenus().add(fileMenu);
        menuBar.getMenus().add(editMenu);
        menuBar.getMenus().add(helpMenu);
    }
}
