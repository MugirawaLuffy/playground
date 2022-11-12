package de.hsbo;
import de.hsbo.graphix.Window;
import de.hsbo.observer.Codes;
import de.hsbo.observer.ConnectedDeviceObserver;
import de.hsbo.observer.MessagePumpObservable;
import de.hsbo.observer.events.ConsoleColors;
import de.hsbo.observer.events.StringEvent;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Main {
    public static final int ERROR_EVENT_CODE = 1111;

    public static void main(String[] args) {
        ConnectedDeviceObserver<StringEvent> logger = new ConnectedDeviceObserver.DeviceBuilder<StringEvent>()
                .name("Logger")
                .addSingleCode(1111)    //Logger event code
                .addSingleCode(1112)    //Error event code
                .setOnSpokenTo(
                        event -> {
                            String buf = "";
                            if(event.getEventCode() == 1111) {
                                buf = ConsoleColors.ANSI_BRIGHT_YELLOW + "[" + Timestamp.from(ZonedDateTime.now().toInstant()).toString() + "]";
                                if(event.getSender() != null)
                                    buf+= "-[sent by device: " + event.getSender().getName() + "]";

                                buf += ConsoleColors.ANSI_YELLOW + " "+event.getPayload() + ConsoleColors.ANSI_RESET + "\n";
                            } else if(event.getEventCode() == 1112) {
                                buf = ConsoleColors.ANSI_BRIGHT_RED + "[" + Timestamp.from(ZonedDateTime.now().toInstant()).toString() + "]";
                                if(event.getSender() != null)
                                    buf+= "-[sent by device: " + event.getSender().getName() + "]";
                                buf += ConsoleColors.ANSI_RED + " "+event.getPayload() + ConsoleColors.ANSI_RESET + "\n";
                            }
                            System.out.print(buf);
                        }
                )
                .build();
        MessagePumpObservable.getInstance().addObserver(logger);
        MessagePumpObservable.getInstance().addEventAndNotify(new StringEvent(Codes.getInstance().getByName("Logger#0"), "Hallo Welt"));    //log
        MessagePumpObservable.getInstance().addEventAndNotify(new StringEvent(Codes.getInstance().getByName("Logger#1"), "Hallo Welt"));    //error
        Window w = new Window();
        w.run();
    }
}