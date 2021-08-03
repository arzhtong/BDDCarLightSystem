package uk.ac.ucl.cs.comp0110;
import java.time.Clock;
import java.time.Instant;
import java.util.TimerTask;
import java.util.Timer;

enum PitmanArmPosition{
    UPWARD7,DOWNWARD7,NEUTRAL,UPWARD5,DOWNWARD5,RIGHT,LEFT
}
enum IgnitionStatus{
    NOKEYINSERTED,KEYINSERTED,KEYINIGNITIONONPOSITION
}
enum LightRotarySwitchState{
    ON,OFF,AUTO
}

public class Car {
    private IgnitionStatus ignitionState;
    private PitmanArmPosition pitmanArmState;
    private Indicator leftIndicator;
    private Indicator rightIndicator;
    private Light headLight;
    private int lengthOfTimeHeld;
    private Timer timer;
    private boolean hazardSwitchState;
    private boolean inUSAOrCanada;
    private boolean allDoorsClosed;
    private boolean dayTimeRunningLight;
    private boolean ambientLight;
    private int exteriorBrightness;
    private int lowBeamHeadlightDuration;
    private boolean darknessSwitch;
    private Light tailLight;
    private int numberofFlashCycles;
    private int durationOfPassingCorner;
    private int drivingSpeed;
    private int numberOfDegreesSteeringWheelTurned;
    private LightRotarySwitchState lightRotarySwitchState;
    private boolean reverseGearEngaged;
    private boolean parkingLightEngaged;
    private Clock clock;
    public Car(){
        parkingLightEngaged=false;

        allDoorsClosed=true;
        reverseGearEngaged=false;
        numberOfDegreesSteeringWheelTurned=0;
        lightRotarySwitchState=LightRotarySwitchState.OFF;
        leftIndicator=new Indicator();
        headLight=new Light();
        tailLight=new Light();
        rightIndicator=new Indicator();
        leftIndicator.setState(Blinking.NONFLASHING);
        rightIndicator.setState(Blinking.NONFLASHING);
        lengthOfTimeHeld=0;
        numberofFlashCycles=0;
        hazardSwitchState=false;
        darknessSwitch=false;
    }
    public Car(Clock clock){
        parkingLightEngaged=false;
        this.clock=clock;
        allDoorsClosed=true;
        reverseGearEngaged=false;
        numberOfDegreesSteeringWheelTurned=0;
        lightRotarySwitchState=LightRotarySwitchState.OFF;
        leftIndicator=new Indicator();
        headLight=new Light();
        tailLight=new Light();
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
        setParkingLightState();
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
        if (ambientLight==true && getHeadLightAmbientLightDuration()<30000){
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
    public void setDegreesSteeringWheelTurned(int numberOfDegreesSteeringWheelTurned){
        this.numberOfDegreesSteeringWheelTurned=numberOfDegreesSteeringWheelTurned;
        checkCorneringLight();

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
            engageAmbientLight(false);
            leftIndicator.isCorneringLightOn(false);
            rightIndicator.isCorneringLightOn(false);
        }else{
            checkCorneringLight();
        }
        this.darknessSwitch=darknessSwitch;

    }
    public void setInUSAOrCanada(boolean inUSAOrCanada){
        this.inUSAOrCanada=inUSAOrCanada;

        if (inUSAOrCanada==true){
            headLight.setLightDimmingPercentage(50);
            headLight.setLightDimmingPercentage(50);
        }else{
            headLight.setLightDimmingPercentage(0);
            headLight.setLightDimmingPercentage(0);
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
            setLightBeam(Headlight.LOWBEAM);

        }else{
            setLightBeam(Headlight.INACTIVE);

        }
    }
    public void engageAmbientLight(boolean ambientLight){
        if (darknessSwitch==false) {
            this.ambientLight = ambientLight;
        }
        if (ambientLight==true && darknessSwitch==false){
            setLightBeam(Headlight.LOWBEAM);
            countAmbientLightTime();
        }else{
            setLightBeam(Headlight.INACTIVE);
        }
    }
    public void isAllDoorsClosed(boolean allDoorsClosed){
        this.allDoorsClosed=allDoorsClosed;
        if (ambientLight==true && getHeadLightAmbientLightDuration()<30000 && exteriorBrightness>200) {
            countAmbientLightTime();
        }
        if (allDoorsClosed==true && exteriorBrightness<200 && ambientLight==true){
            setLightBeam(Headlight.INACTIVE);
        }
        if (allDoorsClosed==false && ambientLight==true){
            if (exteriorBrightness<200) {
               setLightBeam(Headlight.LOWBEAM);
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
            if (position==PitmanArmPosition.RIGHT){
                setLightBeam(Headlight.HIGHBEAM);
            }
            if (position==PitmanArmPosition.LEFT){
                if (lightRotarySwitchState==LightRotarySwitchState.ON){
                    setLightBeam(Headlight.HIGHBEAM);
                    setHeadLightIlluminationArea(220);
                    setHeadLightLuminousStrength(100);

                }
            }

            if (position ==PitmanArmPosition.NEUTRAL){
                checkCorneringLight();
                
                leftIndicator.setState(Blinking.NONFLASHING);
                rightIndicator.setState(Blinking.NONFLASHING);
                leftIndicator.setCycle(false);
                rightIndicator.setCycle(false);
                darkenIndicators();
            }
        }
        checkReverseGearEngaged();
        setParkingLightState();
    }
    public void setDrivingSpeed(int drivingSpeed){
        this.drivingSpeed=drivingSpeed;
        if (getHeadLightBeamState()== Headlight.LOWBEAM){
            checkCorneringLight();
        }
    }
    public void checkCorneringLight(){
        leftIndicator.isCorneringLightOn(false);
        rightIndicator.isCorneringLightOn(false);
        if (leftIndicator.getState()==Blinking.FLASHING && drivingSpeed<10){
            leftIndicator.isCorneringLightOn(true);
        }
        if (rightIndicator.getState()==Blinking.FLASHING && drivingSpeed<10){
            rightIndicator.isCorneringLightOn(true);
        }
        if (numberOfDegreesSteeringWheelTurned>=10 && leftIndicator.getState()==Blinking.NONFLASHING && rightIndicator.getState()==Blinking.NONFLASHING
        && drivingSpeed>0 && drivingSpeed <10){
            rightIndicator.isCorneringLightOn(true);
        }
        if (numberOfDegreesSteeringWheelTurned>=10 && leftIndicator.getState()==Blinking.NONFLASHING && rightIndicator.getState()==Blinking.NONFLASHING
                && drivingSpeed<0 && drivingSpeed >-10){
            leftIndicator.isCorneringLightOn(true);
        }
    }
    public void setExteriorBrightness(int exteriorBrightness){
        this.exteriorBrightness=exteriorBrightness;
        if (lightRotarySwitchState==LightRotarySwitchState.AUTO && exteriorBrightness<200){
            setLightBeam(Headlight.LOWBEAM);
            countLowBeamTime();
        }
        if (lightRotarySwitchState==LightRotarySwitchState.AUTO && exteriorBrightness>250){
            setLightBeam(Headlight.INACTIVE);
        }
    }
    public void setHeadLightBeamState(Headlight beamState){
        leftIndicator.setLowBeamState(beamState);
        rightIndicator.setLowBeamState(beamState);
    }
    public void setLightBeam(Headlight beamState){
        if (beamState== Headlight.LOWBEAM){
            setHeadLightBeamState(Headlight.LOWBEAM);
            if (inUSAOrCanada){
                leftIndicator.setState(Blinking.FLASHING);
                rightIndicator.setState(Blinking.FLASHING);
            }else{
//                tailLight.setLowBeamState(LowBeam.ACTIVE);
            }
        }else if (beamState== Headlight.INACTIVE){
            setHeadLightBeamState(Headlight.INACTIVE);
            if (inUSAOrCanada){
                leftIndicator.setState(Blinking.NONFLASHING);
                rightIndicator.setState(Blinking.NONFLASHING);
            }else{
               // tailLight.setLowBeamState(LowBeam.INACTIVE);
            }
        }else if (beamState==Headlight.HIGHBEAM){
            setHeadLightBeamState(Headlight.HIGHBEAM);
        }
    }

    public void setLowBeamHeadlightDuration(int lowBeamHeadlightDuration){
        this.lowBeamHeadlightDuration=lowBeamHeadlightDuration;
    }
    public void turnLightRotarySwitch(LightRotarySwitchState lightRotarySwitchState) {
        headLight.setLightDimmingPercentage(0);
        headLight.setLightDimmingPercentage(0);

        if (ignitionState == IgnitionStatus.KEYINIGNITIONONPOSITION) {
            this.lightRotarySwitchState = lightRotarySwitchState;
            headLight.setLightDimmingPercentage(0);
            headLight.setLightDimmingPercentage(0);

            if (lightRotarySwitchState == LightRotarySwitchState.ON) {
                checkCorneringLight();
                setLightBeam(Headlight.LOWBEAM);
            }
            if (lightRotarySwitchState == LightRotarySwitchState.OFF) {
                headLight.setLightDimmingPercentage(0);
                headLight.setLightDimmingPercentage(0);
                setLightBeam(Headlight.INACTIVE);
            }


        }
        if (ignitionState == IgnitionStatus.NOKEYINSERTED) {
            if (this.lightRotarySwitchState == LightRotarySwitchState.OFF) {
                this.lightRotarySwitchState = lightRotarySwitchState;
                if (lightRotarySwitchState == LightRotarySwitchState.ON) {
                    setParkingLightState();
                   headLight.setLightDimmingPercentage(50);
                    headLight.setLightDimmingPercentage(50);
                    setLightBeam(Headlight.LOWBEAM);
                }
            }
            this.lightRotarySwitchState=lightRotarySwitchState;
            if (lightRotarySwitchState==LightRotarySwitchState.AUTO){
                setLightBeam(Headlight.INACTIVE);
            }
            if (lightRotarySwitchState==LightRotarySwitchState.OFF){
                setLightBeam(Headlight.INACTIVE);
            }
        }

        this.lightRotarySwitchState=lightRotarySwitchState;
    }
    public void setDurationOfPassingCorner(int durationOfPassingCorner){
        this.durationOfPassingCorner=durationOfPassingCorner;
        if (durationOfPassingCorner==5){
            leftIndicator.isCorneringLightOn(false);
            rightIndicator.isCorneringLightOn(false);
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
    public void setParkingLightState(){
        if (ignitionState==IgnitionStatus.NOKEYINSERTED && lightRotarySwitchState==LightRotarySwitchState.ON){
            if (pitmanArmState==PitmanArmPosition.DOWNWARD5 || pitmanArmState==PitmanArmPosition.DOWNWARD7){
                parkingLightEngaged=true;
                leftIndicator.setLightDimmingPercentage(10);
                leftIndicator.isTailLightOn(true);
                leftIndicator.setLowBeamState(Headlight.LOWBEAM);
                rightIndicator.setLightDimmingPercentage(10);

            }else if (pitmanArmState==PitmanArmPosition.UPWARD5 || pitmanArmState==PitmanArmPosition.UPWARD7){
                parkingLightEngaged=true;
                rightIndicator.setLightDimmingPercentage(10);
                leftIndicator.setLightDimmingPercentage(10);
                rightIndicator.setLowBeamState(Headlight.LOWBEAM);
                rightIndicator.isTailLightOn(true);

            }else{
                parkingLightEngaged=false;
            }
        }
    }
    public void isReverseGearEngaged(boolean reverseGearEngaged){
        this.reverseGearEngaged=reverseGearEngaged;
        checkReverseGearEngaged();

    }
    public void checkReverseGearEngaged(){
        if (reverseGearEngaged==true){
            if (leftIndicator.getState()==Blinking.FLASHING){
                rightIndicator.isCorneringLightOn(true);
            }else if (rightIndicator.getState()==Blinking.FLASHING){
                leftIndicator.isCorneringLightOn(true);
            }
        }else{
            checkCorneringLight();

            this.reverseGearEngaged=false;
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
    public Headlight getHeadLightBeamState(){
        if (leftIndicator.getLowBeamState()==rightIndicator.getLowBeamState()){
            return leftIndicator.getLowBeamState();
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
            return headLight.getLightDimmingPercentage();
        }
        if (direction.equals("Left")){
            return headLight.getLightDimmingPercentage();
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
                    leftIndicator.isCorneringLightOn(false);
                    rightIndicator.isCorneringLightOn(false);
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
        leftIndicator.setAmberLightDuration(0);
        rightIndicator.setAmberLightDuration(0);
        checkAmberLightDuration();
        timer=new Timer();
        setLightBeam(Headlight.LOWBEAM);
        timer.schedule(new TimerTask() {
            int x=leftIndicator.getAmbientLightDuration();
            @Override
            public void run() {
                x++;
                leftIndicator.setAmberLightDuration(x);
                rightIndicator.setAmberLightDuration(x);
                checkAmberLightDuration();
                if (getHeadLightAmbientLightDuration()==30000){
                    setLightBeam(Headlight.INACTIVE);
                    stopTimer();
                }
            }
        }, 1,1);


    }
    public void checkAmberLightDuration(){
        if (getHeadLightAmbientLightDuration()>30000){
            setLightBeam(Headlight.INACTIVE);
        }
    }
    public int getHeadLightAmbientLightDuration(){
        return leftIndicator.getAmbientLightDuration();
    }
    public void countLowBeamTime(){

        lowBeamHeadlightDuration=0;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                lowBeamHeadlightDuration++;
                if (lowBeamHeadlightDuration>3000 && exteriorBrightness>250){
                    setLightBeam(Headlight.INACTIVE);
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
    public boolean getAllDoorsClosed(){
        return allDoorsClosed;
    }
    public boolean getAmbientLight(){
        return ambientLight;
    }
    public boolean getDarknessSwitch(){
        return darknessSwitch;
    }
    public boolean getReverseGearEngaged(){
        return reverseGearEngaged;
    }
    public boolean getParkingLightEngaged(){
        return parkingLightEngaged;
    }
    public void printTheTime(){
        System.out.println(Instant.now(clock));
    }
    public void setHeadLightIlluminationArea(int illuminationArea){
        leftIndicator.setIlluminationArea(illuminationArea);
        rightIndicator.setIlluminationArea(illuminationArea);
    }
    public void setHeadLightLuminousStrength(int luminousStrengthPercentage){
        leftIndicator.setIlluminationStrengthPercentage(luminousStrengthPercentage);
        rightIndicator.setIlluminationStrengthPercentage(luminousStrengthPercentage);
    }
}

