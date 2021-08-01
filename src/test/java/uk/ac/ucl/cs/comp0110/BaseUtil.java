package uk.ac.ucl.cs.comp0110;

import java.time.Clock;

import static org.mockito.Mockito.mock;

public class BaseUtil {
    public Clock clock=mock(Clock.class);
    public Car car=new Car(clock);


}
