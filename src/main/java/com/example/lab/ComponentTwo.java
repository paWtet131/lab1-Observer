package com.example.lab;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ComponentTwo implements IObserver {
    private final TimeServer subject;
    private final Canvas canvas;
    private final Label statusLabel;

    private boolean armed = false;
    private int targetTime = -1;

    public ComponentTwo(TimeServer subject, Canvas canvas, Label statusLabel) {
        this.subject = subject;
        this.canvas = canvas;
        this.statusLabel = statusLabel;
        off();
    }

    public void startAfter(int seconds) {
        int s = Math.max(0, seconds);
        this.targetTime = subject.getState() + s;
        this.armed = true;
        statusLabel.setText("ожидание: через " + s + " c");
        drawWaiting();
    }

    public void stop() {
        off();
    }

    public void reset() {
        off();
    }

    private void off() {
        this.armed = false;
        this.targetTime = -1;
        statusLabel.setText("остановлено");
        drawStopped();
    }

    @Override
    public void update() {
        if (!armed) {
            return;
        }
        if (subject.getState() >= targetTime) {
            // Одноразовый запуск: просто показываем "зелёный экран"
            armed = false;
            statusLabel.setText("запущено");
            drawGreenScreen();
        }
    }

    private void drawStopped() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.DARKGRAY);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setFill(Color.WHITE);
        g.fillText("клип: стоп", 10, 20);
    }

    private void drawWaiting() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.DARKGRAY);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setFill(Color.WHITE);
        g.fillText("ожидание...", 10, 20);
    }

    private void drawGreenScreen() {
        GraphicsContext g = canvas.getGraphicsContext2D();
        g.setFill(Color.LIMEGREEN);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        g.setFill(Color.BLACK);
        g.fillText("клип запущен", 10, 20);
    }
}

