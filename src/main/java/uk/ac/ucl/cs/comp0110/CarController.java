package uk.ac.ucl.cs.comp0110;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EtchedBorder;

public class CarController {
    public CarView view;
    private CarModel model;

    public CarController(CarView view, CarModel model) {
        this.view = view;
        this.model = model;
    }

    public void addButtonFunctions() {
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
}
