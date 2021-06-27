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
    public boolean leftIndicatorFlashed;
    public boolean rightIndicatorFlashed;
    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    public JPanel bottomPanel = new JPanel();
    public JRadioButton leftDirection = new JRadioButton("Downward");
    public JRadioButton rightDirection = new JRadioButton("Upward");
    public CarView(CarModel model) {
        this.model=model;
        leftIndicatorFlashed=false;
        rightIndicatorFlashed=false;
        makeFrame();

    }
    public void paint(Graphics g) {
        super.paint(g);
        g2d = (Graphics2D) g;


        leftFrontIndicator = new Polygon(new int[]{getWidth() / 3 + 70, getWidth() / 3 + 25, getWidth() / 3 + 20}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        rightFrontIndicator = new Polygon(new int[]{getWidth() / 3 - 30, getWidth() / 3, getWidth() / 3}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        leftFrontIndicator.translate(1, -100);
        rightFrontIndicator.translate(260, -100);
        g.drawRect(getWidth() / 3, getHeight() / 8, 300, 600);
        g.drawPolygon(leftFrontIndicator);
        g.drawPolygon(rightFrontIndicator);

            if (model.getBlinkingState("Left") == Blinking.FLASHING) {
                rightDirection.setSelected(false);
                if (leftIndicatorFlashed == false) {
                    g2d.setPaint(new Color(255, 255, 0));
                    g.fillPolygon(leftFrontIndicator);
                    leftIndicatorFlashed=true;
                } else {
                    g2d.setPaint(new Color(211, 211, 211));
                    g.fillPolygon(leftFrontIndicator);
                    leftIndicatorFlashed=false;
                }
            }else{
                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(leftFrontIndicator);
                leftIndicatorFlashed=false;
            }


            if (model.getBlinkingState("Right") == Blinking.FLASHING) {
                leftDirection.setSelected(false);
                if (rightIndicatorFlashed == false) {
                    g2d.setPaint(new Color(255, 255, 0));
                    g.fillPolygon(rightFrontIndicator);
                    rightIndicatorFlashed=true;
                } else {
                    g2d.setPaint(new Color(211, 211, 211));
                    g.fillPolygon(rightFrontIndicator);
                    rightIndicatorFlashed=false;
                }
            }else{

                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(rightFrontIndicator);
                rightIndicatorFlashed=false;
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

}
