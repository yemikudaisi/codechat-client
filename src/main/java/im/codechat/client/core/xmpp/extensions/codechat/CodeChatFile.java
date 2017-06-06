package im.codechat.client.core.xmpp.extensions.codechat;

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
    private SimpleStringProperty extension;
    private CodeChatFile parent;
    private List<CodeChatFile> children;

    public CodeChatFile(String value){
        name = new SimpleStringProperty(value);
    }

    @Override
    public String toString(){
        return this.name.getValue();
    }

    public String getName() {
        return name.getValue();
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public boolean isDirectory() {

        return isDirectory.getValue();
    }

    public void setIsDirectory(boolean isDirectory) {

        this.isDirectory.setValue(isDirectory);
    }

    public String getRelativePath() {

        return relativePath.getValue();
    }

    public void setRelativePath(String relativePath) {

        this.relativePath.setValue(relativePath);
    }

    public String getExtension() {

        return extension.getValue();
    }

    public void setExtension(String extension) {
        this.extension.setValue(extension);
    }

    public CodeChatFile getParent() {
        return parent;
    }

    public void setParent(CodeChatFile parent) {
        this.parent = parent;
    }

    public List<CodeChatFile> getChildren() {
        return children;
    }

    public void setChildren(List<CodeChatFile> children) {
        this.children = children;
    }
}
