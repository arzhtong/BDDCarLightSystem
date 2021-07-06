package uk.ac.ucl.cs.comp0110;
import java.util.TimerTask;
import java.util.Timer;
enum PitmanArmPosition{
    UPWARD7,DOWNWARD7,NEUTRAL,UPWARD5,DOWNWARD5
}
enum IgnitionStatus{
    NOKEYINSERTED,KEYINSERTED,KEYINIGNITIONONPOSITION
}
public class Car {
    private IgnitionStatus ignitionState;
    private PitmanArmPosition pitmanArmState;
    private Indicator leftIndicator;
    private Indicator rightIndicator;
    private int lengthOfTimeHeld;
    private Timer timer;
    private boolean hazardSwitchState;
    private boolean inUSAOrCanada;
    public Car(){
        ignitionState=IgnitionStatus.KEYINIGNITIONONPOSITION;
        leftIndicator=new Indicator();
        rightIndicator=new Indicator();
        leftIndicator.setState(Blinking.NONFLASHING);
        rightIndicator.setState(Blinking.NONFLASHING);
        lengthOfTimeHeld=0;
        hazardSwitchState=false;
    }
    public void isIgnitionOn(IgnitionStatus ignitionState){
        this.ignitionState=ignitionState;
    }
    public void setLengthOfTimeHeld(int lengthOfTimeHeld){
        this.lengthOfTimeHeld=lengthOfTimeHeld;

    }

    public void setHazardSwitch(boolean hazardSwitchState){
        this.hazardSwitchState=hazardSwitchState;
        leftIndicator.setCycle(false);
        rightIndicator.setCycle(false);
        if (hazardSwitchState==true){
            leftIndicator.setState(Blinking.FLASHING);
            rightIndicator.setState(Blinking.FLASHING);
        }
    }
    public void setInUSAOrCanada(boolean inUSAOrCanada){
        this.inUSAOrCanada=inUSAOrCanada;
        if (inUSAOrCanada==true){
            leftIndicator.setDimmedLight(50);
            rightIndicator.setDimmedLight(50);
        }else{
            leftIndicator.setDimmedLight(0);
            rightIndicator.setDimmedLight(0);
        }
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
    public void tipPitmanArm(PitmanArmPosition position,double time) {
        if (position == PitmanArmPosition.UPWARD5) {
            setPitmanArmPosition(PitmanArmPosition.UPWARD5);
            if (time < 500) {
                rightIndicator.setCycle(true);
            }else{
                rightIndicator.setCycle(false);
            }
        }
        if (position == PitmanArmPosition.DOWNWARD5) {
            setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
            if (time < 500) {
                leftIndicator.setCycle(true);
            }else{
                leftIndicator.setCycle(false);
            }
        }
    }


    public boolean getHazardSwitchState(){
        return hazardSwitchState;
    }
    public int getDimmedLightStatus(String direction){
        if (direction.equals("Right")){
            return rightIndicator.getDimmedLight();
        }
        if (direction.equals("Left")){
            return leftIndicator.getDimmedLight();
        }
        return 0;
    }

    public boolean getInUSAOrCanada(){
        return inUSAOrCanada;
    }

    public int getLengthOfTimeHeld(){
        return lengthOfTimeHeld;
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
    public Indicator getLeftIndicator() {
        return leftIndicator;
    }
    public Indicator getRightIndicator() {
        return rightIndicator;
    }
    public void countTimeInPosition() {
        lengthOfTimeHeld=0;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                lengthOfTimeHeld++;
            }
        }, 1,1);

    }
    public void stopTimer(){
        timer.cancel();
    }
}

