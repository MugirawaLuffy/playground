package de.hsbo.observer;

import de.hsbo.Main;
import de.hsbo.observer.events.BaseEvent;
import de.hsbo.observer.events.ExitEvent;
import de.hsbo.observer.events.StringEvent;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MessagePumpObservable extends ObservableBase {
    //_____________________________SINGLETON_IMPLEMENTATION___________________________________
    private static MessagePumpObservable instance = null;
    private MessagePumpObservable() {
        this.events = new LinkedList<>();
    }
    public static MessagePumpObservable getInstance() {
        if(instance == null)
            instance = new MessagePumpObservable();     //TODO: impl. thread-safety

        return instance;
    }
    //-----------------------------------------------------------------------------------------------
    private Queue<BaseEvent> events;
    public void addEvent(BaseEvent event) {
        this.events.add(event);
    }

    public void addEventAndNotify(BaseEvent event) {
        this.events.add(event);
        this.notifyObservers();
    }
    public BaseEvent getFirstEvent() {
        if(events == null || events.isEmpty()) {
            return new StringEvent(Main.ERROR_EVENT_CODE, "no active element set");
        }

        BaseEvent evBuffer = events.poll();
        return evBuffer;
    }
    @Override
    public void onNotifyComplete() {
        if(events.contains(new ExitEvent()))
            System.exit(0);
        if(!events.isEmpty())
            this.notifyObservers();

    }
}
