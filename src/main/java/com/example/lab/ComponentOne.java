package com.example.lab;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ComponentOne implements IObserver {
    private final TimeServer subject;
    private final Label panelLabel;
    private final Label serverTimeLabel;
    private final Canvas canvas;

    public ComponentOne(TimeServer subject, Label panelLabel, Label serverTimeLabel, Canvas canvas) {
        this.subject = subject;
        this.panelLabel = panelLabel;
        this.serverTimeLabel = serverTimeLabel;
        this.canvas = canvas;
        update();
    }

    @Override
    public void update() {
        int t = subject.getState();
        String text = "прошло " + t + " c";
        panelLabel.setText(text);
        serverTimeLabel.setText(String.valueOf(t));

        // Простейшая визуализация на Canvas: мигающий кружок
        GraphicsContext g = canvas.getGraphicsContext2D();
        double w = canvas.getWidth();
        double h = canvas.getHeight();

        g.clearRect(0, 0, w, h);
        g.setFill(Color.web("#F2F2F2"));
        g.fillRect(0, 0, w, h);

        g.setFill((t % 2 == 0) ? Color.DODGERBLUE : Color.LIGHTBLUE);
        double r = 10;
        g.fillOval(10, (h - 2 * r) / 2.0, 2 * r, 2 * r);

        g.setFill(Color.GRAY);
        g.fillText(text, 35, h / 2.0 + 4);
    }
}

