package uk.ac.ucl.cs.comp0110;

import java.time.LocalDateTime;

enum Blinking{
    FLASHING,NONFLASHING
}
enum Flashing{
    BRIGHT,DARK
}
enum Headlight {
    LOWBEAM,HIGHBEAM,INACTIVE
}
public class Indicator {
    private Blinking blinkingState;
    private boolean flashing;
    private int hazardCycleLength;
    private int numberofFlashCycles;
    private Flashing flashState;
    private boolean isCorneringLightOn;
    private boolean isTailLightOn;
    private int dimmedLightPercentage;
    private int ambientLightDuration;
    private Headlight headlight;
    private SystemClock clock;
    private int illuminationArea;
    private int illuminationStrengthPercentage;
    public Indicator(){
        blinkingState=Blinking.NONFLASHING;
        clock=new SystemClock();
        clock.useFixedClockAt(LocalDateTime.now());
        dimmedLightPercentage=0;
    }
    public Flashing getFlashState(){
        return flashState;
    }
    public void setFlashState(Flashing flashState){
        this.flashState=flashState;
    }

    public void setState(Blinking blinkingState){
        this.blinkingState=blinkingState;
    }
    public Blinking getState(){
        return blinkingState;
    }
    public void setCycle(boolean flashing){
        this.flashing=flashing;
    }
    public boolean getCycle(){
        return flashing;
    }
    public void setHazardCycleLength(int hazardCycleLength){
        this.hazardCycleLength=hazardCycleLength;
    }
    public int getHazardCycleLength(){
        return hazardCycleLength;
    }
    public void setNumberofFlashCycles(int numberofFlashCycles){
        this.numberofFlashCycles=numberofFlashCycles;
    }
    public int getNumberofFlashCycles(){
        return numberofFlashCycles;
    }
    public void isCorneringLightOn(boolean isCorneringLightOn){
        this.isCorneringLightOn=isCorneringLightOn;
    }
    public boolean getCorneringLightState(){
        return isCorneringLightOn;
    }
    public void isTailLightOn(boolean isTailLightOn){
        this.isTailLightOn=isTailLightOn;
    }
    public boolean getTailLightState(){
        return isTailLightOn;
    }
    public void setLightDimmingPercentage(int dimmedLightPercentage){
        this.dimmedLightPercentage=dimmedLightPercentage;
    }
    public int getLightDimmingPercentage(){
        return dimmedLightPercentage;
    }
    public int getAmbientLightDuration(){
        return ambientLightDuration;
    }
    public void setAmberLightDuration(int ambientLightDuration){

    }
    public void setAmbientLightDuration(){

    }

    public void setLowBeamState(Headlight headlight){
        this.headlight = headlight;
    }
    public Headlight getLowBeamState(){
        return headlight;
    }
    public SystemClock getClock(){
        return clock;
    }
    public void setIlluminationArea(int illuminationArea){
        this.illuminationArea=illuminationArea;
    }
    public int getIlluminationArea(){
        return illuminationArea;
    }
    public void setIlluminationStrengthPercentage(int illuminationStrengthPercentage){
        this.illuminationStrengthPercentage=illuminationStrengthPercentage;
    }
    public int getIlluminationStrengthPercentage(){
        return illuminationStrengthPercentage;
    }
}