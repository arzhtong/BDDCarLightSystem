package uk.ac.ucl.cs.comp0110;
import java.util.TimerTask;
import java.util.Timer;
enum PitmanArmPosition{
    UPWARD7,DOWNWARD7,NEUTRAL,UPWARD5,DOWNWARD5
}
enum IgnitionStatus{
    NOKEYINSERTED,KEYINSERTED,KEYINIGNITIONONPOSITION
}
public class CarModel {
    private IgnitionStatus ignitionState;
    private PitmanArmPosition pitmanArmState;
    private Indicator leftIndicator;
    private Indicator rightIndicator;
    private int lengthOfTimeHeld;
    private Timer timer;
    public CarModel(){
        ignitionState=IgnitionStatus.KEYINIGNITIONONPOSITION;
        leftIndicator=new Indicator();
        rightIndicator=new Indicator();
        leftIndicator.setState(Blinking.NONFLASHING);
        rightIndicator.setState(Blinking.NONFLASHING);
        lengthOfTimeHeld=0;
    }
    public void isIgnitionOn(IgnitionStatus ignitionState){
        this.ignitionState=ignitionState;
    }
    public void setLengthOfTimeHeld(int lengthOfTimeHeld){
        this.lengthOfTimeHeld=lengthOfTimeHeld;
    }
    public void setHazardSwitch(boolean hazardSwitchState){

    }
    public void setPitmanArmPosition(PitmanArmPosition position) {
        pitmanArmState=position;
        if (ignitionState==IgnitionStatus.KEYINIGNITIONONPOSITION){
            if (position==PitmanArmPosition.UPWARD7 || position==PitmanArmPosition.UPWARD5){
                rightIndicator.setState(Blinking.FLASHING);
                leftIndicator.setState(Blinking.NONFLASHING);


            }
            if (position==PitmanArmPosition.DOWNWARD7 || position==PitmanArmPosition.DOWNWARD5){
                leftIndicator.setState(Blinking.FLASHING);
                rightIndicator.setState(Blinking.NONFLASHING);


            }
            if (position ==PitmanArmPosition.NEUTRAL){
                leftIndicator.setState(Blinking.NONFLASHING);
                rightIndicator.setState(Blinking.NONFLASHING);

            }


            }
        }

    public void countTimeInPosition() {
        lengthOfTimeHeld=0;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                lengthOfTimeHeld++;
                System.out.println(lengthOfTimeHeld);
            }
        }, 1,1);

    }
    public void stopTimer(){
        timer.cancel();

    }
    public int getLengthOfTimeHeld(){
        return lengthOfTimeHeld;
    }
    public void setFlashCycleState(PitmanArmPosition position,double time) {
        if (position == PitmanArmPosition.UPWARD5) {
            if (time < 500) {
                rightIndicator.setCycle(true);
                setPitmanArmPosition(PitmanArmPosition.UPWARD5);
            }
        }
        if (position == PitmanArmPosition.DOWNWARD5) {
            if (time < 500) {
                leftIndicator.setCycle(true);
                setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
            }
        }
    }

    public Blinking getBlinkingState(String direction){
        if (direction.equals("Right")){
            return rightIndicator.getState();
        }
        if (direction.equals("Left")){
            return leftIndicator.getState();
        }
        return null;
    }
    public PitmanArmPosition getPitmanArmState(){
        return pitmanArmState;
    }
    public boolean getFlashingCycles(String direction){
        if (direction.equals("Right")){
            return rightIndicator.getCycle();
        }
        if (direction.equals("Left")){
            return leftIndicator.getCycle();
        }
        return false;
    }
    public Indicator getLeftIndicator(){
        return leftIndicator;
    }

    public Indicator getRightIndicator() {
        return rightIndicator;
    }
}
