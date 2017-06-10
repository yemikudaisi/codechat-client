package im.codechat.client.core.ui.controls.editor;

import javafx.concurrent.Task;
import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

/**
 * The description for ICodeEditorConfiguration class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/10/2017
 */
public interface ICodeEditorConfiguration {

    Task<StyleSpans<Collection<String>>> computeHighlightingAsync();

    void applyHighlighting(StyleSpans<Collection<String>> highlighting);

    ExecutorService getExecutor();

    void setExecutor(ExecutorService executor);

    CodeEditor getCodeEditor();

    void setCodeEditor(CodeEditor editor);

    String getCss();
}
