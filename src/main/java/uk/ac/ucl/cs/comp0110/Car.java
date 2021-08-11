package uk.ac.ucl.cs.comp0110;
import java.time.Clock;
import java.time.Instant;
import java.util.TimerTask;
import java.util.Timer;

enum PitmanArmPosition{
    UPWARD7,DOWNWARD7,NEUTRAL,UPWARD5,DOWNWARD5,FORWARD, BACKWARD
}
enum IgnitionStatus{
    NOKEYINSERTED,KEYINSERTED,KEYINIGNITIONONPOSITION
}
enum LightRotarySwitchState{
    ON,OFF,AUTO
}

/**
 * This is the model of the MVC Architecture being used to design a simulated car following the
 * requirements of the ABZ Case Study 2020.
 * @Author Arzhan Tong
 */
public class Car {
    private IgnitionStatus ignitionState;
    private PitmanArmPosition pitmanArmState;
    private Indicator leftIndicator;
    private Indicator rightIndicator;
    private Light headLight;
    private int timeInTipBlinkingPosition;
    private Timer timer;
    private boolean hazardWarningSwitchOn;
    private boolean inUSAOrCanada;
    private boolean allDoorsClosed;
    private boolean dayTimeRunningLightEngaged;
    private boolean ambientLightingEngaged;
    private int exteriorBrightnessLuminosity;
    private int lowBeamHeadlightDuration;
    private boolean darknessModeSwitchOn;
    private Light tailLight;
    private int numberofFlashCycles;
    private int durationBeforePassingCorner;
    private double drivingSpeed;
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
        allDoorsClosed =true;
        reverseGearEngaged=false;
        numberOfDegreesSteeringWheelTurned=0;
        lightRotarySwitchState=LightRotarySwitchState.OFF;
        leftIndicator=new Indicator();
        headLight=new Light();
        tailLight=new Light();
        rightIndicator=new Indicator();
        leftIndicator.setBlinkingState(Blinking.NONFLASHING);
        rightIndicator.setBlinkingState(Blinking.NONFLASHING);
        timeInTipBlinkingPosition=0;
        numberofFlashCycles=0;
        hazardWarningSwitchOn =false;
        darknessModeSwitchOn =false;
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
            leftIndicator.setBlinkingState(Blinking.FLASHING);
        }else if (pitmanArmState==PitmanArmPosition.UPWARD7){
            rightIndicator.setBlinkingState(Blinking.FLASHING);
        }
        if (ignitionState == IgnitionStatus.KEYINSERTED || ignitionState == IgnitionStatus.NOKEYINSERTED) {
            engageDayTimeRunningLight(false);
            leftIndicator.setBlinkingState(Blinking.NONFLASHING);
            rightIndicator.setBlinkingState(Blinking.NONFLASHING);
            leftIndicator.isTipBlinkingOn(false);
            rightIndicator.isTipBlinkingOn(false);
            if (pitmanArmState == PitmanArmPosition.DOWNWARD5 || pitmanArmState == PitmanArmPosition.UPWARD5) {
                pitmanArmState = PitmanArmPosition.NEUTRAL;
            }
        }
        if (ambientLightingEngaged == true && ignitionState==IgnitionStatus.NOKEYINSERTED) {
            countAmbientLightTime();
        }
        if (ambientLightingEngaged ==true && getHeadLightAmbientLightDuration()<30000){
            stopTimer();
            countAmbientLightTime();
        }
        }
        /*
        This method sets the length of time that the pitman arm is held in a tip-blinking position
         */


    /**
     *
     * @param hazardSwitchState Whether the hazard switch is engaged
     */
    public void pressHazardSwitch(boolean hazardSwitchState){
        this.hazardWarningSwitchOn =hazardSwitchState;
        if (hazardSwitchState==false){
            leftIndicator.setBlinkingState(Blinking.NONFLASHING);
            rightIndicator.setBlinkingState(Blinking.NONFLASHING);
            if (pitmanArmState==PitmanArmPosition.DOWNWARD7){
                leftIndicator.setBlinkingState(Blinking.FLASHING);
            }
            if (pitmanArmState==PitmanArmPosition.UPWARD7){
                rightIndicator.setBlinkingState(Blinking.FLASHING);
            }

        }
        if (hazardSwitchState==true){

                leftIndicator.setBlinkingState(Blinking.FLASHING);
                rightIndicator.setBlinkingState(Blinking.FLASHING);
                leftIndicator.isTipBlinkingOn(false);
                rightIndicator.isTipBlinkingOn(false);
                leftIndicator.setDurationOfHazardCycle(1);
                rightIndicator.setDurationOfHazardCycle(1);

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
        if (hazardWarningSwitchOn ==true){
            rightIndicator.setBlinkingState(Blinking.FLASHING);
            leftIndicator.setBlinkingState(Blinking.FLASHING);
        }

        if (pitmanArmState==PitmanArmPosition.UPWARD7){
            rightIndicator.setBlinkingState(Blinking.FLASHING);
        }
        if (pitmanArmState==PitmanArmPosition.DOWNWARD7){
            leftIndicator.setBlinkingState(Blinking.FLASHING);
        }
    }

    /**
     *
     * @param darknessSwitchEngaged Whether the darkness switch is engaged
     */
    public void pressDarknessSwitch(boolean darknessSwitchEngaged){
        if (darknessSwitchEngaged==true){
            if (ambientLightingEngaged ==true){
                engageAmbientLight(false);
            }

            leftIndicator.isCorneringLightOn(false);
            rightIndicator.isCorneringLightOn(false);
        }else{
            checkCorneringLight();
        }
        this.darknessModeSwitchOn =darknessSwitchEngaged;

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
            timeInTipBlinkingPosition=0;
            setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        }
        if (leftIndicator.getBlinkingState()==Blinking.FLASHING){
            leftIndicator.setNumberofFlashCycles(numberofFlashCycles);
        }
        if (rightIndicator.getBlinkingState()==Blinking.FLASHING){
            rightIndicator.setNumberofFlashCycles(numberofFlashCycles);
        }
    }

    /**
     *
     * @param dayTimeRunningLightEngaged Whether the daytime running light is engaged
     */
    public void engageDayTimeRunningLight(boolean dayTimeRunningLightEngaged){
        this.dayTimeRunningLightEngaged =dayTimeRunningLightEngaged;
        if (dayTimeRunningLightEngaged==true && (ignitionState==IgnitionStatus.KEYINIGNITIONONPOSITION || ignitionState==IgnitionStatus.KEYINSERTED)){
            setHeadLightBeam(Headlight.LOWBEAM);

        }else{
            setHeadLightBeam(Headlight.INACTIVE);

        }
    }

    /**
     *
     * @param ambientLightEngaged Whether the ambient light is engaged
     */
    public void engageAmbientLight(boolean ambientLightEngaged){
        if (darknessModeSwitchOn ==false) {
            this.ambientLightingEngaged = ambientLightEngaged;
        }
        if (ambientLightEngaged==true && darknessModeSwitchOn ==false){
            setHeadLightBeam(Headlight.LOWBEAM);
            countAmbientLightTime();
        }else{
            setHeadLightBeam(Headlight.INACTIVE);
        }
    }

    /**
     *  This method checks the position of the doors in the car which can determine whether to set the low beam headlights
     *   depending on if all doors are shut.
     * @param allDoorsClosed Whether all doors of the car are shut
     */
    public void isAllDoorsClosed(boolean allDoorsClosed){
        this.allDoorsClosed =allDoorsClosed;
        if (ambientLightingEngaged ==true && getHeadLightAmbientLightDuration()<30000 && exteriorBrightnessLuminosity >200) {
            countAmbientLightTime();
        }
        if (allDoorsClosed==true && exteriorBrightnessLuminosity <200 && ambientLightingEngaged ==true){
            setHeadLightBeam(Headlight.INACTIVE);
        }
        if (allDoorsClosed==false && ambientLightingEngaged ==true){
            if (exteriorBrightnessLuminosity <200) {
               setHeadLightBeam(Headlight.LOWBEAM);
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

                rightIndicator.setBlinkingState(Blinking.FLASHING);
                leftIndicator.setBlinkingState(Blinking.NONFLASHING);
            }
            if (position==PitmanArmPosition.DOWNWARD7 || position==PitmanArmPosition.DOWNWARD5){

                setNumberofFlashCycles(0);
                leftIndicator.setBlinkingState(Blinking.FLASHING);
                rightIndicator.setBlinkingState(Blinking.NONFLASHING);

            }
            if (position==PitmanArmPosition.DOWNWARD7 || position==PitmanArmPosition.UPWARD7){
                checkCorneringLight();
                leftIndicator.isTipBlinkingOn(false);
                rightIndicator.isTipBlinkingOn(false);
            }
            if (position==PitmanArmPosition.FORWARD){
                setHeadLightBeam(Headlight.HIGHBEAM);
                checkIncomingVehicleDetectedByCamera();
            }
            if (position==PitmanArmPosition.BACKWARD){

                if (lightRotarySwitchState==LightRotarySwitchState.ON){
                    setHeadLightBeam(Headlight.HIGHBEAM);
                    setHeadLightIlluminationArea(220);
                    setHeadLightLuminousStrength(100);
                }
                if (lightRotarySwitchState==LightRotarySwitchState.AUTO){
                    setHeadLightBeam(Headlight.HIGHBEAM);
                }

            }

            if (position ==PitmanArmPosition.NEUTRAL){
                checkCorneringLight();

                leftIndicator.setBlinkingState(Blinking.NONFLASHING);
                rightIndicator.setBlinkingState(Blinking.NONFLASHING);
                leftIndicator.isTipBlinkingOn(false);
                rightIndicator.isTipBlinkingOn(false);
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
        if (leftIndicator.getBlinkingState()==Blinking.FLASHING && drivingSpeed<10 && leftIndicator.getBeamState()==Headlight.LOWBEAM){
            leftIndicator.isCorneringLightOn(true);
        }
        if (rightIndicator.getBlinkingState()==Blinking.FLASHING && drivingSpeed<10 && rightIndicator.getBeamState()==Headlight.LOWBEAM){
            rightIndicator.isCorneringLightOn(true);
        }
        if (numberOfDegreesSteeringWheelTurned>=10 && leftIndicator.getBlinkingState()==Blinking.NONFLASHING && rightIndicator.getBlinkingState()==Blinking.NONFLASHING
        && drivingSpeed>0 && drivingSpeed <10){
            rightIndicator.isCorneringLightOn(true);
        }
        if (numberOfDegreesSteeringWheelTurned>=10 && leftIndicator.getBlinkingState()==Blinking.NONFLASHING && rightIndicator.getBlinkingState()==Blinking.NONFLASHING
                && drivingSpeed<0 && drivingSpeed >-10){
            leftIndicator.isCorneringLightOn(true);
        }
    }

    /**
     *
     * @param exteriorBrightnessLuminosity The brightness of the outside of the car
     */
    public void setExteriorBrightnessLuminosity(int exteriorBrightnessLuminosity){
        this.exteriorBrightnessLuminosity = exteriorBrightnessLuminosity;
        if (lightRotarySwitchState==LightRotarySwitchState.AUTO && exteriorBrightnessLuminosity <200){
            setHeadLightBeam(Headlight.LOWBEAM);
            countLowBeamTime();
        }
        if (lightRotarySwitchState==LightRotarySwitchState.AUTO && exteriorBrightnessLuminosity >250){
            setHeadLightBeam(Headlight.INACTIVE);
        }
    }

    /**
     * This method ensures that both the left and right indicators are set to the same
     * state when the headlight is active/inactive.
     * @param beamState The state of the headlight
     */
    public void setHeadLightBeamState(Headlight beamState){
        leftIndicator.setBeamState(beamState);
        rightIndicator.setBeamState(beamState);
    }

    /**
     *
     * @param beamState The state of the Headlight
     */
    public void setHeadLightBeam(Headlight beamState){
        if (beamState== Headlight.LOWBEAM){
            setHeadLightBeamState(Headlight.LOWBEAM);
            if (inUSAOrCanada){
                leftIndicator.setBlinkingState(Blinking.FLASHING);
                rightIndicator.setBlinkingState(Blinking.FLASHING);
            }else{
//                tailLight.setLowBeamState(LowBeam.ACTIVE);
            }
        }else if (beamState== Headlight.INACTIVE){
            setHeadLightBeamState(Headlight.INACTIVE);
            if (inUSAOrCanada){
                leftIndicator.setBlinkingState(Blinking.NONFLASHING);
                rightIndicator.setBlinkingState(Blinking.NONFLASHING);
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

        if (ignitionState == IgnitionStatus.KEYINIGNITIONONPOSITION) {
            this.lightRotarySwitchState = lightRotarySwitchState;
            headLight.setLightDimmingPercentage(0);
            if (lightRotarySwitchState == LightRotarySwitchState.ON) {
                leftIndicator.setBlinkingState(Blinking.NONFLASHING);
                rightIndicator.setBlinkingState(Blinking.NONFLASHING);
                checkCorneringLight();
                setHeadLightBeam(Headlight.LOWBEAM);
            }
            if (lightRotarySwitchState == LightRotarySwitchState.OFF) {
                headLight.setLightDimmingPercentage(0);
                setHeadLightBeam(Headlight.INACTIVE);
            }


        }
        if (ignitionState == IgnitionStatus.NOKEYINSERTED) {
            if (this.lightRotarySwitchState == LightRotarySwitchState.OFF) {
                this.lightRotarySwitchState = lightRotarySwitchState;
                if (lightRotarySwitchState == LightRotarySwitchState.ON) {
                    setParkingLightState();
                    headLight.setLightDimmingPercentage(50);
                    setHeadLightBeam(Headlight.LOWBEAM);
                }
            }
            this.lightRotarySwitchState=lightRotarySwitchState;
            if (lightRotarySwitchState==LightRotarySwitchState.AUTO){
                setHeadLightBeam(Headlight.INACTIVE);
            }
            if (lightRotarySwitchState==LightRotarySwitchState.OFF){
                setHeadLightBeam(Headlight.INACTIVE);
            }
        }

        this.lightRotarySwitchState=lightRotarySwitchState;
    }

    /**
     *
     * @param durationBeforePassingCorner The time taken for a car to pass a corner
     */
    public void setDurationBeforePassingCorner(int durationBeforePassingCorner){
        this.durationBeforePassingCorner = durationBeforePassingCorner;
        if (durationBeforePassingCorner ==5){
            leftIndicator.isCorneringLightOn(false);
            rightIndicator.isCorneringLightOn(false);
        }
    }

    /**
     *  This method decides how the indicator will behave when the pitman arm is tipped into a tip-blinking
     *  position for a certain amount of time.
     * @param position The position of the pitman arm
     * @param timeInTipBlinkingPosition The time that the pitman arm is held in a tip-blinking position
     */
    public void tipPitmanArm(PitmanArmPosition position,double timeInTipBlinkingPosition) {
        setNumberofFlashCycles(0);

        if (position == PitmanArmPosition.UPWARD5) {
            setPitmanArmPosition(PitmanArmPosition.UPWARD5);
            if (timeInTipBlinkingPosition < 500) {
                rightIndicator.isTipBlinkingOn(true);
            }else{
                rightIndicator.isTipBlinkingOn(false);
            }
        }
        if (position == PitmanArmPosition.DOWNWARD5) {
            setPitmanArmPosition(PitmanArmPosition.DOWNWARD5);
            if (timeInTipBlinkingPosition < 500) {
                leftIndicator.isTipBlinkingOn(true);
            }else{
                leftIndicator.isTipBlinkingOn(false);
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
                leftIndicator.setBeamState(Headlight.LOWBEAM);
                rightIndicator.setLightDimmingPercentage(10);

            }else if (pitmanArmState==PitmanArmPosition.UPWARD5 || pitmanArmState==PitmanArmPosition.UPWARD7){
                parkingLightEngaged=true;
                rightIndicator.setLightDimmingPercentage(10);
                leftIndicator.setLightDimmingPercentage(10);
                rightIndicator.setBeamState(Headlight.LOWBEAM);
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
            if (leftIndicator.getBlinkingState()==Blinking.FLASHING){
                rightIndicator.isCorneringLightOn(true);
            }else if (rightIndicator.getBlinkingState()==Blinking.FLASHING){
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
    public void checkIncomingVehicleDetectedByCamera() {
        if (incomingVehicleDetectedByCamera == false && drivingSpeed > 30 && getHeadLightBeamState() == Headlight.LOWBEAM) {
            calculateIlluminationArea();
            calculateLuminousStrength();
            countTimeToSetHighBeam();
        } else if (incomingVehicleDetectedByCamera == true && getHeadLightBeamState() == Headlight.HIGHBEAM) {
            countTimeToSetLowBeam();
        } else if (incomingVehicleDetectedByCamera == false && getHeadLightBeamState() == Headlight.LOWBEAM) {
            countTimeToSetHighBeam();

        }
    }
    /**
    This method darkens the indicator to ensure a cycle is finished when there is a change in the pitman arm position
    and before there is a change to the state of the indicator.
     **/
    public void darkenIndicators(){
        if (leftIndicator.getBlinkingState()==Blinking.FLASHING) {
            rightIndicator.setIndicatorBulbState(IndicatorBulb.DARK);
        }
        if (rightIndicator.getBlinkingState()==Blinking.FLASHING) {
            leftIndicator.setIndicatorBulbState(IndicatorBulb.DARK);
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
    public IndicatorBulb getFlashState(){
        if (rightIndicator.getBlinkingState()==Blinking.FLASHING){
            return rightIndicator.getIndicatorBulbState();
        }
        if (leftIndicator.getBlinkingState()==Blinking.FLASHING){
            return leftIndicator.getIndicatorBulbState();
        }
        return null;

    }

    /**
     *
     * @return The state of the headlight as Lowbeam, Highbeam or Inactive.
     */
    public Headlight getHeadLightBeamState(){
        if (leftIndicator.getBeamState()==rightIndicator.getBeamState()){
            return leftIndicator.getBeamState();
        }
        return null;
    }

    /**
     *
     * @return The brightness of the outside of the car
     */
    public int getExteriorBrightnessLuminosity(){
        return exteriorBrightnessLuminosity;
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
        if (rightIndicator.getBlinkingState() == Blinking.FLASHING) {
            if (rightIndicator.getIndicatorBulbState() == IndicatorBulb.BRIGHT) {
                rightIndicator.setIndicatorBulbState(IndicatorBulb.DARK);
            } else {
                rightIndicator.setIndicatorBulbState(IndicatorBulb.BRIGHT);
            }
        }
        if (leftIndicator.getBlinkingState() == Blinking.FLASHING) {
            if (leftIndicator.getIndicatorBulbState() == IndicatorBulb.BRIGHT) {
                leftIndicator.setIndicatorBulbState(IndicatorBulb.DARK);
            } else {
                leftIndicator.setIndicatorBulbState(IndicatorBulb.BRIGHT);
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
    public boolean getHazardWarningSwitchOn(){
        return hazardWarningSwitchOn;
    }

    public int getLengthOfHazardCycle() {
        if (leftIndicator.getDurationOfHazardCycle() == rightIndicator.getDurationOfHazardCycle()) {
            return leftIndicator.getDurationOfHazardCycle();
        }
        return 0;
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
    public int getTimeInTipBlinkingPosition(){
        return timeInTipBlinkingPosition;
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
            return rightIndicator.getBlinkingState();
        }
        if (direction.equals("Left")){
            return leftIndicator.getBlinkingState();
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
            return rightIndicator.getTipBlinkingOccurring();
        }
        if (direction.equals("Left")){
            return leftIndicator.getTipBlinkingOccurring();
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
        timeInTipBlinkingPosition=0;
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timeInTipBlinkingPosition++;
            }
        }, 1,1);

    }

    /**
     * Uses a timer to count the time it takes for a car to pass a corner when driving
     */
    public void countDurationOfPassingCornerTime(){
        durationBeforePassingCorner =0;
        timer=new Timer();
        leftIndicator.setBlinkingState(Blinking.NONFLASHING);
        rightIndicator.setBlinkingState(Blinking.NONFLASHING);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                durationBeforePassingCorner++;
                if (durationBeforePassingCorner ==5000){
                    leftIndicator.isCorneringLightOn(false);
                    rightIndicator.isCorneringLightOn(false);
                    leftIndicator.setBlinkingState(Blinking.NONFLASHING);
                    rightIndicator.setBlinkingState(Blinking.NONFLASHING);
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
                        setHeadLightBeam(Headlight.HIGHBEAM);
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
                    setHeadLightBeam(Headlight.HIGHBEAM);
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
                setHeadLightBeam(Headlight.LOWBEAM);
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
        leftIndicator.setAmbientLightDuration(0);
        rightIndicator.setAmbientLightDuration(0);
        checkAmbientLightDuration();
        timer=new Timer();
        setHeadLightBeam(Headlight.LOWBEAM);
        timer.schedule(new TimerTask() {
            int x=leftIndicator.getAmbientLightDuration();
            @Override
            public void run() {
                x++;
                leftIndicator.setAmbientLightDuration(x);
                rightIndicator.setAmbientLightDuration(x);
                checkAmbientLightDuration();
                if (getHeadLightAmbientLightDuration()==30000){
                    setHeadLightBeam(Headlight.INACTIVE);
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
            setHeadLightBeam(Headlight.INACTIVE);
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
                if (lowBeamHeadlightDuration>3000 && exteriorBrightnessLuminosity >250){
                    setHeadLightBeam(Headlight.INACTIVE);
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
            setHeadLightIlluminationArea((int)drivingSpeed+100);
        }
    }

    /**
     * Calculates the intensity of the luminosity of the headlights based on driving speed
     */
    public void calculateLuminousStrength(){
        if (drivingSpeed>=120){
            setHeadLightLuminousStrength(100);
        }else{
            setHeadLightLuminousStrength((int)drivingSpeed-10);
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
        if (leftIndicator.getBlinkingState()==Blinking.FLASHING){
            return leftIndicator.getNumberofFlashCycles();
        }
        if (rightIndicator.getBlinkingState()==Blinking.FLASHING){
            return rightIndicator.getNumberofFlashCycles();
        }
        return 0;
    }

    /**
     *
     * @return Whether daytime running light is active
     */
    public boolean getDayTimeRunningLightEngaged(){
        return dayTimeRunningLightEngaged;
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
    public boolean getAmbientLightingEngaged(){
        return ambientLightingEngaged;
    }

    /**
     *
     * @return Whether darkness mode is active
     */
    public boolean getDarknessModeSwitchOn(){
        return darknessModeSwitchOn;
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

