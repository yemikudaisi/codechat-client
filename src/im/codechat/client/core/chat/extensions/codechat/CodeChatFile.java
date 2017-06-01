package im.codechat.client.core.chat.extensions.codechat;

import com.sun.org.apache.bcel.internal.classfile.Code;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

/**
 * The description for CodeChatFile class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/1/2017
 */
public class CodeChatFile {
    private SimpleStringProperty name;
    private SimpleBooleanProperty isDirectory;
    private SimpleStringProperty relativePath;
    private CodeChatFile parent;
    private List<CodeChatFile> children;
}
