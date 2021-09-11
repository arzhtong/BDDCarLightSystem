package uk.ac.ucl.cs.comp0110;

import java.time.*;
import java.time.Instant.*;
public class SystemClock {

    private Clock clock = Clock.systemDefaultZone();
    private ZoneId zoneId = ZoneId.systemDefault();
    private Clock constantClock = Clock.fixed(Instant.ofEpochMilli(0), ZoneId.systemDefault());

    public void useFixedClockAt(LocalDateTime date){
        clock = Clock.fixed(date.atZone(zoneId).toInstant(), zoneId);
    }

    public void useSystemDefaultZoneClock(){
        clock = Clock.systemDefaultZone();
    }

    public Clock getTime() {
        return clock ;
    }
    public void setTime(int numSeconds){
        clock.offset(constantClock, Duration.ofSeconds(10));
    }
    public Clock getClock(){
        return constantClock;
    }
}