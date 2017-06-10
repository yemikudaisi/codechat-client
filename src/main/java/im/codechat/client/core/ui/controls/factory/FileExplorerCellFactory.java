package im.codechat.client.core.ui.controls.factory;

import im.codechat.client.core.ui.controls.cell.FileExplorerTreeCell;
import im.codechat.client.core.xmpp.extensions.codechat.CodeChatFile;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.util.Callback;

/**
 * The description for FileExplorerCellFactory class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/1/2017
 */
public class FileExplorerCellFactory implements Callback<TreeView<CodeChatFile>,TreeCell<CodeChatFile>> {
    @Override
    public TreeCell<CodeChatFile> call(TreeView<CodeChatFile> param) {
        ContextMenu menu = new ContextMenu();
        menu.getItems().add(new MenuItem("Test"));
        return new FileExplorerTreeCell();
    }
}
