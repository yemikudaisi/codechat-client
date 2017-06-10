package im.codechat.client.core.ui.controls.editor;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The description for CodeEditor class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/10/2017
 */
public class CodeEditor extends CodeArea {

    private CodeEditorConfiguration config;

    public CodeEditor(CodeEditorConfiguration config, String code){
        super();
        this.config = config;
        config.setExecutor(Executors.newSingleThreadExecutor());
        config.setCodeEditor(this);
        this.setParagraphGraphicFactory(LineNumberFactory.get(this));
        this.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved())) // XXX
                .successionEnds(Duration.ofMillis(500))
                .supplyTask(config::computeHighlightingAsync)
                .awaitLatest(this.richChanges())
                .filterMap(t -> {
                    if(t.isSuccess()) {
                        return Optional.of(t.get());
                    } else {
                        t.getFailure().printStackTrace();
                        return Optional.empty();
                    }
                })
                .subscribe(config::applyHighlighting);
        this.replaceText(0, 0, code);
    }
}
