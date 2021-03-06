package uk.ac.ucl.cs.comp0110;
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
 * @author Arzhan Tong
 */
public class Car {
    private IgnitionStatus ignitionState;
    private PitmanArmPosition pitmanArmState;
    private Indicator leftIndicator;
    private Indicator rightIndicator;
    private Timer timer;
    private boolean hazardWarningSwitchOn;
    private boolean inUSAOrCanada;
    private boolean allDoorsClosed;
    private boolean dayTimeRunningLightEngaged;
    private boolean ambientLightingEngaged;
    private int exteriorBrightnessLuminosity;
    private int lowBeamHeadlightDuration;
    private boolean darknessModeSwitchOn;
    private int numberofFlashCycles;
    private int durationBeforePassingCorner;
    private double drivingSpeed;
    private int numberOfDegreesSteeringWheelTurned;
    private LightRotarySwitchState lightRotarySwitchState;
    private boolean reverseGearEngaged;
    private boolean parkingLightEngaged;
    private boolean incomingVehicleDetectedByCamera;
    private static int timeForHeadLightToIlluminate;
    private TimeMachine timeMachine;
    private int timeInTipBlinkingPosition;

    public Car(TimeMachine timeMachine) {
        timeForHeadLightToIlluminate = 0;
        parkingLightEngaged = false;
        incomingVehicleDetectedByCamera = false;
        allDoorsClosed = true;
        reverseGearEngaged = false;
        numberOfDegreesSteeringWheelTurned = 0;
        lightRotarySwitchState = LightRotarySwitchState.OFF;
        leftIndicator = new Indicator();
        rightIndicator = new Indicator();
        leftIndicator.setBlinkingState(Blinking.NONFLASHING);
        rightIndicator.setBlinkingState(Blinking.NONFLASHING);
        timeInTipBlinkingPosition=0;
        numberofFlashCycles = 0;
        hazardWarningSwitchOn = false;
        darknessModeSwitchOn = false;
        this.timeMachine=timeMachine;
        setHeadLightBeam(Headlight.INACTIVE);
    }

    /**
     * This method sets the ignition status of the car and sets any states that should be set for the
     * indicators based on ignition status.
     * @param ignitionState The ignition state of the car
     */
    public void isIgnitionOn(IgnitionStatus ignitionState) {
        this.ignitionState = ignitionState;
        isVehicleParking();
        checkDayTimeRunningLightActive();
        if (pitmanArmState==PitmanArmPosition.DOWNWARD7){
            leftIndicator.setBlinkingState(Blinking.FLASHING);
        }else if (pitmanArmState==PitmanArmPosition.UPWARD7){
            rightIndicator.setBlinkingState(Blinking.FLASHING);
        }
        if (ignitionState == IgnitionStatus.KEYINSERTED || ignitionState == IgnitionStatus.NOKEYINSERTED) {
            leftIndicator.setBlinkingState(Blinking.NONFLASHING);
            rightIndicator.setBlinkingState(Blinking.NONFLASHING);
            leftIndicator.isTipBlinkingOn(false);
            rightIndicator.isTipBlinkingOn(false);
            if (pitmanArmState == PitmanArmPosition.DOWNWARD5 || pitmanArmState == PitmanArmPosition.UPWARD5) {
                pitmanArmState = PitmanArmPosition.NEUTRAL;
            }
        }
        if (ambientLightingEngaged && ignitionState==IgnitionStatus.NOKEYINSERTED) {

           countAmbientLightTime();

        }
        if (ignitionState==IgnitionStatus.KEYINIGNITIONONPOSITION){
            ambientLightingEngaged=false;

        }
        if (ambientLightingEngaged && getHeadLightAmbientLightDuration()<30){
            countAmbientLightTime();
        }

        }


