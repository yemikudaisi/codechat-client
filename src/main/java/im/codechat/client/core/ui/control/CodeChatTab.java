package im.codechat.client.core.ui.control;


import im.codechat.client.core.xmpp.extensions.codechat.CodeChatFile;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatSession;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatState;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.flowless.VirtualizedScrollPane;

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
    JavaCodeArea codeArea;
    TreeView<CodeChatFile> fileExplorer;

    MenuBar menuBar;
    Menu fileMenu;
    Menu editMenu;
    Menu helpMenu;
    MenuItem newFileMenuItem;
    MenuItem saveFileMenuItem;
    MenuItem closeFileMenuItem;

    public CodeChatTab(CodeChatSession session){
        this("New CodeChatTab",session, CodeChatState.MASTER);
    }

    public CodeChatTab(String title, CodeChatSession session, CodeChatState state){
        super(title);
        this.session = session;
        this.state = state;
        codeArea = new JavaCodeArea();
        initializeUI();
    }

    public void initializeUI(){
        initializeMenu();
        initializeFileExplorer();
        initializeLayout();
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

    private void initializeFileExplorer() {

        TreeItem<CodeChatFile> root = new TreeItem<CodeChatFile>(new CodeChatFile("/"));
        root.setExpanded(true);
        fileExplorer = new TreeView<>(root);
        fileExplorer.setPrefWidth(250);
        VBox.setVgrow(fileExplorer, Priority.ALWAYS);

        TreeItem<CodeChatFile> a = new TreeItem<>(new CodeChatFile("Main.java"));
        TreeItem<CodeChatFile> b = new TreeItem<>(new CodeChatFile("Other.java"));
        TreeItem<CodeChatFile> c = new TreeItem<>(new CodeChatFile("CodeInsane.java"));
        ArrayList<TreeItem<CodeChatFile>> aL = new ArrayList<>();
        aL.add(a);
        aL.add(b);
        aL.add(c);

        ObservableList<TreeItem<CodeChatFile>> oL = FXCollections.observableList(aL);
        fileExplorer.getRoot().getChildren().addAll(oL);
    }

    private void initializeLayout() {
        BorderPane pane = new BorderPane();
        pane.setTop(menuBar);

        ScrollPane scroll = new ScrollPane();
        HBox.setHgrow(scroll, Priority.ALWAYS);
        VBox.setVgrow(scroll, Priority.ALWAYS);
        pane.setLeft(scroll);
        scroll.setContent(fileExplorer);


        VBox.setVgrow(codeArea, Priority.ALWAYS);
        HBox.setHgrow(codeArea, Priority.ALWAYS);
        pane.setCenter(new VirtualizedScrollPane<>(codeArea));

        this.setContent(pane);
    }

}
