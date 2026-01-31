package com.example.lab;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class ComponentThree implements IObserver {
    private final TimeServer subject;
    private final Canvas canvas;
    private final Label statusLabel;

    private int lastPulseAt = -1;
    private Timeline timeline;

    public ComponentThree(TimeServer subject, Canvas canvas, Label statusLabel) {
        this.subject = subject;
        this.canvas = canvas;
        this.statusLabel = statusLabel;
        update();
    }

    public void reset() {
        lastPulseAt = -1;
        statusLabel.setText("ожидание (каждые 15 c)");
        stopTimeline();
        drawCircle(18);
    }

    @Override
    public void update() {
        int t = subject.getState();
        if (t == 0) {
            reset();
            return;
        }

        statusLabel.setText("ожидание (каждые 15 c), t=" + t);

        if (t % 15 == 0 && t != lastPulseAt) {
            lastPulseAt = t;
            pulse();
        }
    }

    private void pulse() {
        stopTimeline();

        // простой "пульс" радиуса: 18 -> 30 -> 18
        timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> drawCircle(18)),
                new KeyFrame(Duration.millis(150), e -> drawCircle(30)),
                new KeyFrame(Duration.millis(300), e -> drawCircle(18))
        );
        timeline.playFromStart();
    }

    private void stopTimeline() {
        if (timeline != null) {
            timeline.stop();
            timeline = null;
        }
    }

    private void drawCircle(double r) {
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        g.clearRect(0, 0, w, h);
        g.setFill(Color.web("#F2F2F2"));
        g.fillRect(0, 0, w, h);

        double cx = w / 2.0;
        double cy = h / 2.0;

        g.setFill(Color.ORANGE);
        g.fillOval(cx - r, cy - r, 2 * r, 2 * r);
        g.setStroke(Color.DARKORANGE);
        g.strokeOval(cx - r, cy - r, 2 * r, 2 * r);
    }
}

