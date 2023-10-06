import com.y.customIcon.CustomIconHandler;
import org.kordamp.ikonli.IkonHandler;

open module FXIcon {
    requires javafx.controls;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.bootstrapicons;
    requires org.kordamp.ikonli.antdesignicons;

    provides IkonHandler with CustomIconHandler;
}