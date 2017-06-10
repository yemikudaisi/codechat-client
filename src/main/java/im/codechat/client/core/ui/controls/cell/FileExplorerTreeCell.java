package im.codechat.client.core.ui.controls.cell;

import im.codechat.client.core.xmpp.extensions.codechat.CodeChatFile;
import javafx.scene.control.Label;
import javafx.scene.control.TreeCell;
import javafx.scene.layout.HBox;

/**
 * The description for FileExplorerTreeCell class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/1/2017
 */
public class FileExplorerTreeCell extends TreeCell<CodeChatFile> {
    //private final FileExplorerFileContextMenu contextMenu;
    @Override
    public void updateItem(CodeChatFile file, boolean empty) {
        super.updateItem(file, empty);

        setText(null);
        setGraphic(null);

        if( file != null ){
            HBox hbox = new HBox();
            Label name = new Label();
            name.setText(file.getName());
            hbox.getChildren().add(name);
            setGraphic(hbox);
        }

    }
}
