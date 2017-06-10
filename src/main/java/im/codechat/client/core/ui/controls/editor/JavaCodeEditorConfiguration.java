package im.codechat.client.core.ui.controls.editor;

import javafx.concurrent.Task;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The description for JavaCodeEditorConfiguration class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/10/2017
 */
public class JavaCodeEditorConfiguration extends CodeEditorConfiguration{

    private static final String[] KEYWORDS = new String[] {
            "abstract", "assert", "boolean", "break", "byte",
            "case", "catch", "char", "class", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    public JavaCodeEditorConfiguration(){
    }

    public Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = getCodeEditor().getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return computeHighlighting(text);
            }
        };
        getExecutor().execute(task);
        return task;
    }

    public void applyHighlighting(StyleSpans<Collection<String>> highlighting) {

        getCodeEditor().setStyleSpans(0, highlighting);
    }

    @Override
    public String getCss() {
        return ".keyword {\n" +
                "    -fx-fill: purple;\n" +
                "    -fx-font-weight: bold;\n" +
                "}\n" +
                ".semicolon {\n" +
                "    -fx-font-weight: bold;\n" +
                "}\n" +
                ".paren {\n" +
                "    -fx-fill: firebrick;\n" +
                "    -fx-font-weight: bold;\n" +
                "}\n" +
                ".bracket {\n" +
                "    -fx-fill: darkgreen;\n" +
                "    -fx-font-weight: bold;\n" +
                "}\n" +
                ".brace {\n" +
                "    -fx-fill: teal;\n" +
                "    -fx-font-weight: bold;\n" +
                "}\n" +
                ".string {\n" +
                "    -fx-fill: blue;\n" +
                "}\n" +
                "\n" +
                ".comment {\n" +
                "\t-fx-fill: cadetblue;\n" +
                "}\n" +
                "\n" +
                ".paragraph-box:has-caret {\n" +
                "    -fx-background-color: #f2f9fc;\n" +
                "}\n";
    }

    public static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
