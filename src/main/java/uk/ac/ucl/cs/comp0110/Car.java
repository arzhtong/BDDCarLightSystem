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

/**
 * This is the model of the MVC Architecture being used to design a simulated car  following the
 * requirements of the ABZ Case Study 2020.
 * @Author Arzhan Tong
 */
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
    private Instant time;
    private boolean incomingVehicleDetectedByCamera;
    private static int timeForHeadLightToIlluminate;
    public Car(){
        time=Instant.EPOCH;
        timeForHeadLightToIlluminate=0;
        parkingLightEngaged=false;
        incomingVehicleDetectedByCamera=false;
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

    /**
     * This method sets the ignition status of the car and sets any states that should be set for the
     * indicators based on ignition status.
     * @param ignitionState The ignition state of the car
     */
    public void isIgnitionOn(IgnitionStatus ignitionState) {
        this.ignitionState = ignitionState;
        setParkingLightState();
        if (pitmanArmState==PitmanArmPosition.DOWNWARD7){
            leftIndicator.setState(Blinking.FLASHING);
        }else if (pitmanArmState==PitmanArmPosition.UPWARD7){
            rightIndicator.setState(Blinking.FLASHING);
        }
        if (ignitionState == IgnitionStatus.KEYINSERTED || ignitionState == IgnitionStatus.NOKEYINSERTED) {
            engageDayTimeRunningLight(false);
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
        /*
        This method sets the length of time that the pitman arm is held in a tip-blinking position
         */
    public void setLengthOfTimeHeld(int lengthOfTimeHeld){
        this.lengthOfTimeHeld=lengthOfTimeHeld;

    }

    /**
     *
     * @param hazardSwitchState Whether the hazard switch is engaged
     */
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

    /**
     *
     * @param numberOfDegreesSteeringWheelTurned The number of degrees that the steering wheel is turned clockwise or
     *                                           anti-clockwise
     */
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

    /**
     *
     * @param darknessSwitch Whether the darkness switch is engaged
     */
    public void pressDarknessSwitch(boolean darknessSwitch){
        if (darknessSwitch==true){
            if (ambientLight==true){
                engageAmbientLight(false);
            }

            leftIndicator.isCorneringLightOn(false);
            rightIndicator.isCorneringLightOn(false);
        }else{
            checkCorneringLight();
        }
        this.darknessSwitch=darknessSwitch;

    }

    /**
     * @param inUSAOrCanada Whether the car was sold in the USA or Canada
     */
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

    /**
     *  This method sets the number of flash cycles that has occurred by the indicator
     *  when the pitman arm is in a tip-blinking position.
     * @param numberofFlashCycles The number of flash cycles that has occurred
     */
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

    /**
     *
     * @param dayTimeRunningLight Whether the daytime running light is engaged
     */
    public void engageDayTimeRunningLight(boolean dayTimeRunningLight){
        this.dayTimeRunningLight=dayTimeRunningLight;
        if (dayTimeRunningLight==true && (ignitionState==IgnitionStatus.KEYINIGNITIONONPOSITION || ignitionState==IgnitionStatus.KEYINSERTED)){
            setLightBeam(Headlight.LOWBEAM);

        }else{
            setLightBeam(Headlight.INACTIVE);

        }
    }

    /**
     *
     * @param ambientLight Whether the ambient light is engaged
     */
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

    /**
     *  This method checks the position of the doors in the car which can determine whether to set the low beam headlights
     *   depending on if all doors are shut.
     * @param allDoorsClosed Whether all doors of the car are shut
     */
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

    /**
     * This method sets the numerous possible pitman arm positions and the state of the indicator(s) based
     * on this. Some positions may have an affect on the cornering light.
     * @param position The position of the pitman arm
     */
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
                checkIncomingVehicleDetectedByCamera();
            }
            if (position==PitmanArmPosition.LEFT){

                if (lightRotarySwitchState==LightRotarySwitchState.ON){
                    setLightBeam(Headlight.HIGHBEAM);
                    setHeadLightIlluminationArea(220);
                    setHeadLightLuminousStrength(100);
                }
                if (lightRotarySwitchState==LightRotarySwitchState.AUTO){
                    setLightBeam(Headlight.HIGHBEAM);
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

    /**
     *
     * @param drivingSpeed The speed of the car
     */
    public void setDrivingSpeed(int drivingSpeed){
        this.drivingSpeed=drivingSpeed;
        if (getHeadLightBeamState()== Headlight.LOWBEAM){
            checkCorneringLight();
        }
    }
    /**
    This method sets the state of the cornering light which may depend on other factors such as
    degrees turned by steering wheel and driving speed of the car.
     */
    public void checkCorneringLight(){
        leftIndicator.isCorneringLightOn(false);
        rightIndicator.isCorneringLightOn(false);
        if (leftIndicator.getState()==Blinking.FLASHING && drivingSpeed<10 && leftIndicator.getLowBeamState()==Headlight.LOWBEAM){
            leftIndicator.isCorneringLightOn(true);
        }
        if (rightIndicator.getState()==Blinking.FLASHING && drivingSpeed<10 && rightIndicator.getLowBeamState()==Headlight.LOWBEAM){
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

    /**
     *
     * @param exteriorBrightness The brightness of the outside of the car
     */
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

    /**
     * This method ensures that both the left and right indicators are set to the same
     * state when the headlight is active/inactive.
     * @param beamState The state of the headlight
     */
    public void setHeadLightBeamState(Headlight beamState){
        leftIndicator.setLowBeamState(beamState);
        rightIndicator.setLowBeamState(beamState);
    }

    /**
     *
     * @param beamState The state of the Headlight
     */
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

    /**
     *
     * @param lowBeamHeadlightDuration The time that the headlight is in a low beam state
     */
    public void setLowBeamHeadlightDuration(int lowBeamHeadlightDuration){
        this.lowBeamHeadlightDuration=lowBeamHeadlightDuration;
    }

    /**
     *  This method determines the position of the light rotary switch which will have
     *  an affect on the headlight state and brightness.
     * @param lightRotarySwitchState The position of the light rotary switch
     */
    public void turnLightRotarySwitch(LightRotarySwitchState lightRotarySwitchState) {
        headLight.setLightDimmingPercentage(0);
        headLight.setLightDimmingPercentage(0);

        if (ignitionState == IgnitionStatus.KEYINIGNITIONONPOSITION) {
            this.lightRotarySwitchState = lightRotarySwitchState;
            headLight.setLightDimmingPercentage(0);
            headLight.setLightDimmingPercentage(0);

            if (lightRotarySwitchState == LightRotarySwitchState.ON) {
                leftIndicator.setState(Blinking.NONFLASHING);
                rightIndicator.setState(Blinking.NONFLASHING);
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

    /**
     *
     * @param durationOfPassingCorner The time taken for a car to pass a corner
     */
    public void setDurationOfPassingCorner(int durationOfPassingCorner){
        this.durationOfPassingCorner=durationOfPassingCorner;
        if (durationOfPassingCorner==5){
            leftIndicator.isCorneringLightOn(false);
            rightIndicator.isCorneringLightOn(false);
        }
    }

    /**
     *  This method decides how the indicator will behave when the pitman arm is tipped into a tip-blinking
     *  position for a certain amount of time.
     * @param position The position of the pitman arm
     * @param time The time that the pitman arm is held in a tip-blinking position
     */
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
    /**
        This method determines whether the car is parking and therefore will have an affect on the determine the state
        of the indicator(s) and brightnness
     */
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

    /**
     *
     * @param reverseGearEngaged Whether the reverse gear of the car is engaged
     */
    public void isReverseGearEngaged(boolean reverseGearEngaged){
        this.reverseGearEngaged=reverseGearEngaged;
        checkReverseGearEngaged();

    }
    /**
    This method sets the states of the cornering lights after checking the state of the reverse gear.
     */
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

    /**
     *
     * @param incomingVehicleDetectedByCamera Whether the car has been detected by a camera
     */
    public void isIncomingVehicleDetectedByCamera(boolean incomingVehicleDetectedByCamera){
        this.incomingVehicleDetectedByCamera=incomingVehicleDetectedByCamera;
        checkIncomingVehicleDetectedByCamera();

    }
    /**
        This method sets the headlight state as well as how much area is illuminated and the luminous strength,
        depending on whether the car has been detected by camera and other factors.
     */
    public synchronized void checkIncomingVehicleDetectedByCamera() {
        if (incomingVehicleDetectedByCamera == false && drivingSpeed > 30 && getHeadLightBeamState() == Headlight.LOWBEAM) {
            calculateIlluminationArea();
            calculateLuminousStrength();
            countTimeToSetHighBeam();
        } else if (incomingVehicleDetectedByCamera == true && getHeadLightBeamState() == Headlight.HIGHBEAM) {
            countTimeToSetLowBeam();
        } else if (incomingVehicleDetectedByCamera == false && getHeadLightBeamState() == Headlight.LOWBEAM) {
            countTimeToSetHighBeam();
            System.out.println(Car.timeForHeadLightToIlluminate);

        }
    }
    /**
    This method darkens the indicator to ensure a cycle is finished when there is a change in the pitman arm position
    and before there is a change to the state of the indicator.
    @param
     **/
    public void darkenIndicators(){
        if (leftIndicator.getState()==Blinking.FLASHING) {
            rightIndicator.setFlashState(Flashing.DARK);
        }
        if (rightIndicator.getState()==Blinking.FLASHING) {
            leftIndicator.setFlashState(Flashing.DARK);
        }
    }
    /**
       @return The ignition state of the car
     **/
    public IgnitionStatus getIgnitionState(){
        return ignitionState;
    }

    /**
     *
     * @return How long the headlight should be illuminated for
     */
    public int getTimeForHeadlightToIlluminate(){
        return timeForHeadLightToIlluminate;
    }

    /**
     *
     * @return The state of the tail light
     */
    public Light getTailLight(){
        return tailLight;
    }

    /**
     *
     * @return The position of the light rotary switch
     */
    public LightRotarySwitchState getLightRotarySwitchState(){
        return lightRotarySwitchState;
    }

    /**
     *
     * @return The state of the indicator at a point in time as to whether the bulb is bright or dark
     */
    public Flashing getFlashState(){
        if (rightIndicator.getState()==Blinking.FLASHING){
            return rightIndicator.getFlashState();
        }
        if (leftIndicator.getState()==Blinking.FLASHING){
            return leftIndicator.getFlashState();
        }
        return null;

    }

    /**
     *
     * @return The state of the headlight as Lowbeam, Highbeam or Inactive.
     */
    public Headlight getHeadLightBeamState(){
        if (leftIndicator.getLowBeamState()==rightIndicator.getLowBeamState()){
            return leftIndicator.getLowBeamState();
        }
        return null;
    }

    /**
     *
     * @return The brightness of the outside of the car
     */
    public int getExteriorBrightness(){
        return exteriorBrightness;
    }

    /**
     *
     * @return The duration that the low beam headlight is active
     */
    public int getLowBeamHeadlightDuration(){
        return lowBeamHeadlightDuration;
    }

    /**
     * This method changes the state of the flashing of the indicator from bright to dark and vice versa
     */
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

    /**
     *
     * @return Whether the car has been detected by a camera
     */
    public boolean getIncomingVehicleDetectedByCamera(){
        return incomingVehicleDetectedByCamera;
    }

    /**
     *
     * @return Whether the hazard switch has been pressed
     */
    public boolean getHazardSwitchState(){
        return hazardSwitchState;
    }

    public int getLengthOfHazardCycle(){
        return leftIndicator.getHazardCycleLength();
    }

    /**
     *
     * @param direction The direction that the driver is signalling
     * @return The percentage of the brightness dimming of the indicator that corresponds to the direction indicated
     */
    public int getDimmedLightStatus(String direction){
        if (direction.equals("Right")){
            return headLight.getLightDimmingPercentage();
        }
        if (direction.equals("Left")){
            return headLight.getLightDimmingPercentage();
        }
        return 0;
    }

    /**
     *
     * @return The length of time the pitman arm is held in a tip-blinking position
     */
    public int getLengthOfTimeHeld(){
        return lengthOfTimeHeld;
    }
    public Light getHeadLight(){
        return headLight;
    }

    /**
     *
     * @param direction The direction that the driver is signalling
     * @return The blinking state of the indicator that corresponds to the direction the driver is signalling
     */
    public Blinking getBlinkingState(String direction){
        if (direction.equals("Right")){
            return rightIndicator.getState();
        }
        if (direction.equals("Left")){
            return leftIndicator.getState();
        }
        return null;
    }

    /**
     *
     * @return The position of the pitman arm
     */
    public PitmanArmPosition getPitmanArmState(){
        return pitmanArmState;
    }

    /**
     *
     * @param direction The direction the driver is signalling
     * @return Whether the three flashing cycles of tip-blinking is occurring for the indicator corresponding to the
     * direction the driver is signalling
     */
    public boolean getFlashingCycles(String direction){
        if (direction.equals("Right")){
            return rightIndicator.getCycle();
        }
        if (direction.equals("Left")){
            return leftIndicator.getCycle();
        }
        return false;
    }

    /**
     *
     * @return The left indicator
     */
    public Indicator getLeftIndicator() {

        return leftIndicator;
    }

    /**
     *
     * @return The right indicator
     */
    public Indicator getRightIndicator() {
        return rightIndicator;
    }

    /**
     * Uses a timer to count the time that the pitman arm is held in a tip-blinking position
     */
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

    /**
     * Uses a timer to count the time it takes for a car to pass a corner when driving
     */
    public void countDurationOfPassingCornerTime(){
        durationOfPassingCorner=0;
        timer=new Timer();
        leftIndicator.setState(Blinking.NONFLASHING);
        rightIndicator.setState(Blinking.NONFLASHING);
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

    /**
     * Uses a timer to count how long it takes for a car to set the headlight to highbeam
     */
    public void countTimeToSetHighBeam() {

        timer = new Timer();

        if (incomingVehicleDetectedByCamera == false) {
            timer.schedule(new TimerTask() {
                int x=0;
                @Override
                public void run() {
                    x++;
                    ++Car.timeForHeadLightToIlluminate;
                    if (Car.timeForHeadLightToIlluminate>2){
                        setLightBeam(Headlight.HIGHBEAM);
                        Car.timeForHeadLightToIlluminate=x;
                        Car.timeForHeadLightToIlluminate=x;
                        System.out.println(Car.timeForHeadLightToIlluminate);
                        stopTimer();

                    }
                }

            }, 1, 1);

        } else {

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setLightBeam(Headlight.HIGHBEAM);
                    timeForHeadLightToIlluminate++;
                    stopTimer();
                }
            }, 1, 1);

        }
    }

    /**
     *
     * @param timeForHeadLightToIlluminate Integer value for number of seconds it takes for headlight to illuminate
     */
    public void setTimeForHeadLightToIlluminate(int timeForHeadLightToIlluminate){
        this.timeForHeadLightToIlluminate=timeForHeadLightToIlluminate;
    }

    /**
     * Uses a timer to count how long it takes for a car to set the headlight to lowbeam
     */
    public void countTimeToSetLowBeam(){

        timer=new Timer();
        timeForHeadLightToIlluminate=0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                setLightBeam(Headlight.LOWBEAM);
                setHeadLightIlluminationArea(65);
                System.out.println("working");
                calculateLuminousStrength();
                timeForHeadLightToIlluminate++;
                stopTimer();
            }
        }, 1,1);

    }

    /**
     * Uses a timer to count how long the car has ambient light on
     */
    public void countAmbientLightTime(){
//        SystemClock currentTime=new SystemClock();
//
//        Clock newClock=currentTime.setTime(10);
//        System.out.println(LocalTime.now(currentTime.getConstantClock()));
        leftIndicator.setAmberLightDuration(0);
        rightIndicator.setAmberLightDuration(0);
        checkAmbientLightDuration();
        timer=new Timer();
        setLightBeam(Headlight.LOWBEAM);
        timer.schedule(new TimerTask() {
            int x=leftIndicator.getAmbientLightDuration();
            @Override
            public void run() {
                x++;
                leftIndicator.setAmberLightDuration(x);
                rightIndicator.setAmberLightDuration(x);
                checkAmbientLightDuration();
                if (getHeadLightAmbientLightDuration()==30000){
                    setLightBeam(Headlight.INACTIVE);
                    stopTimer();
                }
            }
        }, 1,1);


    }

    /**
     * Determines whether to set headlight to inactive based on time that ambient light is on
     */
    public void checkAmbientLightDuration(){
        if (getHeadLightAmbientLightDuration()>30000){
            setLightBeam(Headlight.INACTIVE);
        }
    }

    /**
     *
     * @return Number of seconds that ambient light is on
     */
    public int getHeadLightAmbientLightDuration(){
        return leftIndicator.getAmbientLightDuration();
    }

    /**
     * Uses a timer to count how long a headlight is in a low beam state
     */
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

    /**
     * Sets the area that is illuminated outside the car by the headlights based on driving speed
     */
    public void calculateIlluminationArea(){
        if (drivingSpeed>=165){
            setHeadLightIlluminationArea(300);
        }else{
            setHeadLightIlluminationArea(drivingSpeed+100);
        }
    }

    /**
     * Calculates the intensity of the luminosity of the headlights based on driving speed
     */
    public void calculateLuminousStrength(){
        if (drivingSpeed>=120){
            setHeadLightLuminousStrength(100);
        }else{
            setHeadLightLuminousStrength(drivingSpeed-10);
        }
    }

    /**
     * Stops any timer that is taking place
     */
    public void stopTimer(){

        timer.cancel();
    }

    /**
     *
     * @return The number of cycles that have occurred when tip-blinking is taking place
     */
    public int getNumberofFlashCycles(){
        if (leftIndicator.getState()==Blinking.FLASHING){
            return leftIndicator.getNumberofFlashCycles();
        }
        if (rightIndicator.getState()==Blinking.FLASHING){
            return rightIndicator.getNumberofFlashCycles();
        }
        return 0;
    }

    /**
     *
     * @return Whether daytime running light is active
     */
    public boolean getDayTimeRunningLight(){
        return dayTimeRunningLight;
    }

    /**
     *
     * @return Whether all doors of the car are shut
     */
    public boolean getAllDoorsClosed(){
        return allDoorsClosed;
    }

    /**
     *
     * @return Whether the ambient light is active
     */
    public boolean getAmbientLight(){
        return ambientLight;
    }

    /**
     *
     * @return Whether darkness mode is active
     */
    public boolean getDarknessSwitch(){
        return darknessSwitch;
    }

    /**
     *
     * @return Whether the reverse gear has been engaged
     */
    public boolean getReverseGearEngaged(){
        return reverseGearEngaged;
    }

    /**
     *
     * @return Whether the parking light of the car is engaged
     */
    public boolean getParkingLightEngaged(){
        return parkingLightEngaged;
    }
    public void printTheTime(){
        System.out.println(Instant.now(clock));
    }

    /**
     *
     * @param illuminationArea The area that is to be illuminated by the headlights
     */
    public void setHeadLightIlluminationArea(int illuminationArea){
        leftIndicator.setIlluminationArea(illuminationArea);
        rightIndicator.setIlluminationArea(illuminationArea);
    }

    /**
     *
     * @param luminousStrengthPercentage The luminosity strength of the headlights
     */
    public void setHeadLightLuminousStrength(int luminousStrengthPercentage){
        leftIndicator.setIlluminationStrengthPercentage(luminousStrengthPercentage);
        rightIndicator.setIlluminationStrengthPercentage(luminousStrengthPercentage);
    }

    /**
     *
     * @return The area that is illuminated outside the car
     */
    public int getIlluminationArea(){
        return leftIndicator.getIlluminationArea();
    }

    /**
     *
     * @return The strength of the luminosity of the headlights
     */
    public int getLuminiousStrength(){
        return leftIndicator.getIlluminationStrengthPercentage();
    }
}

