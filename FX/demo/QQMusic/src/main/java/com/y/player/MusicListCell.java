package com.y.player;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.io.File;

public class MusicListCell extends ListCell<File> {
    private final Label label;
    private final BorderPane pane;

    public MusicListCell() {
        pane = new BorderPane();
        label = new Label();
        //设置最大宽度 防止出现横向滚动条
        label.setMaxWidth(240);
        BorderPane.setAlignment(label, Pos.CENTER_LEFT);
        Button button = new Button();
        button.getStyleClass().add("remove-btn");
        button.setGraphic(new Region());
        button.setOnAction(event -> {
            getListView().getItems().remove(getItem());
        });
        pane.setCenter(label);
        pane.setRight(button);
    }

    @Override
    protected void updateItem(File file, boolean b) {
        super.updateItem(file, b);
        if (file == null || b) {
            setGraphic(null);
            label.setText("");
        } else {
            String name = file.getName();
            label.setText(name.substring(0, name.length() - 4));
            setGraphic(pane);
        }

    }
}
