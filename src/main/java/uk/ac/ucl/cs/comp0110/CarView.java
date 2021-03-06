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
    public Car model;
    public Graphics2D g2d;
    public ScheduledExecutorService service;
    Polygon leftFrontIndicator;
    Polygon rightFrontIndicator;
    Polygon leftSideIndicator;
    Polygon rightSideIndicator;
    Polygon leftCornerLight;
    Polygon rightCornerLight;
    Polygon leftBackIndicator;
    Polygon rightBackIndicator;
    public boolean leftIndicatorFlashed;
    public boolean rightIndicatorFlashed;
    public JPanel bottomPanel = new JPanel();
    public JRadioButton leftDirection;
    public JRadioButton rightDirection;
    public JRadioButton leftTipBlinking;
    public JRadioButton rightTipBlinking;
    public JRadioButton hazardSwitch;
    public JRadioButton soldInUKOrCanada;
    public JRadioButton keyInPosition;
    public JRadioButton keyInserted;
    public JRadioButton noKeyInserted;
    public JRadioButton ambientLight;
    public JRadioButton dayTimeRunningLight;
    public JTextField exteriorBrightness;
    public JComboBox lightRotarySwitch;
    public JRadioButton darknessSwitch;
    public JRadioButton doorPosition;
    public JTextField speedOfCar;
    public JRadioButton passCorner;
    public JRadioButton reverseGear;
    public JRadioButton pitmanArmForward;
    public JRadioButton pitmanArmBackward;
    public JRadioButton incomingVehicleDetectedByCamera;
    private JTextField numberOfDegreesSteeringWheelTurned;
    private int numberOfFlashCycles;

    public CarView(Car model) {
        this.model=model;

        leftIndicatorFlashed=false;
        rightIndicatorFlashed=false;
        leftDirection = new JRadioButton("Downward Direction Blinking");
        rightDirection = new JRadioButton("Upward Direction Blinking");
        leftTipBlinking=new JRadioButton("Downward Tip-Blinking");
        rightTipBlinking=new JRadioButton("Upward Tip-Blinking");
        hazardSwitch=new JRadioButton("Hazard Warning Button");
        soldInUKOrCanada=new JRadioButton("Car sold in UK or Canada");
        keyInserted=new JRadioButton("Key Inserted");
        noKeyInserted=new JRadioButton("No Key Inserted");
        keyInPosition=new JRadioButton("Key Inserted with ignition on");
        ambientLight=new JRadioButton("Ambient Light Status");
        dayTimeRunningLight=new JRadioButton("Daytime Running Light Status");
        lightRotarySwitch= new JComboBox(new String[]{"Off", "On","Auto"});
        darknessSwitch=new JRadioButton("Darkness Switch");
        doorPosition=new JRadioButton("Door Open");
        passCorner= new JRadioButton("Passed Corner");
        exteriorBrightness=new JTextField("Exterior Brightness");
        speedOfCar=new JTextField("Speed of car");
        numberOfDegreesSteeringWheelTurned=new JTextField("Degrees Turned by Steering Wheel");
        reverseGear=new JRadioButton("Reverse Gear");
        pitmanArmForward =new JRadioButton("Move Pitman Arm Forward");
        pitmanArmBackward =new JRadioButton("Move Pitman Arm Backward");
        incomingVehicleDetectedByCamera=new JRadioButton("Incoming Vehicle Detected");
        numberOfFlashCycles=0;
        service= Executors.newSingleThreadScheduledExecutor();
        makeFrame();


    }

    public void drawHazard(Graphics g){
        if (model.getHazardWarningSwitchOn()){
            if (!leftIndicatorFlashed && !rightIndicatorFlashed) {
                g2d.setPaint(new Color(255, 255, 0));
                g.fillPolygon(leftFrontIndicator);
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftBackIndicator);
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                leftIndicatorFlashed = true;
                rightIndicatorFlashed=true;


            } else {
                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(leftFrontIndicator);
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftBackIndicator);
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                leftIndicatorFlashed = false;
                rightIndicatorFlashed=false;


            }
        }
    }
    public void drawCornerLight(Graphics g){
        if (model.getLeftIndicator().getCorneringLightState()){
            g2d.setPaint(new Color(255, 255, 0));
            g.fillPolygon(leftCornerLight);
        }else{
            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(leftCornerLight);
            leftDirection.setSelected(false);
            rightDirection.setSelected(false);
            passCorner.setSelected(false);
        }
        if (model.getRightIndicator().getCorneringLightState()){
            g2d.setPaint(new Color(255, 255, 0));
            g.fillPolygon(rightCornerLight);
        }else{
            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(rightCornerLight);
            leftDirection.setSelected(false);
            rightDirection.setSelected(false);
            passCorner.setSelected(false);
        }
    }


    public void drawLeftBlinking(Graphics g){
        changeSelectedButton();

        if (model.getBlinkingState("left")==Blinking.FLASHING && !model.checkFlashingCyclesAreOccurring("left")) {

            if (model.getFlashState()==IndicatorBulb.DARK) {
                g2d.setPaint(new Color(255, 255, 0));
                if (model.getDimmedLightStatus("left")==50){
                    g2d.setPaint(new Color(255,200,0));

                }
                g.fillPolygon(leftFrontIndicator);
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftBackIndicator);
                model.changeFlashState();
            } else {
                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(leftSideIndicator);

                if (model.getIndicatorBeamState()== Headlight.LOWBEAM){
                    g.fillPolygon(leftFrontIndicator);
                    g.fillPolygon(rightBackIndicator);
                }
                model.changeFlashState();


            }
        } else if (model.getBlinkingState("left") == Blinking.NONFLASHING)  {

            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(leftSideIndicator);

            if (model.getIndicatorBeamState()== Headlight.INACTIVE){
                g.fillPolygon(leftFrontIndicator);
                g.fillPolygon(leftBackIndicator);

            }

        }
    }
    public void drawParkingLight(Graphics g){
        if (model.getParkingLightEngaged() &&(model.getPitmanArmState()==PitmanArmPosition.DOWNWARD5 || model.getPitmanArmState()==PitmanArmPosition.DOWNWARD7)) {
            g2d.setPaint(new Color(200, 150, 0));
            g.fillPolygon(leftFrontIndicator);
            g.fillPolygon(leftBackIndicator);

        }else if (model.getParkingLightEngaged() && (model.getPitmanArmState()==PitmanArmPosition.UPWARD5 || model.getPitmanArmState()==PitmanArmPosition.UPWARD7)){
            g.fillPolygon(rightBackIndicator);
            g.fillPolygon(rightFrontIndicator);
        }
    }
    public void drawLowBeamHeadLight(Graphics g) {
        if (model.getLightRotarySwitchState() == LightRotarySwitchState.OFF && model.getLeftIndicator().getBlinkingState()==Blinking.NONFLASHING && model.getRightIndicator().getBlinkingState()==Blinking.NONFLASHING && model.getIndicatorBeamState()==Headlight.LOWBEAM) {
            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(leftFrontIndicator);
            g.fillPolygon(rightFrontIndicator);
        }
        if ((model.getIndicatorBeamState()== Headlight.LOWBEAM)) {
            g2d.setPaint(new Color(255, 255, 0));
            g.fillPolygon(leftFrontIndicator);
            g.fillPolygon(rightFrontIndicator);
            g.fillPolygon(leftBackIndicator);
            g.fillPolygon(rightBackIndicator);
        }

        if ((model.getLeftIndicator().getBeamState()== Headlight.LOWBEAM && model.getRightIndicator().getLightDimmingPercentage()==50)) {
            g2d.setPaint(new Color(255, 200, 0));
            g.fillPolygon(leftFrontIndicator);
            g.fillPolygon(rightFrontIndicator);
            g.fillPolygon(leftBackIndicator);
            g.fillPolygon(rightBackIndicator);
        }
        if (darknessSwitch.isSelected()){
            ambientLight.setSelected(false);
        }

    }

    public void drawLeftTipBlinking(Graphics g){
        changeSelectedButton();
        if (model.checkFlashingCyclesAreOccurring("left")) {
            leftTipBlinking.setSelected(false);
            if (model.getFlashState()==IndicatorBulb.DARK) {
                g2d.setPaint(new Color(255, 255, 0));
                g.fillPolygon(leftBackIndicator);
                g.fillPolygon(leftSideIndicator);
                g.fillPolygon(leftFrontIndicator);
                model.changeFlashState();
                int flashCycle=model.getNumberofFlashCycles()+1;
                model.setNumberofFlashCycles(flashCycle);
            } else {
                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(leftSideIndicator);

                if (model.getIndicatorBeamState()== Headlight.INACTIVE){
                    g.fillPolygon(leftFrontIndicator);
                    g.fillPolygon(leftBackIndicator);
                }
                model.changeFlashState();

            }
            if (model.getNumberofFlashCycles()==3) {
                leftTipBlinking.setSelected(false);
            }
        }
    }
    public void changeSelectedButton(){
        rightDirection.setSelected(false);
        leftDirection.setSelected(false);
        leftTipBlinking.setSelected(false);
        rightTipBlinking.setSelected(false);
    }
    public void drawRightBlinking(Graphics g){
        changeSelectedButton();

        if (model.getBlinkingState("right") == Blinking.FLASHING && !model.checkFlashingCyclesAreOccurring("right")) {

            if (model.getFlashState()==IndicatorBulb.DARK) {
                g2d.setPaint(new Color(255, 255, 0));
                if (model.getDimmedLightStatus("right")==50){
                    g2d.setPaint(new Color(255,200,0));
                }

                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                model.changeFlashState();
            } else {

                g2d.setPaint(new Color(211, 211, 211));
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                if (model.getIndicatorBeamState()== Headlight.INACTIVE){
                    g.fillPolygon(rightFrontIndicator);
                    g.fillPolygon(rightBackIndicator);
                }
                model.changeFlashState();

            }

        } else if (model.getBlinkingState("right") == Blinking.NONFLASHING) {

            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(rightSideIndicator);

            if (model.getIndicatorBeamState()== Headlight.INACTIVE){
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightBackIndicator);
            }

        }
    }
    public void drawRightTipBlinking(Graphics g){
        changeSelectedButton();
        if (model.checkFlashingCyclesAreOccurring("right")) {
            rightTipBlinking.setSelected(false);

            if (model.getFlashState()==IndicatorBulb.DARK) {

                g2d.setPaint(new Color(255, 255, 0));
                g.fillPolygon(rightFrontIndicator);
                g.fillPolygon(rightSideIndicator);
                g.fillPolygon(rightBackIndicator);
                model.changeFlashState();
                int flashCycle=model.getNumberofFlashCycles()+1;
                model.setNumberofFlashCycles(flashCycle);
            } else {

                g2d.setPaint(new Color(211, 211, 211));

                g.fillPolygon(rightSideIndicator);

                model.changeFlashState();
                if (model.getIndicatorBeamState()== Headlight.INACTIVE){
                    g.fillPolygon(rightFrontIndicator);
                    g.fillPolygon(rightBackIndicator);
                }
            }
            if (model.getNumberofFlashCycles()==3) {
                rightTipBlinking.setSelected(false);
            }
        }
    }
    public void drawHighBeam(Graphics g){
        if (model.getIndicatorBeamState()==Headlight.HIGHBEAM && (model.getPitmanArmState()==PitmanArmPosition.FORWARD||model.getPitmanArmState()==PitmanArmPosition.BACKWARD)){
            g2d.setPaint(new Color(120, 255, 0));
            g.fillPolygon(rightFrontIndicator);
            g.fillPolygon(leftFrontIndicator);
            g.fillPolygon(leftBackIndicator);
            g.fillPolygon(rightBackIndicator);
        }else if (model.getIndicatorBeamState()!=Headlight.HIGHBEAM && model.getBlinkingState("left")==Blinking.NONFLASHING && model.getBlinkingState("right")==Blinking.NONFLASHING && model.checkFlashingCyclesAreOccurring("left")
        && model.checkFlashingCyclesAreOccurring("right")){
            g2d.setPaint(new Color(211, 211, 211));
            g.fillPolygon(rightFrontIndicator);
            g.fillPolygon(leftFrontIndicator);
            g.fillPolygon(leftBackIndicator);
            g.fillPolygon(rightBackIndicator);
        }
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
        leftCornerLight = new Polygon(new int[]{getWidth() / 3 + 70, getWidth() / 3 + 25, getWidth() / 3 + 20}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        rightCornerLight = new Polygon(new int[]{getWidth() / 3 - 30, getWidth() / 3, getWidth() / 3}, new int[]{getHeight() / 3 + 20, getHeight() / 3 + 45, getHeight() / 3 + 90}, 3);
        leftFrontIndicator.translate(1, -180);
        rightFrontIndicator.translate(260, -180);
        leftSideIndicator.translate(-70, -60);
        rightSideIndicator.translate(330, -60);
        leftBackIndicator.translate(1, 300);
        rightBackIndicator.translate(260, 300);
        leftCornerLight.translate(-140, -200);
        rightCornerLight.translate(360, -190);
        g.drawRect(getWidth() / 3, getHeight() / 8, 300, 600);
        g.drawPolygon(leftFrontIndicator);
        g.drawPolygon(rightFrontIndicator);
        g.drawPolygon(leftSideIndicator);
        g.drawPolygon(rightSideIndicator);
        g.drawPolygon(leftBackIndicator);
        g.drawPolygon(rightBackIndicator);
        g.drawPolygon(leftCornerLight);
        g.drawPolygon(rightCornerLight);



            checkIgnition();


            drawLeftBlinking(g);
            drawRightBlinking(g);
            drawLeftTipBlinking(g);
            drawRightTipBlinking(g);
            drawCornerLight(g);
            drawHazard(g);
             drawParkingLight(g);
            drawHighBeam(g);
            drawLowBeamHeadLight(g);

    }
    public void makeFrame() {
        Container contentPane = getContentPane();
        JPanel centralGrid = new JPanel();
        JPanel southPanel = new JPanel();
        JPanel westPanel=new JPanel();
        westPanel.setBorder(new EtchedBorder());
        southPanel.setBorder(new EtchedBorder());
        southPanel.add(makeBlinkingInputs());
        westPanel.add(makeOptionalInputs());
        centralGrid.setLayout(new BorderLayout(6, 6));
        centralGrid.add(southPanel, BorderLayout.SOUTH);
        centralGrid.add(westPanel,BorderLayout.WEST);
        contentPane.add(centralGrid);
        setTitle("Exterior Light System");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        pack();
        setVisible(true);
        service.scheduleAtFixedRate(() -> repaint(),0,500,TimeUnit.MILLISECONDS);

    }
    public JPanel makeOptionalInputs(){
        final JPanel optionalInputs=new JPanel();
        optionalInputs.setLayout(new BoxLayout(optionalInputs, BoxLayout.Y_AXIS));
        optionalInputs.add(soldInUKOrCanada);
        optionalInputs.add(noKeyInserted);
        optionalInputs.add(keyInPosition);
        optionalInputs.add(keyInserted);
        optionalInputs.add(lightRotarySwitch);
        optionalInputs.add(doorPosition);
        optionalInputs.add(ambientLight);
        optionalInputs.add(dayTimeRunningLight);
        optionalInputs.add(exteriorBrightness);
        optionalInputs.add(darknessSwitch);
        optionalInputs.add(passCorner);
        optionalInputs.add(speedOfCar);
        optionalInputs.add(numberOfDegreesSteeringWheelTurned);
        optionalInputs.add(reverseGear);
        optionalInputs.add(incomingVehicleDetectedByCamera);
        optionalInputs.add(hazardSwitch);
        return optionalInputs;
    }
    public JPanel makeBlinkingInputs() {
        final JPanel typeOfBlinking = new JPanel();
        final JPanel blinkingDirection = new JPanel();

        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        blinkingDirection.setLayout(new FlowLayout());
        typeOfBlinking.setLayout(new FlowLayout());
        JLabel blinkingType = new JLabel("Type of Blinking:");
        JLabel direction = new JLabel("Direction of Pitarm");
        blinkingDirection.add(direction);
        blinkingDirection.add(leftDirection);
        blinkingDirection.add(leftTipBlinking);
        blinkingDirection.add(rightTipBlinking);
        blinkingDirection.add(rightDirection);
        blinkingDirection.add(pitmanArmForward);
        blinkingDirection.add(pitmanArmBackward);
        typeOfBlinking.add(blinkingType);
        bottomPanel.add(typeOfBlinking);
        bottomPanel.add(blinkingDirection);
        return bottomPanel;
    }
    public void checkIgnition(){
        if (model.getIgnitionState()==IgnitionStatus.KEYINIGNITIONONPOSITION){
            noKeyInserted.setSelected(false);
            keyInserted.setSelected(false);
        }else if (model.getIgnitionState()==IgnitionStatus.KEYINSERTED){
            noKeyInserted.setSelected(false);
            keyInPosition.setSelected(false);
        }else{
            keyInPosition.setSelected(false);
            keyInserted.setSelected(false);
        }
        if (model.getIgnitionState()!=IgnitionStatus.KEYINIGNITIONONPOSITION){


        }else{


        }
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
    public JRadioButton getHazardSwitch(){
        return hazardSwitch;
    }
    public JRadioButton getSoldInUKOrCanada(){
        return soldInUKOrCanada;
    }
    public JRadioButton getKeyInPosition(){
        return keyInPosition;
    }
    public JRadioButton getKeyInserted(){
        return keyInserted;
    }
    public JRadioButton getNoKeyInserted(){
        return noKeyInserted;
    }
    public JComboBox getLightRotarySwitch(){
        return lightRotarySwitch;
    }
    public JRadioButton getDoorPosition(){
        return doorPosition;
    }
    public JRadioButton getAmbientLight(){
        return ambientLight;
    }
    public JRadioButton getDayTimeRunningLight(){
        return dayTimeRunningLight;
    }
    public JTextField getExteriorBrightness(){
        return exteriorBrightness;
    }
    public JRadioButton getDarknessSwitch(){
        return darknessSwitch;
    }
    public JTextField getSpeedOfCar(){
        return speedOfCar;
    }
    public JRadioButton getPassCorner(){
        return passCorner;
    }
    public JTextField getNumberOfDegreesSteeringWheelTurned(){
        return numberOfDegreesSteeringWheelTurned;
    }
    public JRadioButton getReverseGear(){
        return reverseGear;
    }
    public JRadioButton getPitmanArmForward(){
        return pitmanArmForward;
    }
    public JRadioButton getPitmanArmBackward(){
        return pitmanArmBackward;
    }
    public JRadioButton getIncomingVehicleDetectedByCamera(){
        return incomingVehicleDetectedByCamera;
    }
}