    /**
     *
     * @param hazardWarningSwitchOn Whether the hazard switch is engaged
     */
    public void pressHazardSwitch(boolean hazardWarningSwitchOn){
        this.hazardWarningSwitchOn =hazardWarningSwitchOn;
        if (!hazardWarningSwitchOn){
            leftIndicator.setBlinkingState(Blinking.NONFLASHING);
            rightIndicator.setBlinkingState(Blinking.NONFLASHING);
            if (pitmanArmState==PitmanArmPosition.DOWNWARD7){
                leftIndicator.setBlinkingState(Blinking.FLASHING);
            }
            if (pitmanArmState==PitmanArmPosition.UPWARD7){
                rightIndicator.setBlinkingState(Blinking.FLASHING);
            }

        }
        if (hazardWarningSwitchOn){

                leftIndicator.setBlinkingState(Blinking.FLASHING);
                rightIndicator.setBlinkingState(Blinking.FLASHING);
                leftIndicator.isTipBlinkingOn(false);
                rightIndicator.isTipBlinkingOn(false);

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
        if (hazardWarningSwitchOn){
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
        if (darknessSwitchEngaged){
            if (ambientLightingEngaged){
                pressAmbientLightButton(false);
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

        if (inUSAOrCanada){
            leftIndicator.setLightDimmingPercentage(50);
            rightIndicator.setLightDimmingPercentage(50);
        }else{
            leftIndicator.setLightDimmingPercentage(0);
            rightIndicator.setLightDimmingPercentage(0);
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
            //timeInTipBlinkingPosition=0;
            setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        }
        if (leftIndicator.getBlinkingState()==Blinking.FLASHING){
            leftIndicator.setNumberofFlashCycles(numberofFlashCycles);
        }
        if (rightIndicator.getBlinkingState()==Blinking.FLASHING){
            rightIndicator.setNumberofFlashCycles(numberofFlashCycles);
        }
    }
    public void setIndicatorToBlink(String direction){
        if (direction.equals("right")){
            rightIndicator.setBlinkingState(Blinking.FLASHING);
        }
        if (direction.equals("left")){
            leftIndicator.setBlinkingState(Blinking.FLASHING);
        }
    }
    public void checkDayTimeRunningLightActive(){
        if (dayTimeRunningLightEngaged && (ignitionState==IgnitionStatus.KEYINIGNITIONONPOSITION || ignitionState==IgnitionStatus.KEYINSERTED) && lightRotarySwitchState!=LightRotarySwitchState.AUTO){
            setHeadLightBeam(Headlight.LOWBEAM);
        }else{
            setHeadLightBeam(Headlight.INACTIVE);

        }
    }
    /**
     *
     * @param dayTimeRunningLightEngaged Whether the daytime running light is engaged
     */
    public void pressDayTimeRunningLightButton(boolean dayTimeRunningLightEngaged){
        this.dayTimeRunningLightEngaged =dayTimeRunningLightEngaged;
        checkDayTimeRunningLightActive();
    }

    /**
     *
     * @param ambientLightEngaged Whether the ambient light is engaged
     */
    public void pressAmbientLightButton(boolean ambientLightEngaged){

        if (!darknessModeSwitchOn) {
            this.ambientLightingEngaged = ambientLightEngaged;
        }
        if (ambientLightEngaged && !darknessModeSwitchOn && lightRotarySwitchState!=LightRotarySwitchState.AUTO){
            setHeadLightBeam(Headlight.LOWBEAM);
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
        if (ambientLightingEngaged && timeMachine.getNumSeconds()<30 && exteriorBrightnessLuminosity >200) {
            //countAmbientLightTime(35);
            //t1.start();

        }
        if (allDoorsClosed && exteriorBrightnessLuminosity <200 && ambientLightingEngaged){
            setHeadLightBeam(Headlight.INACTIVE);
        }
        if (!allDoorsClosed && ambientLightingEngaged){
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
        leftIndicator.isTipBlinkingOn(false);
        rightIndicator.isTipBlinkingOn(false);
        leftIndicator.isCorneringLightOn(false);
        rightIndicator.isCorneringLightOn(false);
        isVehicleParking();
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

                leftIndicator.setBlinkingState(Blinking.NONFLASHING);
                rightIndicator.setBlinkingState(Blinking.NONFLASHING);
                checkCorneringLight();
                leftIndicator.isTipBlinkingOn(false);
                rightIndicator.isTipBlinkingOn(false);
                darkenIndicators();
            }
        }
        checkReverseGearEngaged();

    }

    /**
     *
     * @param drivingSpeed The speed of the car
     */
    public void setDrivingSpeed(int drivingSpeed){
        this.drivingSpeed=drivingSpeed;
        leftIndicator.isCorneringLightOn(false);
        rightIndicator.isCorneringLightOn(false);
        if (getIndicatorBeamState()== Headlight.LOWBEAM){
            checkCorneringLight();
        }
    }
    /**
    This method sets the state of the cornering light which may depend on other factors such as
    degrees turned by steering wheel and driving speed of the car.
     */
    public void checkCorneringLight(){
        if (leftIndicator.getBlinkingState()==Blinking.FLASHING && drivingSpeed<10 && leftIndicator.getBeamState()==Headlight.LOWBEAM){
            leftIndicator.isCorneringLightOn(true);
        }
        if (rightIndicator.getBlinkingState()==Blinking.FLASHING && drivingSpeed<10 && rightIndicator.getBeamState()==Headlight.LOWBEAM){
            rightIndicator.isCorneringLightOn(true);
        }
        if (numberOfDegreesSteeringWheelTurned>=10 && leftIndicator.getBlinkingState()==Blinking.NONFLASHING && rightIndicator.getBlinkingState()==Blinking.NONFLASHING
        && drivingSpeed>0 && drivingSpeed <10){
            rightIndicator.isCorneringLightOn(true);
            leftIndicator.isCorneringLightOn(false);
        }
        if (numberOfDegreesSteeringWheelTurned<=-10 && leftIndicator.getBlinkingState()==Blinking.NONFLASHING && rightIndicator.getBlinkingState()==Blinking.NONFLASHING
                && drivingSpeed>0 && drivingSpeed<10){

            leftIndicator.isCorneringLightOn(true);
            rightIndicator.isCorneringLightOn(false);
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
            countTimeToSetLowBeam();
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
    public void setIndicatorBeamState(Headlight beamState){
        leftIndicator.setBeamState(beamState);
        rightIndicator.setBeamState(beamState);
        if (beamState==Headlight.INACTIVE){
            leftIndicator.isTailLightOn(false);
            rightIndicator.isTailLightOn(false);
        }else{
            leftIndicator.isTailLightOn(true);
            rightIndicator.isTailLightOn(true);
        }
    }
    /**
     * This method ensures that both the left and right indicators are set to the same
     * state regarding blinking
     * @param blinkingState The state of whether indicator is blinking
     */
    public void setIndicatorBlinkingState(Blinking blinkingState){
        leftIndicator.setBlinkingState(blinkingState);
        rightIndicator.setBlinkingState(blinkingState);
    }

    /**
     *
     * @param beamState The state of the Headlight
     */
    public void setHeadLightBeam(Headlight beamState){
        if (beamState== Headlight.LOWBEAM){
            setIndicatorBeamState(Headlight.LOWBEAM);
            if (inUSAOrCanada){
                setIndicatorBlinkingState(Blinking.FLASHING);
            }

        }else if (beamState== Headlight.INACTIVE){
            setIndicatorBeamState(Headlight.INACTIVE);
            if (inUSAOrCanada){
                setIndicatorBlinkingState(Blinking.NONFLASHING);
            }
        }else if (beamState==Headlight.HIGHBEAM){
            setIndicatorBeamState(Headlight.HIGHBEAM);
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
        setHeadLightLightDimmingPercentage(0);

        if (ignitionState == IgnitionStatus.KEYINIGNITIONONPOSITION || ignitionState==IgnitionStatus.KEYINSERTED) {
            this.lightRotarySwitchState = lightRotarySwitchState;
            setHeadLightLightDimmingPercentage(0);
            if (lightRotarySwitchState == LightRotarySwitchState.ON) {
                leftIndicator.setBlinkingState(Blinking.NONFLASHING);
                rightIndicator.setBlinkingState(Blinking.NONFLASHING);
                checkCorneringLight();
                setHeadLightBeam(Headlight.LOWBEAM);
            }
            if (lightRotarySwitchState == LightRotarySwitchState.OFF) {
                setHeadLightLightDimmingPercentage(0);
                setHeadLightBeam(Headlight.INACTIVE);
            }
            if (lightRotarySwitchState==LightRotarySwitchState.AUTO && (ignitionState==IgnitionStatus.KEYINSERTED || ignitionState==IgnitionStatus.NOKEYINSERTED)) {
                setHeadLightLightDimmingPercentage(0);
                setHeadLightBeam(Headlight.INACTIVE);
            }
        }
        if (ignitionState == IgnitionStatus.NOKEYINSERTED) {
            if (this.lightRotarySwitchState == LightRotarySwitchState.OFF) {
                this.lightRotarySwitchState = lightRotarySwitchState;
                if (lightRotarySwitchState == LightRotarySwitchState.ON) {
                    isVehicleParking();
                    setHeadLightLightDimmingPercentage(50);
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
     * @param timeInTipBlinkingPosition The time that the pitman arm is held in a tip-blinking position
     */
    public void tipPitmanArm(int timeInTipBlinkingPosition){
        setNumberofFlashCycles(0);

        if (pitmanArmState == PitmanArmPosition.UPWARD5) {
            if (timeInTipBlinkingPosition<5 && ignitionState==IgnitionStatus.KEYINIGNITIONONPOSITION) {
                rightIndicator.isTipBlinkingOn(true);
            }else{
                rightIndicator.isTipBlinkingOn(false);
            }
        }
        if (pitmanArmState == PitmanArmPosition.DOWNWARD5) {
            if (timeInTipBlinkingPosition<5 &&ignitionState==IgnitionStatus.KEYINIGNITIONONPOSITION) {
                leftIndicator.isTipBlinkingOn(true);

            }else{
                leftIndicator.isTipBlinkingOn(false);
            }
        }
    }
    /**
        This method determines whether the car is parking and therefore will have an affect on the determine the state
        of the indicator(s) and brightness
     */
    public void isVehicleParking(){
        if (ignitionState==IgnitionStatus.NOKEYINSERTED && lightRotarySwitchState==LightRotarySwitchState.ON){
            if (pitmanArmState==PitmanArmPosition.DOWNWARD5 || pitmanArmState==PitmanArmPosition.DOWNWARD7){
                parkingLightEngaged=true;
                leftIndicator.setLightDimmingPercentage(10);
                leftIndicator.isTailLightOn(true);
                leftIndicator.setBeamState(Headlight.LOWBEAM);
                rightIndicator.setBeamState(Headlight.INACTIVE);
                rightIndicator.setLightDimmingPercentage(10);

            }else if (pitmanArmState==PitmanArmPosition.UPWARD5 || pitmanArmState==PitmanArmPosition.UPWARD7){
                parkingLightEngaged=true;
                rightIndicator.setLightDimmingPercentage(10);
                leftIndicator.setLightDimmingPercentage(10);
                rightIndicator.setBeamState(Headlight.LOWBEAM);
                leftIndicator.setBeamState(Headlight.INACTIVE);
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
        if (reverseGearEngaged){
            if (leftIndicator.getBlinkingState()==Blinking.FLASHING){
                rightIndicator.isCorneringLightOn(true);
                leftIndicator.isCorneringLightOn(false);
            }else if (rightIndicator.getBlinkingState()==Blinking.FLASHING){
                leftIndicator.isCorneringLightOn(true);
                rightIndicator.isCorneringLightOn(false);
            }
        }else{
            leftIndicator.isCorneringLightOn(false);
            rightIndicator.isCorneringLightOn(false);
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
        if (!incomingVehicleDetectedByCamera && drivingSpeed > 30 && getIndicatorBeamState() == Headlight.LOWBEAM) {
            calculateIlluminationArea();
            calculateLuminousStrength();
            countTimeToSetHighBeam();
        } else if (incomingVehicleDetectedByCamera && getIndicatorBeamState() == Headlight.HIGHBEAM) {
            countTimeToSetLowBeam();
        } else if (!incomingVehicleDetectedByCamera && getIndicatorBeamState() == Headlight.LOWBEAM) {
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
    public Headlight getIndicatorBeamState(){
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

    /**
     *
     * @param direction The direction that the driver is signalling
     * @return The percentage of the brightness dimming of the indicator that corresponds to the direction indicated
     */
    public int getDimmedLightStatus(String direction){
        if (direction.equals("right")){
            return rightIndicator.getLightDimmingPercentage();
        }
        if (direction.equals("left")){
            return leftIndicator.getLightDimmingPercentage();
        }
        return 0;
    }


    /**
     *
     * @return The length of time the pitman arm is held in a tip-blinking position
     */

    /**
     *
     * @param direction The direction that the driver is signalling
     * @return The blinking state of the indicator that corresponds to the direction the driver is signalling
     */
    public Blinking getBlinkingState(String direction){
        if (direction.equals("right")){
            return rightIndicator.getBlinkingState();
        }
        if (direction.equals("left")){
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
    public boolean checkFlashingCyclesAreOccurring(String direction){
        if (direction.equals("right")){
            return rightIndicator.getTipBlinkingOccurring();
        }
        if (direction.equals("left")){
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
     * @return Whether the parking light of the car is engaged
     */
    public boolean getParkingLightEngaged(){
        return parkingLightEngaged;
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
    public void checkToResetAmbientLightDuration(boolean doorsClosed,IgnitionStatus ignitionState){
        if (doorsClosed==this.allDoorsClosed && ignitionState==this.getIgnitionState()){
            setHeadLightBeam(Headlight.INACTIVE);
        }

    }
    public TimeMachine getTimeMachine(){
        return timeMachine;
    }
    public int getTimeInTipBlinkingPosition(){
        return timeInTipBlinkingPosition;
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

        if (!incomingVehicleDetectedByCamera) {
            timer.schedule(new TimerTask() {
                int x=0;
                @Override
                public void run() {
                    x++;
                    ++Car.timeForHeadLightToIlluminate;
                    if (Car.timeForHeadLightToIlluminate>2){
                        setHeadLightBeam(Headlight.HIGHBEAM);
                        Car.timeForHeadLightToIlluminate=x;
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
                calculateLuminousStrength();
                timeForHeadLightToIlluminate++;
                stopTimer();
            }
        }, 1,1);

    }
    public void checkToSetHighBeam(){
        if(!incomingVehicleDetectedByCamera && getIndicatorBeamState() == Headlight.LOWBEAM){
            setHeadLightBeam(Headlight.HIGHBEAM);
        }
    }

    /**
     * Uses a timer to count how long the car has ambient light on
     */
    public void countAmbientLightTime() {
        leftIndicator.setAmbientLightDuration(0);
        rightIndicator.setAmbientLightDuration(0);
        checkAmbientLightDuration();
        timer = new Timer();
        setHeadLightBeam(Headlight.LOWBEAM);
        timer.schedule(new TimerTask() {
            int x = leftIndicator.getAmbientLightDuration();

            @Override
            public void run() {
                x++;
                leftIndicator.setAmbientLightDuration(x);
                rightIndicator.setAmbientLightDuration(x);
                checkAmbientLightDuration();
                if (getHeadLightAmbientLightDuration() == 30000) {
                    setHeadLightBeam(Headlight.INACTIVE);
                    stopTimer();
                }
            }
        }, 1, 1);

    }



    /**
     * Determines whether to set headlight to inactive based on time that ambient light is on
     */
    public void checkAmbientLightDuration(){
        if (getHeadLightAmbientLightDuration()>30){
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
     * @param illuminationArea The area that is to be illuminated by the headlights
     */
    public void setHeadLightIlluminationArea(int illuminationArea){
        leftIndicator.setIlluminationArea(illuminationArea);
        rightIndicator.setIlluminationArea(illuminationArea);
    }
    /**
     *
     * @param lightDimmingPercentage The percentage of how much to dim the light
     */
    public void setHeadLightLightDimmingPercentage(int lightDimmingPercentage){
        leftIndicator.setLightDimmingPercentage(lightDimmingPercentage);
        rightIndicator.setLightDimmingPercentage(lightDimmingPercentage);
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
public boolean indicatorsAreBlinking(){
    return leftIndicator.getBlinkingState() == Blinking.FLASHING && rightIndicator.getBlinkingState() == Blinking.FLASHING;
}
public boolean checkCorneringLightIsOn(String direction){
        if (direction.equals("left")){
            return leftIndicator.getCorneringLightState();
        }
        if (direction.equals("right")){
            return rightIndicator.getCorneringLightState();
        }
       return false;
}
public void isCorneringLightOn(String directionOfCorneringLight,boolean corneringLightOn){
        if (directionOfCorneringLight.equals("left")){
            leftIndicator.isCorneringLightOn(corneringLightOn);
        }
        if (directionOfCorneringLight.equals("right")){
            rightIndicator.isCorneringLightOn(corneringLightOn);
        }
}
}

