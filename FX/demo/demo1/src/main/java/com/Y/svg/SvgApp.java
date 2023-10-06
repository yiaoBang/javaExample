package com.Y.svg;

import javafx.application.Application;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;


public class SvgApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Region region = new Region();
        SVGPath svgPath = new SVGPath();
        svgPath.setContent("此处写svg路径");
        region.setShape(svgPath);
        region.setPrefSize(100, 100);
        region.setStyle("-fx-background-color: red");

    }
}
