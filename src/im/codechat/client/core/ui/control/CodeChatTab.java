package im.codechat.client.core.ui.control;


import im.codechat.client.core.xmpp.extensions.codechat.CodeChatFile;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatSession;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingNode;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import java.util.ArrayList;

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


    public CodeChatTab(String title){
        super(title);
        initializeUI();
    }

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
        initializeLayout();
    }

    private void initializeLayout() {
        BorderPane pane = new BorderPane();
        pane.setTop(menuBar);

        ScrollPane scroll = new ScrollPane();
        scroll.setContent(fileExplorer);
        HBox.setHgrow(scroll, Priority.ALWAYS);
        pane.setLeft(scroll);


        RSyntaxTextArea textArea = new RSyntaxTextArea(20, 60);
        textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
        textArea.setCodeFoldingEnabled(true);
        String text="";
        textArea.setAntiAliasingEnabled(true);
        RTextScrollPane sp = new RTextScrollPane(textArea);
        SwingNode sn = new SwingNode();
        VBox.setVgrow(sn, Priority.ALWAYS);
        HBox.setHgrow(sn, Priority.ALWAYS);
        sn.setContent(sp);
        pane.setCenter(sn);


        this.setContent(pane);
    }

    private void initializeFileExplorer() {

        TreeItem<CodeChatFile> root = new TreeItem<CodeChatFile>(new CodeChatFile("/"));
        //CodeChatFile root = new CodeChatFile("");
        fileExplorer = new TreeView<CodeChatFile>(root);
        fileExplorer.setPrefWidth(250);
        HBox.setHgrow(fileExplorer, Priority.ALWAYS);

        TreeItem<CodeChatFile> a = new TreeItem<CodeChatFile>(new CodeChatFile("Main.java"));
        TreeItem<CodeChatFile> b = new TreeItem<CodeChatFile>(new CodeChatFile("Other.java"));
        TreeItem<CodeChatFile> c = new TreeItem<CodeChatFile>(new CodeChatFile("CodeInsane.java"));
        ArrayList<TreeItem<CodeChatFile>> aL = new ArrayList<>();
        aL.add(a);
        aL.add(b);
        aL.add(c);

        ObservableList<TreeItem<CodeChatFile>> oL = FXCollections.observableList(aL);
        fileExplorer.getRoot().getChildren().addAll(oL);
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
