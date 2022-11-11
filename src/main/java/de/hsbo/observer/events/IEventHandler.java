package de.hsbo.observer.events;

@FunctionalInterface
public interface IEventHandler <T extends BaseEvent>{
    public void handle(T event);
}
