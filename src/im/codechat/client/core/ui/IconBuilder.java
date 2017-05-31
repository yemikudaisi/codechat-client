package im.codechat.client.core.ui;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * The description for IconBuilder class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/30/2017
 */
public abstract class IconBuilder<T,I, B extends IconBuilder<T,I,B>> implements IIconViewBuilder<T>{
    String sizeExpression;
    Paint color;
    I glyphIcon;

    public IconBuilder(){
        this.sizeExpression = "14px";
        color = Color.BLACK;
    }

    public abstract B icon(I icon);

    public static <B> B get(Class<B> c){
        try {
            return c.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
