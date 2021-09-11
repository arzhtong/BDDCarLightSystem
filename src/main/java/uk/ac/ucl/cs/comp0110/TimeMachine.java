package uk.ac.ucl.cs.comp0110;
import java.time.*;
import java.util.concurrent.atomic.AtomicInteger;

public class TimeMachine extends Thread{
    private static Clock clock = Clock.fixed(Instant.ofEpochMilli(0),ZoneId.systemDefault());
    private int tick_delay=1000;
    private LocalTime myClock=now();
    private int numSeconds=0;
    public LocalTime now() {
        return LocalTime.now(getClock());
    }
    public void setClockForTesting(){
        tick_delay=0;
    }
    public void tick(){
        try {
            myClock = myClock.plusSeconds(1);
            Thread.sleep(tick_delay);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void forwardTimeForAmbientLight(Car car)  {
        int counter=0;
        numSeconds=0;
        while(true){
            tick();
            counter++;
            System.out.println(counter);
            if (counter==30){
                car.setHeadLightBeam(Headlight.INACTIVE);
                return;
            }
        }
    }
    public void testTimeForAmbientLight(int duration,Car car)  {
        setClockForTesting();
        int counter=0;
        numSeconds=0;
        boolean allDoorsClosed=car.getAllDoorsClosed();
        IgnitionStatus ignitionStatus=car.getIgnitionState();
        while(true){
            if (counter<duration){
                tick();
                System.out.println(myClock.getSecond());
                counter++;
            }
            if (counter==duration && duration>=30){
                numSeconds=myClock.getSecond();
                car.checkToResetAmbientLightDuration(allDoorsClosed,ignitionStatus);
                return;
            }
            if (duration<30 && counter==duration){
                numSeconds=myClock.getSecond();
                car.checkToResetAmbientLightDuration(allDoorsClosed,ignitionStatus);
                return;
            }

        }
    }
    public void testTimeForTipBlinking(double duration,Car car)  {
        setClockForTesting();
        int counter=0;
        numSeconds=0;
        while(true){
            if (counter<duration){
                tick();
                System.out.println(myClock.getSecond());
                counter++;
            }
            if (counter>=duration && duration<=0.5){
                numSeconds=myClock.getSecond();
                car.getRightIndicator().isTipBlinkingOn(true);
                car.getLeftIndicator().isTipBlinkingOn(true);
                return;
            }
            if (duration>0.5 && counter>=duration){
                numSeconds=myClock.getSecond();
                return;
            }

        }
    }
    public void testTimeForSettingHighBeam(double duration,Car car)  {
        setClockForTesting();
        int counter=0;
        numSeconds=0;
        while(true){
            if (counter<duration){
                tick();
                System.out.println(myClock.getSecond());
                counter++;
            }
            if (counter>=duration && duration<2){
                numSeconds=myClock.getSecond();
                return;
            }
            if (duration>=2 && counter>=duration){
                numSeconds=myClock.getSecond();
                car.checkToSetHighBeam();
                return;
            }

        }
    }

    public int getNumSeconds(){
        return numSeconds;
    }
    private Clock getClock() {
        return clock ;
    }

}


