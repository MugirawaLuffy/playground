package de.hsbo;
import de.hsbo.graphix.Window;
import de.hsbo.observer.ConnectedDeviceObserver;
import de.hsbo.observer.MessagePumpObservable;
import de.hsbo.observer.events.ConsoleColors;
import de.hsbo.observer.events.StringEvent;
import java.sql.Timestamp;
import java.time.ZonedDateTime;

public class Main {
    public static final int ERROR_EVENT_CODE = 1111;
    public static final int LOG_EVENT_CODE = 1112;

    public static void main(String[] args) {
        ConnectedDeviceObserver<StringEvent> logger = new ConnectedDeviceObserver.DeviceBuilder<StringEvent>()
                .name("Logger")
                .addSingleCode(Main.LOG_EVENT_CODE)
                .setOnSpokenTo(
                        event -> {
                            String buf = ConsoleColors.ANSI_BRIGHT_YELLOW + "[" + Timestamp.from(ZonedDateTime.now().toInstant()).toString() + "]";
                            if(event.getSender() != null)
                                buf+= "-[sent by device: " + event.getSender().getName() + "]";
                            buf += ConsoleColors.ANSI_YELLOW + " "+event.getPayload() + ConsoleColors.ANSI_RESET;
                            System.out.println(buf);
                        }
                )
                .build();
        MessagePumpObservable.getInstance().addObserver(logger);
        MessagePumpObservable.getInstance().addEventAndNotify(new StringEvent(LOG_EVENT_CODE, "Hallo Welt"));

        Window w = new Window();
        w.run();
    }
}