package im.codechat.client.core.ui.controls.editor;

import javafx.concurrent.Task;
import org.fxmisc.richtext.model.StyleSpans;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * The description for CSharpCodeEditorConfiguration class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/10/2017
 */
public class CSharpCodeEditorConfiguration extends CodeEditorConfiguration{

    private static final String[] KEYWORDS = new String[] {
            "abstract","as","base","bool","break","byte","case","catch","char",
            "checked","class","const","continue","decimal","default","delegate","do",
            "double","else","enum","event","explicit","extern","false","finally",
            "fixed","float","for","foreach","goto","if","implicit","in","in","int",
            "interface","internal","is","lock","long","namespace","new","null","object",
            "operator","out","out","override","params","private","protected","public",
            "readonly","ref","return","sbyte","sealed","short","sizeof","stackalloc",
            "static","string","struct","switch","this","throw","true","try","typeof",
            "uint","ulong","unchecked","unsafe","ushort","using","using static","void",
            "volatile","while","add","alias","ascending","async","await","descending",
            "dynamic","from","get","global","group","into","join","let","orderby",
            "partial","remove","select","set","value","var","when","yield",
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


    public Task<StyleSpans<Collection<String>>> computeHighlightingAsync() {
        String text = getCodeEditor().getText();
        Task<StyleSpans<Collection<String>>> task = new Task<StyleSpans<Collection<String>>>() {
            @Override
            protected StyleSpans<Collection<String>> call() throws Exception {
                return JavaCodeEditorConfiguration.computeHighlighting(text);
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
                "    -fx-fill: #3400FF;\n" +
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
                "    -fx-fill: #A31515;\n" +
                "}\n" +
                "\n" +
                ".comment {\n" +
                "\t-fx-fill: #638000;\n" +
                "}\n" +
                "\n" +
                ".paragraph-box:has-caret {\n" +
                "    -fx-background-color: #f2f9fc;\n" +
                "}";
    }
}
