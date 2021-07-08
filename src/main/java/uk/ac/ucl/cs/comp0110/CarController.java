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
    }



    public void leftDirectionPressed() {
        if (model.getBlinkingState("Left") == Blinking.FLASHING) {
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        } else {
            model.setPitmanArmPosition(PitmanArmPosition.DOWNWARD7);
        }
    }
    public void rightDirectionPressed() {

        if (model.getBlinkingState("Right") == Blinking.FLASHING) {
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        }else{
            model.setPitmanArmPosition(PitmanArmPosition.UPWARD7);

        }

    }
    public void leftTipBlinkingPressed(){
        if (model.getBlinkingState("Left")==Blinking.FLASHING){
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
            model.stopTimer();
        }else{
            model.stopTimer();
            model.tipPitmanArm(PitmanArmPosition.DOWNWARD5,model.getLengthOfTimeHeld());
        }
    }
    public void rightTipBlinkingPressed(){
        if (model.getBlinkingState("Right")==Blinking.FLASHING){
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
            model.stopTimer();
        }else{
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
}
