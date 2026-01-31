package com.example.lab;

import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class TimeServer implements Subject {
    // по методичке
    private final List<IObserver> observers = new ArrayList<IObserver>();

    private int timeState = 0;
    private final Timer timer;
    private final TimerTask task;

    private final long delayMs;
    private final long periodMs;

    private volatile boolean active = false;

    public TimeServer() {
        this(0, 1000);
    }

    public TimeServer(long delayMs, long periodMs) {
        this.delayMs = delayMs;
        this.periodMs = periodMs;

        // daemon=true, чтобы таймер не мешал закрытию приложения
        this.timer = new Timer(true);
        this.task = new TimerTask() {
            @Override
            public void run() {
                tick();
            }
        };

        timer.schedule(task, this.delayMs, this.periodMs);
    }

    public boolean isActive() {
        return active;
    }

    public void start() {
        active = true;
    }

    public void stop() {
        active = false;
    }

    public void reset() {
        setState(0);
    }

    public int getState() {
        return timeState;
    }

    public void setState(int time) {
        timeState = Math.max(0, time);
        notifyAllObservers();
    }

    public void shutdown() {
        active = false;
        timer.cancel();
    }

    private void tick() {
        if (!active) {
            return;
        }
        timeState++;
        notifyAllObservers();
    }

    @Override
    public void attach(IObserver observer) {
        synchronized (observers) {
            observers.add(observer);
        }
    }

    @Override
    public void detach(IObserver observer) {
        synchronized (observers) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyAllObservers() {
        final List<IObserver> snapshot;
        synchronized (observers) {
            snapshot = new ArrayList<>(observers);
        }

        Platform.runLater(() -> {
            for (IObserver observer : snapshot) {
                observer.update();
            }
        });
    }
}

