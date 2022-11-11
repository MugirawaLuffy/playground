package de.hsbo.observer;

import de.hsbo.Main;
import de.hsbo.observer.events.BaseEvent;
import de.hsbo.observer.events.IEventHandler;
import de.hsbo.observer.events.StringEvent;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ConnectedDeviceObserver<T extends BaseEvent> implements IObserver{
    private static int dId = 0;
    @Getter
    String name = "device";
    int id;
    IEventHandler<T> handler;
    List<Integer> codes;
    public void update() {
        try{
            T ev = (T)MessagePumpObservable.getInstance().getFirstEvent();

            for (Integer code: codes) {
                if(code == ev.getEventCode()) {
                    onSpokenTo(ev);
                    return;
                }
            }
        }catch(ClassCastException ex) {
            MessagePumpObservable.getInstance().addEventAndNotify(new StringEvent(Main.ERROR_EVENT_CODE, "Connected device [" + this.name + "] could not work with code in pump"));
        }
    }
    private void onSpokenTo(BaseEvent ev) {
        if(handler == null) {
            MessagePumpObservable.getInstance().addEventAndNotify(new StringEvent(Main.ERROR_EVENT_CODE, "Connected device [" + this.name + "] has no attached handler"));
            return;
        }
        handler.handle((T)ev);
    }

    //------------------------------------------------ CREATTION ----------------------------------------
    private ConnectedDeviceObserver(DeviceBuilder<T> builder) {
        this.handler = builder.handler;
        this.codes = builder.codes;
        this.id = dId;
        dId++;
        if("".equals(builder.name)) {
            this.name = "device_" + this.id;
        }else {
            this.name = builder.name;
        }
        for (int code = 0; code<codes.size(); code++) {
            Codes.getInstance().addCode(this.name+"#"+code, codes.get(code));
        }
        MessagePumpObservable.getInstance().addEvent(new StringEvent(Codes.getInstance().getByName("Logger#0"), "Device [" + this.name + "] connected", this) );
    }

    public static class DeviceBuilder<T extends BaseEvent> {
        ArrayList<Integer> codes;
        IEventHandler<T> handler;
        String name = "";

        public DeviceBuilder() {
            codes = new ArrayList<>();
        }

        public DeviceBuilder<T> addSingleCode(int i) {
            this.codes.add(i);
            return this;
        }
        public DeviceBuilder<T> removeSingleCode(int i) {
            this.codes.remove(i);
            return this;
        }
        public DeviceBuilder<T> addRange(int start, int end) {
            for(int i = start; i < end; i++)
                this.codes.add(i);
            return this;
        }
        public DeviceBuilder<T> removeRange(int start, int end) {
            for(int i = start; i < end; i++)
                this.codes.remove(Integer.valueOf(i));
            return this;
        }
        public DeviceBuilder<T> name(String name) {
            this.name = name;
            return this;
        }
        public DeviceBuilder<T> setOnSpokenTo(IEventHandler<T> handler) {
            this.handler = handler;
            return this;
        }
        public ConnectedDeviceObserver<T> build() {
            return new ConnectedDeviceObserver<T>(this);
        }
    }
}
