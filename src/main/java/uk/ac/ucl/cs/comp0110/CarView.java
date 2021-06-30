package uk.ac.ucl.cs.comp0110;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
public class CarView extends JFrame{
    public CarModel model;
    public Graphics2D g2d;
    Polygon leftFrontIndicator;
    Polygon rightFrontIndicator;
    Polygon leftSideIndicator;
    Polygon rightSideIndicator;
    Polygon leftBackIndicator;
    Polygon rightBackIndicator;
    public boolean leftIndicatorFlashed;
    public boolean rightIndicatorFlashed;
    ScheduledExecutorService service;
    public JPanel bottomPanel = new JPanel();
    public JRadioButton leftDirection;
    public JRadioButton rightDirection;
    public JRadioButton leftTipBlinking;
    public JRadioButton rightTipBlinking;
    private int numberOfFlashCycles;

    public CarView(CarModel model) {
        this.model=model;
        leftIndicatorFlashed=false;
        rightIndicatorFlashed=false;
        service= Executors.newSingleThreadScheduledExecutor();
        leftDirection = new JRadioButton("Downward Direction Blinking");
        rightDirection = new JRadioButton("Upward Direction Blinking");
        leftTipBlinking=new JRadioButton("Upward Tip-Blinking");
        rightTipBlinking=new JRadioButton("Downward Tip-Blinking");
        numberOfFlashCycles=0;
        makeFrame();


    }
    public void paint(Graphics g) {

        super.paint(g);
        g2d = (Graphics2D) g;
        leftFrontIndicator = new Polygon(new int[]{getWidth() / 3 + 70, getWidth() / 3 + 25, getWidth() / 3 + 20}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        rightFrontIndicator = new Polygon(new int[]{getWidth() / 3 - 30, getWidth() / 3, getWidth() / 3}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        leftSideIndicator = new Polygon(new int[]{getWidth() / 3 + 70, getWidth() / 3 + 25, getWidth() / 3 + 20}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        rightSideIndicator = new Polygon(new int[]{getWidth() / 3 - 30, getWidth() / 3, getWidth() / 3}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        leftBackIndicator = new Polygon(new int[]{getWidth() / 3 + 70, getWidth() / 3 + 25, getWidth() / 3 + 20}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        rightBackIndicator = new Polygon(new int[]{getWidth() / 3 - 30, getWidth() / 3, getWidth() / 3}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        leftFrontIndicator.translate(1, -180);
        rightFrontIndicator.translate(260, -180);
        leftSideIndicator.translate(-70, -60);
        rightSideIndicator.translate(330, -60);
        leftBackIndicator.translate(1, 300);
        rightBackIndicator.translate(260, 300);
        g.drawRect(getWidth() / 3, getHeight() / 8, 300, 600);
        g.drawPolygon(leftFrontIndicator);
        g.drawPolygon(rightFrontIndicator);
        g.drawPolygon(leftSideIndicator);
        g.drawPolygon(rightSideIndicator);
        g.drawPolygon(leftBackIndicator);
        g.drawPolygon(rightBackIndicator);

        if (model.getBlinkingState("Left") == Blinking.FLASHING && model.getFlashingCycles("Left") == false) {

            if (leftIndicatorFlashed == false) {
                g2d.setPaint(new Color(255, 255, 0));
                g.fillPolygon(leftFrontIndicator);
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftBackIndicator);
                leftIndicatorFlashed = true;
            } else {
                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(leftFrontIndicator);
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftBackIndicator);
                leftIndicatorFlashed = false;
            }
        } else if (model.getBlinkingState("Left") == Blinking.NONFLASHING)  {

            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(leftFrontIndicator);
            g.fillPolygon(leftSideIndicator);
            g.fillPolygon(leftBackIndicator);
            leftIndicatorFlashed = false;
        }
        if (model.getBlinkingState("Left") == Blinking.FLASHING && model.getFlashingCycles("Left") == true) {
            rightDirection.setSelected(false);

            if (leftIndicatorFlashed == false) {

                g2d.setPaint(new Color(255, 255, 0));
                g.fillPolygon(leftBackIndicator);
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftFrontIndicator);
                leftIndicatorFlashed = true;
                numberOfFlashCycles++;
            } else {

                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftBackIndicator);
                g.fillPolygon(leftFrontIndicator);
                leftIndicatorFlashed = false;

            }
            if (numberOfFlashCycles==3) {
                numberOfFlashCycles=0;
                model.setLengthOfTimeHeld(0);
                model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
                rightTipBlinking.setSelected(false);
            }
        }else{
            rightTipBlinking.setSelected(false);
        }


        if (model.getBlinkingState("Right") == Blinking.FLASHING && model.getFlashingCycles("Right") == false) {



            if (rightIndicatorFlashed == false) {

                g2d.setPaint(new Color(255, 255, 0));
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                rightIndicatorFlashed = true;
            } else {

                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                rightIndicatorFlashed = false;
            }

        } else if (model.getBlinkingState("Right") == Blinking.NONFLASHING) {

            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(rightFrontIndicator);
            g.fillPolygon(rightSideIndicator);
            g.fillPolygon(rightBackIndicator);
            rightIndicatorFlashed = false;
        }
        if (model.getBlinkingState("Right") == Blinking.FLASHING && model.getFlashingCycles("Right") == true) {


                if (rightIndicatorFlashed == false) {

                    g2d.setPaint(new Color(255, 255, 0));
                    g.fillPolygon(rightFrontIndicator);
                    g.fillPolygon(rightSideIndicator);
                    g.fillPolygon(rightBackIndicator);
                    rightIndicatorFlashed = true;
                    numberOfFlashCycles++;
                } else {

                    g2d.setPaint(new Color(211, 211, 211));
                    g.fillPolygon(rightFrontIndicator);
                    g.fillPolygon(rightSideIndicator);
                    g.fillPolygon(rightBackIndicator);
                    rightIndicatorFlashed = false;

                }
            if (numberOfFlashCycles==3) {
                numberOfFlashCycles=0;
                model.setLengthOfTimeHeld(0);
                model.setPitmanArmPosition(PitmanArmPosition.NEUTRAL);
                leftTipBlinking.setSelected(false);
            }
            }else{
            leftTipBlinking.setSelected(false);
        }


        }


    public void makeFrame() {
        Container contentPane = getContentPane();
        JPanel centralGrid = new JPanel();
        JPanel southPanel = new JPanel();
        southPanel.setBorder(new EtchedBorder());
        southPanel.add(makeBottomInputs());
        centralGrid.setLayout(new BorderLayout(6, 6));
        centralGrid.add(southPanel, BorderLayout.SOUTH);
        contentPane.add(centralGrid);
        setTitle("Exterior Light System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setVisible(true);
        service.scheduleAtFixedRate(new Runnable(){
            @Override
            public void run() {
                repaint();

            }
        },0,1,TimeUnit.SECONDS);

    }




    public JPanel makeBottomInputs() {
        final JPanel typeOfBlinking = new JPanel();
        final JPanel blinkingDirection = new JPanel();

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        blinkingDirection.setLayout(new FlowLayout());
        typeOfBlinking.setLayout(new FlowLayout());
        JLabel blinkingType = new JLabel("Type of Blinking:");
        JLabel direction = new JLabel("Direction of Pitarm");
        blinkingDirection.add(direction);
        blinkingDirection.add(leftDirection);
        blinkingDirection.add(rightTipBlinking);
        blinkingDirection.add(leftTipBlinking);
        blinkingDirection.add(rightDirection);
        typeOfBlinking.add(blinkingType);
        bottomPanel.add(typeOfBlinking);
        bottomPanel.add(blinkingDirection);
        return bottomPanel;
    }
    public JRadioButton getLeftDirection(){
        return leftDirection;
    }
    public JRadioButton getRightDirection(){
        return rightDirection;
    }
    public JRadioButton getLeftTipBlinking(){
        return leftTipBlinking;
    }
    public JRadioButton getRightTipBlinking(){
        return rightTipBlinking;
    }


}
