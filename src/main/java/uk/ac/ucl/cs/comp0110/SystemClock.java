package uk.ac.ucl.cs.comp0110;
import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.*;

public class SystemClock{
    public Clock constantClock;
    public SystemClock(){
        constantClock=Clock.systemUTC();
    }
    public Clock setTime(int numseconds){
        Clock clock= Clock.offset(constantClock, Duration.ofSeconds(10));
        return clock;
    }
    public Clock getConstantClock(){
        return constantClock;
    }

}
