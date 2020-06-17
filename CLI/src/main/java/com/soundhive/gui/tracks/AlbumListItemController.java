package com.soundhive.gui.tracks;

import com.jfoenix.controls.JFXListView;
import com.soundhive.core.tracks.Album;
import com.soundhive.core.tracks.Track;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;

import java.util.function.Consumer;

public class AlbumListItemController {
    private Album album;
    private Consumer<String> messageLogger;
    private Consumer<Throwable> errorLogger;

    @FXML
    private Label lbTitle;

    @FXML
    private Label lbListens;

    @FXML
    private ImageView ivArt;

    @FXML
    private JFXListView<AnchorPane> lvTracks;

    @FXML
    private AnchorPane albumPane;

    @FXML
    private void initialize(){
    }


    public void setAlbumsAndLoggersAndStart(final Album album,
                                          final Consumer<String> messageLogger,
                                          final Consumer<Throwable> errorLogger) {
        this.album = album;
        this.messageLogger = messageLogger;
        this.errorLogger = errorLogger;
        start();
    }

    private void start() {
        this.lbListens.setText("69");
        this.lbTitle.setText(this.album.getTitle());
        fillTracks();
    }

    private void fillTracks() {
        this.lvTracks.getItems().clear();
        if (this.album.getTracks().size() == 1) {
            this.lvTracks.setPrefHeight(0);


        } else {
            for (Track track :
                    album.getTracks()) {
                System.out.println(track.getTitle());
                Label lbName = new Label(track.getTitle());
                lbName.setTextFill(Paint.valueOf("white"));
                AnchorPane pane = new AnchorPane(lbName);
                lvTracks.getItems().add(pane);
            }
        }
        this.albumPane.setPrefHeight(lbTitle.getPrefHeight() + (album.getTracks().size() * 15 + 2) + 20);
        lvTracks.setPrefHeight(album.getTracks().size() * 15 + 2);

    }

}