package im.codechat.client.core.ui.controls;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * The description for FileExplorerFileContextMenu class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/1/2017
 */
public class FileExplorerFileContextMenu {
    private final ContextMenu contextMenu;
    private final MenuItem add;
    private final MenuItem delete;

    public FileExplorerFileContextMenu () {
        this.add = new MenuItem("add child");
        this.delete = new MenuItem("delete");
        this.contextMenu = new ContextMenu(add, delete);
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public MenuItem getAdd() {
        return add;
    }

    public MenuItem getDelete() {
        return delete;
    }

    /**
     * This method prevents memory leak by setting all actionListeners to null.
     */
    public void freeActionListeners() {
        this.add.setOnAction(null);
        this.delete.setOnAction(null);
    }
}
