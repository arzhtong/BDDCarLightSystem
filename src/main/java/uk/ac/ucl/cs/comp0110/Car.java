package uk.ac.ucl.cs.comp0110;
import java.util.TimerTask;
import java.util.Timer;

enum PitmanArmPosition{
    UPWARD7,DOWNWARD7,NEUTRAL,UPWARD5,DOWNWARD5
}
enum IgnitionStatus{
    NOKEYINSERTED,KEYINSERTED,KEYINIGNITIONONPOSITION
}
enum LightRotarySwitchState{
    ON,OFF,AUTO
}
enum DoorPosition{
    OPEN,CLOSED
}
public class Car {
    private IgnitionStatus ignitionState;
    private PitmanArmPosition pitmanArmState;
    private Indicator leftIndicator;
    private Indicator rightIndicator;
    private Light headLight;
    private Light corneringLight;
    private int lengthOfTimeHeld;
    private Timer timer;
    private boolean hazardSwitchState;
    private boolean inUSAOrCanada;
    private DoorPosition doorPosition;
    private boolean dayTimeRunningLight;
    private boolean ambientLight;
    private int exteriorBrightness;
    private int lowBeamHeadlightDuration;
    private boolean darknessSwitch;
    private Light tailLight;
    private int numberofFlashCycles;
    private int durationOfPassingCorner;
    private int drivingSpeed;
    private LightRotarySwitchState lightRotarySwitchState;
    public Car(){

        lightRotarySwitchState=LightRotarySwitchState.OFF;
        doorPosition=DoorPosition.CLOSED;
        leftIndicator=new Indicator();
        headLight=new Light();
        tailLight=new Light();
        corneringLight=new Light();
        rightIndicator=new Indicator();
        leftIndicator.setState(Blinking.NONFLASHING);
        rightIndicator.setState(Blinking.NONFLASHING);
        lengthOfTimeHeld=0;
        numberofFlashCycles=0;
        hazardSwitchState=false;
        darknessSwitch=false;
    }
    public void isIgnitionOn(IgnitionStatus ignitionState) {
        this.ignitionState = ignitionState;
        if (pitmanArmState==PitmanArmPosition.DOWNWARD7){
            leftIndicator.setState(Blinking.FLASHING);
        }else if (pitmanArmState==PitmanArmPosition.UPWARD7){
            rightIndicator.setState(Blinking.FLASHING);
        }
        if (ignitionState == IgnitionStatus.KEYINSERTED || ignitionState == IgnitionStatus.NOKEYINSERTED) {
            leftIndicator.setState(Blinking.NONFLASHING);
            rightIndicator.setState(Blinking.NONFLASHING);
            leftIndicator.setCycle(false);
            rightIndicator.setCycle(false);
            if (pitmanArmState == PitmanArmPosition.DOWNWARD5 || pitmanArmState == PitmanArmPosition.UPWARD5) {
                pitmanArmState = PitmanArmPosition.NEUTRAL;
            }
        }
        if (ambientLight == true && ignitionState==IgnitionStatus.NOKEYINSERTED) {
            countAmbientLightTime();
        }
        if (ambientLight==true && headLight.getAmbientLightDuration()<30000){
            stopTimer();
            countAmbientLightTime();
        }
        }


    public void setLengthOfTimeHeld(int lengthOfTimeHeld){
        this.lengthOfTimeHeld=lengthOfTimeHeld;

    }

