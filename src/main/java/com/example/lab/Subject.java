package com.example.lab;

public interface Subject {
    // В методичке встречаются оба варианта названия; оставляем оба для соответствия.
    void notifyAllObservers(); // уведомить

    default void notifyAllObserver() { // уведомить (вариант из методички)
        notifyAllObservers();
    }

    void attach(IObserver obs); // добавить слушателя

    void detach(IObserver obs); // удалить слушателя
}

