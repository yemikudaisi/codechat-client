package im.codechat.client.core.ui;

import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import javafx.scene.paint.Paint;

/**
 * The description for MaterialDesignIconBuilder class
 *
 * @author Yemi Kudaisi
 * @version 1.0
 * @since 5/30/2017
 */
public class MaterialDesignIconBuilder extends IconBuilder<MaterialDesignIconView, MaterialDesignIcon,MaterialDesignIconBuilder> {

    MaterialDesignIconBuilder(){
        super();
        this.glyphIcon = MaterialDesignIcon.ACCOUNT;
    }

    public MaterialDesignIconBuilder size(String expression) {
        this.sizeExpression = expression;
        return this;
    }

    public MaterialDesignIconBuilder fill(Paint color) {
        this.color = color;
        return this;
    }

    public MaterialDesignIconBuilder icon(MaterialDesignIcon icon){
        this.glyphIcon = icon;
        return this;
    }

    @Override
    public MaterialDesignIconView build() {
        MaterialDesignIconView iconView = new MaterialDesignIconView(this.glyphIcon);
        iconView.setFill(color);
        iconView.setSize(sizeExpression);
        return iconView;
    }
}
