package im.codechat.client.core.ui.controls.editor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringBufferInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.concurrent.ExecutorService;

/**
 * The description for CodeEditorConfiguration class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 6/10/2017
 */
public abstract class CodeEditorConfiguration implements ICodeEditorConfiguration{

    private ExecutorService executor;
    private CodeEditor editor;
    public ExecutorService getExecutor() {
        return executor;
    }

    public void setExecutor(ExecutorService executor) {
        this.executor = executor;
    }

    public CodeEditor getCodeEditor() {
        return editor;
    }

    public void setCodeEditor(CodeEditor editor) {
        this.editor = editor;
        //TODO Figure out how to add stylesheet dynamically.
        this.editor.getStylesheets().add("internal:"+System.nanoTime()+"stylesheet.css");
    }

    {
        URL.setURLStreamHandlerFactory(new StringURLStreamHandlerFactory());
    }

    private class StringURLConnection extends URLConnection {
        public StringURLConnection(URL url){
            super(url);
        }

        @Override public void connect() throws IOException {}

        @Override public InputStream getInputStream() throws IOException {
            return new StringBufferInputStream(getCss());
        }
    }

    private class StringURLStreamHandlerFactory implements URLStreamHandlerFactory {
        URLStreamHandler streamHandler = new URLStreamHandler(){
            @Override protected URLConnection openConnection(URL url) throws IOException {
                if (url.toString().toLowerCase().endsWith(".css")) {
                    return new StringURLConnection(url);
                }
                throw new FileNotFoundException();
            }
        };
        @Override public URLStreamHandler createURLStreamHandler(String protocol) {
            if ("internal".equals(protocol)) {
                return streamHandler;
            }
            return null;
        }
    }
}
