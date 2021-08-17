package uk.ac.ucl.cs.comp0110;
import java.awt.event.*;

public class CarController {
    public CarView view;
    private Car model;

    private double lengthOfTimeHeld=0;
    public CarController(CarView view, Car model) {
        this.view = view;
        this.model = model;

    }

    public void addButtonFunctions() {
        view.getHazardSwitch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hazardSwitchPressed();
            }
        });
        view.getLeftDirection().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                leftDirectionPressed();
            }
        });
        view.getRightDirection().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rightDirectionPressed();
            }
        });
        view.getSoldInUKOrCanada().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                soldInUKOrCanadaPressed();
            }
        });
        view.getNoKeyInserted().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noKeyInsertedPressed();
            }
        });
        view.getKeyInPosition().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyInPosition();
            }
        });
        view.getKeyInserted().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyInsertedPressed();
            }
        });
        view.getLightRotarySwitch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lightRotarySwitchPressed();
            }
        });
        view.getLeftTipBlinking().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.countTimeInPosition();
            }
            @Override
            public void mouseReleased(MouseEvent e) {
                leftTipBlinkingPressed();

            }
        });
        view.getRightTipBlinking().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                model.countTimeInPosition();

            }
            @Override
            public void mouseReleased(MouseEvent e) {
                rightTipBlinkingPressed();
            }
        });
        view.getDoorPosition().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                doorPressed();
            }
        });
        view.getAmbientLight().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ambientLightPressed();
            }
        });
        view.getDayTimeRunningLight().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dayTimeRunningLightPressed();
            }
        });
        view.getExteriorBrightness().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exteriorBrightnessPressed();
            }
        });
        view.getDarknessSwitch().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                darknessSwitchPressed();
            }
        });
        view.getSpeedOfCar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carDuration();
            }
        });
        view.getPassCorner().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                passedCorner();
            }
        });
        view.getNumberOfDegreesSteeringWheelTurned().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberOfDegreesSteeringWheelTurned();
            }
        });
        view.getReverseGear().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reverseGearEngaged();
            }
        });
        view.getPitmanArmRight().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                highBeamPressed();
            }
        });
        view.getPitmanArmLeft().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pitmanArmMovedLeft();

            }
        });
        view.getIncomingVehicleDetectedByCamera().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incomingVehicleDetected();
            }
        });
    }


    public void leftDirectionPressed() {

        if (model.getBlinkingState("Left") == Blinking.FLASHING && model.getHazardWarningSwitchOn()==false) {
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        } else {
            model.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);
        }
    }
    public void rightDirectionPressed() {

        if (model.getBlinkingState("Right") == Blinking.FLASHING && model.getHazardWarningSwitchOn()==false) {
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);

        }else{
            model.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
        }

    }
    public void leftTipBlinkingPressed(){

        if (model.getBlinkingState("Left")==Blinking.FLASHING && model.getFlashingCycles("Left")==true){
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
            model.stopTimer();
        }else{
            model.stopTimer();
            model.tipPitmanArm(model.getTimeInTipBlinkingPosition());
        }
    }
    public void rightTipBlinkingPressed(){

        if (model.getBlinkingState("Right")==Blinking.FLASHING && model.getFlashingCycles("Right")==true){
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
            model.stopTimer();
        }else{
            model.stopTimer();
            model.tipPitmanArm(model.getTimeInTipBlinkingPosition());
        }
    }
    public void hazardSwitchPressed(){
        if (model.getHazardWarningSwitchOn()==false){
            model.pressHazardSwitch(true);
        }else{
            model.pressHazardSwitch(false);

        }
    }

    public void soldInUKOrCanadaPressed(){
        if (model.getDimmedLightStatus("Left")==0 || model.getDimmedLightStatus("Right")==0){
            model.setInUSAOrCanada(true);
        }else{
            model.setInUSAOrCanada(false);
        }
    }
    public void noKeyInsertedPressed(){
        model.isIgnitionOn(IgnitionStatus.NOKEYINSERTED);
    }
    public void keyInsertedPressed(){
        model.isIgnitionOn(IgnitionStatus.KEYINSERTED);
    }
    public void keyInPosition(){
        model.isIgnitionOn(IgnitionStatus.KEYINIGNITIONONPOSITION);
        model.checkPitmanArmState();
    }
    public void lightRotarySwitchPressed(){
        if (view.getLightRotarySwitch().getSelectedItem().equals("On")){
            model.turnLightRotarySwitch(LightRotarySwitchState.ON);
        }
        if (view.getLightRotarySwitch().getSelectedItem().equals("Off")){
            model.turnLightRotarySwitch(LightRotarySwitchState.OFF);
        }
        if (view.getLightRotarySwitch().getSelectedItem().equals("Auto")){
            model.turnLightRotarySwitch(LightRotarySwitchState.AUTO);
        }
    }
    public void dayTimeRunningLightPressed(){
        if (model.getDayTimeRunningLightEngaged()==false){
            model.engageDayTimeRunningLight(true);
        }else{
            model.engageDayTimeRunningLight(false);
        }
    }
    public void doorPressed(){
        if (model.getAllDoorsClosed()==true){
            model.isAllDoorsClosed(false);
        }else{
            model.isAllDoorsClosed(true);
        }
    }
    public void ambientLightPressed(){
        if (model.getAmbientLightingEngaged()==true){
            model.engageAmbientLight(false);
        }else{
            model.engageAmbientLight(true);
        }
    }
    public void exteriorBrightnessPressed(){
        model.setExteriorBrightnessLuminosity(Integer.parseInt(view.getExteriorBrightness().getText()));
    }
    public void darknessSwitchPressed(){
        if (model.getDarknessModeSwitchOn()==true){
            model.pressDarknessSwitch(false);
        }else{
            model.pressDarknessSwitch(true);
        }
    }
    public void carDuration(){
        model.setDrivingSpeed(Integer.parseInt(view.getSpeedOfCar().getText()));
    }
    public void passedCorner(){
        model.countDurationOfPassingCornerTime();
    }
    public void numberOfDegreesSteeringWheelTurned(){
        model.setDegreesSteeringWheelTurned(Integer.parseInt(view.getNumberOfDegreesSteeringWheelTurned().getText()));
    }
    public void reverseGearEngaged(){
        if (model.getReverseGearEngaged()==false){
            model.isReverseGearEngaged(true);
        }else{
            model.isReverseGearEngaged(false);
        }
    }
    public void highBeamPressed(){
        if (model.getPitmanArmState()!=PitmanArmPosition.FORWARD){
            model.setPitmanArmPosition(PitmanArmPosition.FORWARD);
        }else{
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        }
    }
    public void pitmanArmMovedLeft(){
        if (model.getPitmanArmState()!=PitmanArmPosition.BACKWARD){
            model.setPitmanArmPosition(PitmanArmPosition.BACKWARD);
        }else{
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        }
    }
    public void incomingVehicleDetected(){
        if (model.getIncomingVehicleDetectedByCamera()==false){
            model.isIncomingVehicleDetectedByCamera(true);
        }else{
            model.isIncomingVehicleDetectedByCamera(false);
        }
    }
}
