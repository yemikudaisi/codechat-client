package im.codechat.client.core.ui.control.cell;

import im.codechat.client.core.xmpp.extensions.codechat.CodeChatFile;
import im.codechat.client.core.ui.control.FileExplorerFileContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.StringConverter;

/**
 * The description for FileExplorerFileTreeCell class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/1/2017
 */
public class FileExplorerFileTreeCell extends TextFieldTreeCell<CodeChatFile> {
    private final FileExplorerFileContextMenu contextMenu;

    public FileExplorerFileTreeCell(FileExplorerFileContextMenu contextMenu, StringConverter<CodeChatFile> converter) {
        super(converter);
        if (contextMenu == null) {
            throw new NullPointerException();
        }
        this.contextMenu = contextMenu;
        this.setOnContextMenuRequested(evt -> {
            prepareContextMenu(getTreeItem());
            evt.consume();
        });
    }

    private void prepareContextMenu(TreeItem<CodeChatFile> item) {
        MenuItem delete = contextMenu.getDelete();
        boolean root = item.getParent() == null;
        if (!root) {
            delete.setOnAction(evt -> {
                item.getParent().getChildren().remove(item);
                contextMenu.freeActionListeners();
            });
        }
        delete.setDisable(root);
        contextMenu.getAdd().setOnAction(evt -> {
            item.getChildren().add(new TreeItem<CodeChatFile>());
            contextMenu.freeActionListeners();
        });
    }

    @Override
    public void updateItem(CodeChatFile item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContextMenu("nocontext".equals(item) ? null : contextMenu.getContextMenu());
            setEditable(!"noedit".equals(item));
        }
    }
}
