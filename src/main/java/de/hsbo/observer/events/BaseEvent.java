package de.hsbo.observer.events;

import de.hsbo.observer.ConnectedDeviceObserver;
import lombok.Getter;

public class BaseEvent {
    @Getter
    int eventCode;
    @Getter
    ConnectedDeviceObserver<? extends BaseEvent> sender = null;
    BaseEvent(int evCode) {
        this.eventCode = evCode;
    }
    BaseEvent(int evCode, ConnectedDeviceObserver<? extends BaseEvent> sender) {
        this.eventCode = evCode;
        this.sender = sender;
    }

}
