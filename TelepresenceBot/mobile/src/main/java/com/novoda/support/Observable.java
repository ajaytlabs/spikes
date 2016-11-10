package com.novoda.support;

import java.util.ArrayList;

public abstract class Observable<T> {

    private boolean changed = false;
    private final ArrayList<Observer<T>> observers;

    public Observable() {
        observers = new ArrayList<>();
    }

    public synchronized void addObserver(Observer<T> o) {
        if (o == null) {
            throw new NullPointerException();
        }
        if (!observers.contains(o)) {
            observers.add(o);
        }
        start();
    }

    public synchronized void deleteObserver(Observer o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        notifyObservers(null);
    }

    public void notifyObservers(T arg) {
        Observer<T>[] arrLocal;

        synchronized (this) {
            if (!hasChanged()) {
                return;
            }

            arrLocal = observers.toArray(new Observer[observers.size()]);
            clearChanged();
        }

        for (int i = arrLocal.length - 1; i >= 0; i--) {
            arrLocal[i].update(arg);
        }
    }

    public synchronized void deleteObservers() {
        observers.clear();
    }

    protected synchronized void setChanged() {
        changed = true;
    }

    protected synchronized void clearChanged() {
        changed = false;
    }

    public synchronized boolean hasChanged() {
        return changed;
    }

    public synchronized int countObservers() {
        return observers.size();
    }

    public abstract void start();

}
