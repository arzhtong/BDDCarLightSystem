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
    }


    public void leftDirectionPressed() {

        if (model.getBlinkingState("Left") == Blinking.FLASHING && model.getFlashingCycles("Left")==false) {
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        } else {
            model.setNumberofFlashCycles(0);
            model.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);
            model.getLeftIndicator().setCycle(false);
            model.getRightIndicator().setCycle(false);
        }
    }
    public void rightDirectionPressed() {

        if (model.getBlinkingState("Right") == Blinking.FLASHING && model.getFlashingCycles("Right")==false) {
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);

        }else{
            model.setNumberofFlashCycles(0);
            model.setPitmanArmPosition(PitmanArmPosition.UPWARD7);
            model.getLeftIndicator().setCycle(false);
            model.getRightIndicator().setCycle(false);
        }

    }
    public void leftTipBlinkingPressed(){

        if (model.getBlinkingState("Left")==Blinking.FLASHING && model.getFlashingCycles("Left")==true){
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
            model.stopTimer();
        }else{
            model.stopTimer();
            model.setNumberofFlashCycles(0);
            model.tipPitmanArm(PitmanArmPosition.DOWNWARD5,model.getLengthOfTimeHeld());
        }
    }
    public void rightTipBlinkingPressed(){

        if (model.getBlinkingState("Right")==Blinking.FLASHING && model.getFlashingCycles("Right")==true){
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
            model.stopTimer();
            model.setNumberofFlashCycles(0);
        }else{
            model.setNumberofFlashCycles(0);
            model.stopTimer();
            model.tipPitmanArm(PitmanArmPosition.UPWARD5,model.getLengthOfTimeHeld());
        }
    }
    public void hazardSwitchPressed(){
        if (model.getHazardSwitchState()==false){
            model.setHazardSwitch(true);
        }else{
            model.setHazardSwitch(false);

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
            model.setLightRotarySwitch(LightRotarySwitchState.ON);
        }
        if (view.getLightRotarySwitch().getSelectedItem().equals("Off")){
            model.setLightRotarySwitch(LightRotarySwitchState.OFF);
        }
        if (view.getLightRotarySwitch().getSelectedItem().equals("Auto")){
            model.setLightRotarySwitch(LightRotarySwitchState.AUTO);
        }
    }
    public void dayTimeRunningLightPressed(){
        if (model.getDayTimeRunningLight()==false){
            model.setDayTimeRunningLight(true);
        }else{
            model.setDayTimeRunningLight(false);
        }
    }
    public void doorPressed(){
        if (model.getDoorPosition()==DoorPosition.CLOSED){
            model.setDoorStatus(DoorPosition.OPEN);

        }else{
            model.setDoorStatus(DoorPosition.CLOSED);
        }
    }
    public void ambientLightPressed(){
        if (model.getAmbientLight()==true){
            model.setAmbientLight(false);

        }else{
            model.setAmbientLight(true);
            model.countAmbientLightTime();
        }
    }
    public void exteriorBrightnessPressed(){
        model.setExteriorBrightness(Integer.parseInt(view.getExteriorBrightness().getText()));
    }
    public void darknessSwitchPressed(){
        if (model.getDarknessSwitch()==true){
            model.setDarknessSwitch(false);
        }else{
            model.setDarknessSwitch(true);
        }
    }
    public void carDuration(){
        model.setDrivingSpeed(Integer.parseInt(view.getSpeedOfCar().getText()));
    }
    public void passedCorner(){
        model.countDurationOfPassingCornerTime();
    }
}
