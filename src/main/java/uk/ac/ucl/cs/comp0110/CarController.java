package uk.ac.ucl.cs.comp0110;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;

public class CarController {
    public CarView view;
    private CarModel model;

    private double lengthOfTimeHeld=0;
    public CarController(CarView view, CarModel model) {
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
        if (model.getBlinkingState("Right") == Blinking.FLASHING) {
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
        } else {
            model.setFlashCycleState(PitmanArmPosition.DOWNWARD5,model.getLengthOfTimeHeld());
        }
    }
    public void hazardSwitchPressed(){
        if (model.getHazardSwitchState()==false){
            model.setHazardSwitch(true);
        }else{
            model.setHazardSwitch(false);
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
            System.out.println("testing12345678");
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
            model.stopTimer();
        }else{
            model.stopTimer();
            model.setFlashCycleState(PitmanArmPosition.DOWNWARD5,model.getLengthOfTimeHeld());
        }
    }
    public void rightTipBlinkingPressed(){
        if (model.getBlinkingState("Right")==Blinking.FLASHING){
            System.out.println("testing12345678");
            model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
            model.stopTimer();
        }else{
            model.stopTimer();
            model.setFlashCycleState(PitmanArmPosition.UPWARD5,model.getLengthOfTimeHeld());
        }
    }
}
