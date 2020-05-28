package com.soundhive.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.soundhive.Router;
import com.soundhive.authentication.SessionHandler;
import com.soundhive.response.Response;
import com.soundhive.stats.Keyframe;
import com.soundhive.stats.Stats;
import com.soundhive.stats.StatsHandler;
import com.soundhive.stats.StatsService;
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
        System.out.println(this.getSession());
        statsService = new StatsService(this.getSession(), this.cbSpan.valueProperty(), StatsHandler.Scope.USER);
        this.acStats.setTitle("Views from last week");
        statsService.setOnSucceeded(e -> {
            Response<Stats> stats = (Response<Stats>) e.getSource().getValue();
            if (stats.getStatus() == Response.Status.SUCCESS){
                this.acStats.getData().add(generateListenSeries(stats.getContent().getKeyframes()));
            }
            else {
                System.err.println("Couldn't fetch stats : " +  stats.getStatus().toString());
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
