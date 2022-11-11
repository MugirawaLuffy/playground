package de.hsbo.observer.events;

import de.hsbo.observer.ConnectedDeviceObserver;

public class IntegerEvent extends BaseEvent{
    int payload;
    public IntegerEvent(int evCode, int payload) {
        super(evCode);
        this.payload = payload;
    }
    public IntegerEvent(int evCode, int payload, ConnectedDeviceObserver<? extends BaseEvent> sender) {
        super(evCode, sender);
        this.payload = payload;
    }
}
