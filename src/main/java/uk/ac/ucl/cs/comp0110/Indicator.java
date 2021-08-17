package uk.ac.ucl.cs.comp0110;

import java.time.LocalDateTime;

enum Blinking{
    FLASHING,NONFLASHING
}
enum IndicatorBulb {
    BRIGHT,DARK
}
enum Headlight {
    LOWBEAM,HIGHBEAM,INACTIVE
}
public class Indicator {
    private Blinking blinkingState;
    private boolean tipBlinkingOn;
    private int durationOfHazardCycle;
    private int numberofFlashCycles;
    private IndicatorBulb indicatorBulbState;
    private boolean corneringLightOn;
    private boolean tailLightOn;
    private int dimmedLightPercentage;
    private int ambientLightDuration;
    private Headlight headlightBeam;
    private SystemClock clock;
    private int illuminationArea;
    private int illuminationStrengthPercentage;
    public Indicator(){
        blinkingState=Blinking.NONFLASHING;
        clock=new SystemClock();
        clock.useFixedClockAt(LocalDateTime.now());
        dimmedLightPercentage=0;
        durationOfHazardCycle=1;
    }
    public IndicatorBulb getIndicatorBulbState(){
        return indicatorBulbState;
    }
    public void setIndicatorBulbState(IndicatorBulb indicatorBulbState){
        this.indicatorBulbState = this.indicatorBulbState;
    }

    public void setBlinkingState(Blinking blinkingState){
        this.blinkingState=blinkingState;
    }
    public Blinking getBlinkingState(){
        return blinkingState;
    }
    public void isTipBlinkingOn(boolean tipBlinkingOn){
        this.tipBlinkingOn =tipBlinkingOn;
    }
    public boolean getTipBlinkingOccurring(){
        return tipBlinkingOn;
    }
    public void setDurationOfHazardCycle(int durationOfHazardCycle){
        this.durationOfHazardCycle = durationOfHazardCycle;
    }
    public int getDurationOfHazardCycle(){
        return durationOfHazardCycle;
    }
    public void setNumberofFlashCycles(int numberofFlashCycles){
        this.numberofFlashCycles=numberofFlashCycles;
    }
    public int getNumberofFlashCycles(){
        return numberofFlashCycles;
    }
    public void isCorneringLightOn(boolean isCorneringLightOn){
        this.corneringLightOn =isCorneringLightOn;
    }
    public boolean getCorneringLightState(){
        return corneringLightOn;
    }
    public void isTailLightOn(boolean isTailLightOn){
        this.tailLightOn =isTailLightOn;
    }
    public boolean getTailLightState(){
        return tailLightOn;
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
    public void setAmbientLightDuration(int ambientLightDuration){
        this.ambientLightDuration=ambientLightDuration;
    }

    public void setBeamState(Headlight headlightBeam){
        this.headlightBeam = headlightBeam;
    }
    public Headlight getBeamState(){
        return headlightBeam;
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