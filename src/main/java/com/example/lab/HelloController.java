package com.example.lab;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.canvas.Canvas;

public class HelloController {
    @FXML private Label serverStateLabel;
    @FXML private Label serverTimeLabel;

    @FXML private Label comp1Label;
    @FXML private Canvas comp1Canvas;

    @FXML private Spinner<Integer> clipDelaySpinner;
    @FXML private Canvas clipCanvas;
    @FXML private Label comp2StatusLabel;

    @FXML private Canvas pulseCanvas;
    @FXML private Label comp3StatusLabel;

    private TimeServer server;
    private ComponentOne componentOne;
    private ComponentTwo componentTwo;
    private ComponentThree componentThree;

    @FXML
    private void initialize() {
        server = new TimeServer(); // по умолчанию неактивен

        clipDelaySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 5, 1));
        clipDelaySpinner.setEditable(true);

        componentOne = new ComponentOne(server, comp1Label, serverTimeLabel, comp1Canvas);
        componentTwo = new ComponentTwo(server, clipCanvas, comp2StatusLabel);
        componentThree = new ComponentThree(server, pulseCanvas, comp3StatusLabel);

        server.attach(componentOne);
        server.attach(componentTwo);
        server.attach(componentThree);

        refreshServerStateLabel();
        server.setState(0);
    }

    public void shutdown() {
        if (server != null) {
            server.shutdown();
        }
    }

    private void refreshServerStateLabel() {
        serverStateLabel.setText(server.isActive() ? "активен" : "неактивен");
    }

    @FXML
    private void onStartServer() {
        server.start();
        refreshServerStateLabel();
    }

    @FXML
    private void onStopServer() {
        server.stop();
        refreshServerStateLabel();
    }

    @FXML
    private void onResetServer() {
        server.reset();
        componentTwo.reset();
        componentThree.reset();
        refreshServerStateLabel();
    }

    @FXML
    private void onStartClip() {
        Integer n = clipDelaySpinner.getValue();
        componentTwo.startAfter(n == null ? 0 : n);
    }

    @FXML
    private void onStopClip() {
        componentTwo.stop();
    }
}