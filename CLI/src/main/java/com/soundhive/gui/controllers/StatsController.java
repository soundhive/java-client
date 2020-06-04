package com.soundhive.gui.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.soundhive.core.response.Response;
import com.soundhive.core.stats.Stats;
import com.soundhive.gui.stats.StatsService;
import com.soundhive.core.stats.Keyframe;
import com.soundhive.core.stats.StatsHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.List;

public class StatsController extends  Controller{
    private StatsService statsService;

    @FXML
    AreaChart<String, Number> acStats;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    JFXComboBox<StatsService.SpanOption> cbSpan;

    @FXML
    public void initialize() {


    }


    @Override
    protected void start() {
        populateSpans();
        populateChart();
    }

    private void populateSpans() {
        cbSpan.getItems().setAll(StatsService.SpanOption.values());
        cbSpan.setValue(StatsService.SpanOption.LAST_WEEK);

    }

    private void populateChart() {
        System.out.println(getContext().getSession());
        statsService = new StatsService(getContext().getSession(), this.cbSpan.valueProperty(), StatsHandler.Scope.USER);
        this.acStats.setTitle("Views from last week");
        statsService.setOnSucceeded(e -> {
            Response<Stats> stats = (Response<Stats>) e.getSource().getValue();
            switch (stats.getStatus()) {
                case SUCCESS:
                    this.acStats.getData().add(generateListenSeries(stats.getContent().getKeyframes()));

                case UNAUTHENTICATED:
                    getContext().getRouter().issueDialog("You were disconnected from your session. Please log in again.");
                    getContext().getRouter().goTo("Login", controller -> controller.setContextAndStart(getContext()));

                case CONNEXION_FAILED:
                    getContext().getRouter().issueDialog("The server is unreachable. Please check your internet connexion.");
                    getContext().getRouter().goTo("Login", controller -> controller.setContextAndStart(getContext()));

                case UNKNOWN_ERROR:
                    getContext().getRouter().issueDialog("An error occurred.");
            }
            if (getContext().Verbose()) {
                System.out.println(stats.getMessage());
            }

        });
        statsService.setOnFailed(e -> {
            e.getSource().getException().printStackTrace();

        });
        statsService.start();

    }

    XYChart.Series<String, Number> generateListenSeries(List<Keyframe> keyframes){
        XYChart.Series<String, Number> listenSeries = new XYChart.Series<>();
        listenSeries.setName("Listens");

        for (Keyframe frame :
                keyframes) {
            listenSeries.getData().add(new XYChart.Data<>(frame.getPeriod(), frame.getPlays()));
        }

        return listenSeries;
    }
}