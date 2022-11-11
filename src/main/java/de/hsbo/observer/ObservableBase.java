package de.hsbo.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class ObservableBase {
    private List<IObserver> observers = new ArrayList<>();
    public void addObserver(IObserver obs) {
        this.observers.add(obs);
    }
    public void removeObserver(IObserver obs) {
        this.observers.remove(obs);
    }

    public void notifyObservers() {
        for(IObserver obs : this.observers)
            obs.update();

        onNotifyComplete();
    }
    public abstract void onNotifyComplete();

}
