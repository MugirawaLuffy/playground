package de.hsbo.observer.events;

import de.hsbo.observer.ConnectedDeviceObserver;
import lombok.Getter;

public class StringEvent extends BaseEvent{
    @Getter
    String payload;

    public StringEvent(int evCode, String payload) {
        super(evCode);
        this.payload = payload;
    }

    public StringEvent(int evCode, String payload, ConnectedDeviceObserver<? extends BaseEvent> sender) {
        super(evCode, sender);
        this.payload = payload;
    }
}