    public void pressHazardSwitch(boolean hazardSwitchState){
        this.hazardSwitchState=hazardSwitchState;
        if (hazardSwitchState==false){
            leftIndicator.setState(Blinking.NONFLASHING);
            rightIndicator.setState(Blinking.NONFLASHING);
            if (pitmanArmState==PitmanArmPosition.DOWNWARD7){
                leftIndicator.setState(Blinking.FLASHING);
            }
            if (pitmanArmState==PitmanArmPosition.UPWARD7){
                rightIndicator.setState(Blinking.FLASHING);
            }

        }
        if (hazardSwitchState==true){

                leftIndicator.setState(Blinking.FLASHING);
                rightIndicator.setState(Blinking.FLASHING);
                leftIndicator.setCycle(false);
                rightIndicator.setCycle(false);
                leftIndicator.setHazardCycleLength(1);
                rightIndicator.setHazardCycleLength(1);

        }

    }
    public void checkPitmanArmState(){
        if (hazardSwitchState==true){
            rightIndicator.setState(Blinking.FLASHING);
            leftIndicator.setState(Blinking.FLASHING);
        }

        if (pitmanArmState==PitmanArmPosition.UPWARD7){

            rightIndicator.setState(Blinking.FLASHING);
        }
        if (pitmanArmState==PitmanArmPosition.DOWNWARD7){
            leftIndicator.setState(Blinking.FLASHING);
        }
    }
    public void pressDarknessSwitch(boolean darknessSwitch){
        if (darknessSwitch==true){
            //engageAmbientLight(false);
            corneringLight.setState(Lighting.OFF);
        }else{
            if (leftIndicator.getState()==Blinking.FLASHING || rightIndicator.getState()==Blinking.FLASHING && drivingSpeed<10 && headLight.getState()==Lighting.ON)
                corneringLight.setState(Lighting.ON);
        }
        this.darknessSwitch=darknessSwitch;

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
    public void setNumberofFlashCycles(int numberofFlashCycles){
        if (numberofFlashCycles==3){
            setNumberofFlashCycles(0);
            setLengthOfTimeHeld(0);
            setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        }
        if (leftIndicator.getState()==Blinking.FLASHING){
            leftIndicator.setNumberofFlashCycles(numberofFlashCycles);
        }
        if (rightIndicator.getState()==Blinking.FLASHING){
            rightIndicator.setNumberofFlashCycles(numberofFlashCycles);
        }
    }
    public void engageDayTimeRunningLight(boolean dayTimeRunningLight){
        this.dayTimeRunningLight=dayTimeRunningLight;
        if (dayTimeRunningLight==true && (ignitionState==IgnitionStatus.KEYINIGNITIONONPOSITION || ignitionState==IgnitionStatus.KEYINSERTED)){
            setLightBeam(LowBeamState.ACTIVE);
        }else{
            setLightBeam(LowBeamState.INACTIVE);

        }
    }
    public void engageAmbientLight(boolean ambientLight){
        if (darknessSwitch==false) {
            this.ambientLight = ambientLight;
        }
        if (ambientLight==true && darknessSwitch==false){
            setLightBeam(LowBeamState.ACTIVE);
            countAmbientLightTime();
        }else{
            setLightBeam(LowBeamState.INACTIVE);
        }
    }
    public void changeDoorPosition(DoorPosition doorPosition){
        this.doorPosition=doorPosition;
        System.out.println("testing3");
        if (ambientLight==true && headLight.getAmbientLightDuration()<30000 && exteriorBrightness>200) {
            System.out.println("testing2");
            countAmbientLightTime();
        }
        if (doorPosition==DoorPosition.CLOSED && exteriorBrightness<200 && ambientLight==true){
            System.out.println("testing1");
            setLightBeam(LowBeamState.INACTIVE);
        }
        if (doorPosition==DoorPosition.OPEN && ambientLight==true){
            if (exteriorBrightness<200) {
                System.out.println("testing");
               setLightBeam(LowBeamState.ACTIVE);
            }
            }
        }

    public void setPitmanArmPosition(PitmanArmPosition position) {
        pitmanArmState=position;
        if (ignitionState==IgnitionStatus.KEYINIGNITIONONPOSITION){

            if (position==PitmanArmPosition.UPWARD7 || position==PitmanArmPosition.UPWARD5){

                setNumberofFlashCycles(0);

                rightIndicator.setState(Blinking.FLASHING);
                leftIndicator.setState(Blinking.NONFLASHING);

            }
            if (position==PitmanArmPosition.DOWNWARD7 || position==PitmanArmPosition.DOWNWARD5){
                setNumberofFlashCycles(0);
                leftIndicator.setState(Blinking.FLASHING);
                rightIndicator.setState(Blinking.NONFLASHING);
            }
            if (position==PitmanArmPosition.DOWNWARD7 || position==PitmanArmPosition.UPWARD7){
                checkCorneringLight();
                leftIndicator.setCycle(false);
                rightIndicator.setCycle(false);
            }

            if (position ==PitmanArmPosition.NEUTRAL){
                corneringLight.setState(Lighting.OFF);
                leftIndicator.setState(Blinking.NONFLASHING);
                rightIndicator.setState(Blinking.NONFLASHING);
                leftIndicator.setCycle(false);
                rightIndicator.setCycle(false);
                darkenIndicators();
            }
        }

    }
    public void setDrivingSpeed(int drivingSpeed){
        this.drivingSpeed=drivingSpeed;
        if (headLight.getLowBeamState()==LowBeamState.ACTIVE){
            checkCorneringLight();
        }
    }
    public void checkCorneringLight(){
        if (leftIndicator.getState()==Blinking.FLASHING || rightIndicator.getState()==Blinking.FLASHING && drivingSpeed<10){
            corneringLight.setState(Lighting.ON);
        }
    }
    public void setExteriorBrightness(int exteriorBrightness){
        this.exteriorBrightness=exteriorBrightness;
        if (lightRotarySwitchState==LightRotarySwitchState.AUTO && exteriorBrightness<200){
            setLightBeam(LowBeamState.ACTIVE);
            countLowBeamTime();
        }
        if (lightRotarySwitchState==LightRotarySwitchState.AUTO && exteriorBrightness>250){
            setLightBeam(LowBeamState.INACTIVE);
        }
    }
    public void setLightBeam(LowBeamState beamState){
        if (beamState==LowBeamState.ACTIVE){
            headLight.setLowBeamState(LowBeamState.ACTIVE);
            if (inUSAOrCanada){
                leftIndicator.setState(Blinking.FLASHING);
                rightIndicator.setState(Blinking.FLASHING);
            }else{
                tailLight.setLowBeamState(LowBeamState.ACTIVE);
            }
        }else if (beamState==LowBeamState.INACTIVE){
            headLight.setLowBeamState(LowBeamState.INACTIVE);
            if (inUSAOrCanada){
                leftIndicator.setState(Blinking.NONFLASHING);
                rightIndicator.setState(Blinking.NONFLASHING);
            }else{
                tailLight.setLowBeamState(LowBeamState.INACTIVE);
            }
        }
    }

    public void setLowBeamHeadlightDuration(int lowBeamHeadlightDuration){
        this.lowBeamHeadlightDuration=lowBeamHeadlightDuration;
    }
    public void turnLightRotarySwitch(LightRotarySwitchState lightRotarySwitchState) {
        leftIndicator.setDimmedLight(0);
        rightIndicator.setDimmedLight(0);

        if (ignitionState == IgnitionStatus.KEYINIGNITIONONPOSITION) {
            this.lightRotarySwitchState = lightRotarySwitchState;
            leftIndicator.setDimmedLight(0);
            rightIndicator.setDimmedLight(0);

            if (lightRotarySwitchState == LightRotarySwitchState.ON) {
                checkCorneringLight();
                setLightBeam(LowBeamState.ACTIVE);
            }
            if (lightRotarySwitchState == LightRotarySwitchState.OFF) {
                leftIndicator.setDimmedLight(0);
                rightIndicator.setDimmedLight(0);
                setLightBeam(LowBeamState.INACTIVE);
            }


        }
        if (ignitionState == IgnitionStatus.NOKEYINSERTED) {
            if (this.lightRotarySwitchState == LightRotarySwitchState.OFF) {
                this.lightRotarySwitchState = lightRotarySwitchState;
                if (lightRotarySwitchState == LightRotarySwitchState.ON) {
                    leftIndicator.setDimmedLight(50);
                    rightIndicator.setDimmedLight(50);
                    setLightBeam(LowBeamState.ACTIVE);
                }
            }
            this.lightRotarySwitchState=lightRotarySwitchState;
            if (lightRotarySwitchState==LightRotarySwitchState.AUTO){
                setLightBeam(LowBeamState.INACTIVE);
            }
            if (lightRotarySwitchState==LightRotarySwitchState.OFF){
                setLightBeam(LowBeamState.INACTIVE);
            }
        }

        this.lightRotarySwitchState=lightRotarySwitchState;
    }
    public void setDurationOfPassingCorner(int durationOfPassingCorner){
        this.durationOfPassingCorner=durationOfPassingCorner;
        if (durationOfPassingCorner==5){
            corneringLight.setState(Lighting.OFF);
        }
    }

    public void tipPitmanArm(PitmanArmPosition position,double time) {
        setNumberofFlashCycles(0);
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
    public void darkenIndicators(){
        if (leftIndicator.getState()==Blinking.FLASHING) {
            rightIndicator.setFlashState(Flashing.DARK);
        }
        if (rightIndicator.getState()==Blinking.FLASHING) {
            leftIndicator.setFlashState(Flashing.DARK);
        }
    }
    public IgnitionStatus getIgnitionState(){
        return ignitionState;
    }
    public LowBeamState getLowBeamState(Light carlight){
        if (carlight==headLight){
            return headLight.getLowBeamState();
        }else if (carlight==tailLight){
            return tailLight.getLowBeamState();
        }
        return null;
    }

    public Light getTailLight(){
        return tailLight;
    }

    public LightRotarySwitchState getLightRotarySwitchState(){
        return lightRotarySwitchState;
    }
    public Flashing getFlashState(){
        if (rightIndicator.getState()==Blinking.FLASHING){
            return rightIndicator.getFlashState();
        }
        if (leftIndicator.getState()==Blinking.FLASHING){
            return leftIndicator.getFlashState();
        }
        return null;

    }
    public int getExteriorBrightness(){
        return exteriorBrightness;
    }
    public int getLowBeamHeadlightDuration(){
        return lowBeamHeadlightDuration;
    }
    public void changeFlashState() {
        if (rightIndicator.getState() == Blinking.FLASHING) {
            if (rightIndicator.getFlashState() == Flashing.BRIGHT) {
                rightIndicator.setFlashState(Flashing.DARK);
            } else {
                rightIndicator.setFlashState(Flashing.BRIGHT);
            }
        }
        if (leftIndicator.getState() == Blinking.FLASHING) {
            if (leftIndicator.getFlashState() == Flashing.BRIGHT) {
                leftIndicator.setFlashState(Flashing.DARK);
            } else {
                leftIndicator.setFlashState(Flashing.BRIGHT);
            }
        }
    }
    public boolean getHazardSwitchState(){
        return hazardSwitchState;
    }
    public int getLengthOfHazardCycle(){
        return leftIndicator.getHazardCycleLength();
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

    public int getLengthOfTimeHeld(){
        return lengthOfTimeHeld;
    }
    public Light getHeadLight(){
        return headLight;
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
    public void countDurationOfPassingCornerTime(){
        durationOfPassingCorner=0;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                durationOfPassingCorner++;
                if (durationOfPassingCorner==5000){
                    corneringLight.setState(Lighting.OFF);
                    leftIndicator.setState(Blinking.NONFLASHING);
                    rightIndicator.setState(Blinking.NONFLASHING);
                    stopTimer();
                }
            }
        }, 1,1);

    }
    public void countAmbientLightTime(){
//        SystemClock currentTime=new SystemClock();
//
//        Clock newClock=currentTime.setTime(10);
//        System.out.println(LocalTime.now(currentTime.getConstantClock()));
        headLight.setAmberLightDuration(0);
        checkAmberLightDuration();
        timer=new Timer();
        setLightBeam(LowBeamState.ACTIVE);
        timer.schedule(new TimerTask() {
            int x=headLight.getAmbientLightDuration();
            @Override
            public void run() {
                x++;
                headLight.setAmberLightDuration(x);
                checkAmberLightDuration();
                if (headLight.getAmbientLightDuration()==30000){
                    setLightBeam(LowBeamState.INACTIVE);
                    stopTimer();
                }
            }
        }, 1,1);


    }
    public void checkAmberLightDuration(){
        if (headLight.getAmbientLightDuration()>30000){
            setLightBeam(LowBeamState.INACTIVE);
        }
    }
    public void countLowBeamTime(){

        lowBeamHeadlightDuration=0;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                lowBeamHeadlightDuration++;
                if (lowBeamHeadlightDuration>3000 && exteriorBrightness>250){
                    setLightBeam(LowBeamState.INACTIVE);
                    stopTimer();
                }
            }
        }, 1,1);


    }
    public void stopTimer(){

        timer.cancel();
    }
    public int getNumberofFlashCycles(){
        if (leftIndicator.getState()==Blinking.FLASHING){
            return leftIndicator.getNumberofFlashCycles();
        }
        if (rightIndicator.getState()==Blinking.FLASHING){
            return rightIndicator.getNumberofFlashCycles();
        }
        return 0;
    }
    public boolean getDayTimeRunningLight(){
        return dayTimeRunningLight;
    }
    public DoorPosition getDoorPosition(){
        return doorPosition;
    }
    public boolean getAmbientLight(){
        return ambientLight;
    }
    public boolean getDarknessSwitch(){
        return darknessSwitch;
    }
    public int getDrivingSpeed(){
        return drivingSpeed;
    }
    public Light getCorneringLight(){
        return corneringLight;
    }

}

